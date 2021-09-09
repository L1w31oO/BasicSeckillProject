package com.lw.service.impl;

import com.lw.dao.OrderDaoMapper;
import com.lw.dao.SequenceDaoMapper;
import com.lw.dataObject.OrderDao;
import com.lw.dataObject.SequenceDao;
import com.lw.error.BusinessException;
import com.lw.error.EmBusinessError;
import com.lw.service.ItemService;
import com.lw.service.OrderService;
import com.lw.service.UserService;
import com.lw.service.model.ItemModel;
import com.lw.service.model.OrderModel;
import com.lw.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 订单模块接口实现
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDaoMapper orderDaoMapper;

    @Autowired
    private SequenceDaoMapper sequenceDaoMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount, Integer promoId) throws BusinessException {
        // 1校验下单状态      ①下单的商品 是否存在     ②用户是否合法    ③购买数量是否正确
        // 此时可以看到 service 独立 的好处了, 也就是所有的 service 互相关联, 互相调用, 所有的逻辑都在service内部去做
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品信息不存在");
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
        }
        // 用户不能 买 <=0 件商品, 也不能买超过 99件商品
        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "数量信息不正确");
        }

        // 校验活动信息
        if (promoId != null) {
            // ①校验对应活动是否存在 这个使用商品
            if (promoId.intValue() != itemModel.getPromoModel().getId()) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息不正确");
                //②校验活动是否进行中
            } else if (itemModel.getPromoModel().getStatus().intValue() != 2) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动还未开始");
            }

        }

        // 2落单减库存
        boolean result = itemService.decreaseStock(itemId,amount);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        // 3订单入库
        OrderModel orderModel = new OrderModel();
        // orderModel对应的流水号, 有自己的生成规则, 故不自增
        // orderModel.setId();  <-- 少了这一步
        orderModel.setUserId(userModel.getId());
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        // orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setPromoId(promoId);
        if (promoId != null) {
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        } else {
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));

        /**
         *  生成交易流水号, 即订单号 --> 此种写法的问题 ①\\@Transactional 会在插入失败后回滚, 且下一个订单会使用失败的sequence
         *  及时插入失败, 也不应该使用重复的 sequence, 这是为了保证全局唯一性的策略
         *  解决方案 generateOrderNo() 开启新的事务, 因为是子事务, 所以会提前提交, 就算回滚了也不会拿到相同sequence
         */
        // 生成交易流水号, 即订单号
        orderModel.setId(generateOrderNo());
        OrderDao orderDao = convertFromOrderModel(orderModel);
        orderDaoMapper.insertSelective(orderDao);

        // 加上商品的销量, 前端显示用刷新重新获取解决
        itemService.increaseSales(itemId, amount);

        // 4返回前端
        return orderModel;
    }
    private OrderDao convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderDao orderDao = new OrderDao();
        BeanUtils.copyProperties(orderModel, orderDao);
        //因为 OrderDO 对应的 ItemPrice 和 OrderPrice 是Double, 而 oderModel是BigDecimal, 所以要人肉处理一下 bigDecimal-->double
        orderDao.setItemPrice(orderModel.getOrderPrice().doubleValue());
        orderDao.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDao;
    }
    // 测试时间生成前8位流水号
    // public static void main(String[] args) {
    //     LocalDateTime now = LocalDateTime.now();
    //     String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
    //     System.out.println(nowDate);
    // }
    // propagation = Propagation.REQUIRES_NEW ：
    // 只要完成generateOrderNo代码块，无论外部程序执行与否，对应的事务都提交掉，对应的sequence都被使用
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateOrderNo() {
        //订单号有16位
        StringBuilder stringBuilder = new StringBuilder();
        //前8位为时间信息, 年月日
        // java8 里边应该使用 LocalDateTime, Date打印不美观, 且SimpleDateFormat时, 线程不安全
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);

        //中间6位为自增序列
        //获取当前sequence
        int sequence = 0;
        SequenceDao sequenceDao = sequenceDaoMapper.getSequenceByName("order_info");
        sequence = sequenceDao.getCurrentValue();
        // //这次拿走了, 下一次就应该, + 步长
        sequenceDao.setCurrentValue(sequenceDao.getCurrentValue() + sequenceDao.getStep());
        sequenceDaoMapper.updateByPrimaryKeySelective(sequenceDao);
        //拼接 凑足6位
        //拼接 凑足6位 --> 此种写法的问题 ①超过了6位最大值, 异常
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);

        //最后2位, 为 **分库分表位** , 暂时写死
        /**
         * 例如, ↓ 拓展
         *  Integer userId = 1000122;
         *  userId % 100
         */
        stringBuilder.append("00");

        return stringBuilder.toString();
    }
}
