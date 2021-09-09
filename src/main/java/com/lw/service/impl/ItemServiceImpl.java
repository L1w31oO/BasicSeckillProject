package com.lw.service.impl;

import com.lw.dao.ItemDaoMapper;
import com.lw.dao.ItemStockDaoMapper;
import com.lw.dataObject.ItemDao;
import com.lw.dataObject.ItemStockDao;
import com.lw.error.BusinessException;
import com.lw.error.EmBusinessError;
import com.lw.service.ItemService;
import com.lw.service.PromoService;
import com.lw.service.model.ItemModel;
import com.lw.service.model.PromoModel;
import com.lw.validator.ValidationResult;
import com.lw.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 商品模块接口实现
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDaoMapper itemDaoMapper;

    @Autowired
    private ItemStockDaoMapper itemStockDaoMapper;

    @Autowired
    private PromoService promoService;

    @Override
    // 创建一个Item必须在一个事务当中
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        // 校验入参
        ValidationResult result = validator.validate(itemModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        //转化 itemModel -> dataObject
        ItemDao itemDao = this.convertItemDaoFromItemModel(itemModel);

        //写入数据库
        itemDaoMapper.insertSelective(itemDao);
        itemModel.setId(itemDao.getId());
        ItemStockDao itemStockDO = this.convertItemStockDaoFromItemModel(itemModel);
        itemStockDaoMapper.insertSelective(itemStockDO);

        //返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }
    private ItemDao convertItemDaoFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemDao itemDao = new ItemDao();
        BeanUtils.copyProperties(itemModel, itemDao);
        itemDao.setPrice(itemModel.getPrice().doubleValue());
        return itemDao;
    }
    private ItemStockDao convertItemStockDaoFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemStockDao itemStockDao = new ItemStockDao();
        itemStockDao.setItemId(itemModel.getId());
        itemStockDao.setStock(itemModel.getStock());
        return itemStockDao;
    }

    @Override
    public List<ItemModel> listItems() {
        List<ItemDao> itemDOList = itemDaoMapper.listItem();
        // java8的StreamAPI
        List<ItemModel> itemModelList = itemDOList.stream().map(itemDao -> {
            ItemStockDao itemStockDao = itemStockDaoMapper.selectByItemId(itemDao.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemDao, itemStockDao);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDao itemDao = itemDaoMapper.selectByPrimaryKey(id);
        if (itemDao == null) {
            return null;
        }
        //操作获取库存数量
        ItemStockDao itemStockDao = itemStockDaoMapper.selectByItemId(id);

        //将 dataObject -> model
        // ItemModel itemModel = this.convertModelFromDataObject(itemDao, itemStockDao);
        ItemModel itemModel = convertModelFromDataObject(itemDao, itemStockDao);

        // //获取活动商品信息
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        //如果 秒杀存在, 且未结束, 则添加秒杀信息
        if (promoModel != null && promoModel.getStatus().intValue() != 3) {
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }
    private ItemModel convertModelFromDataObject(ItemDao itemDao, ItemStockDao itemStockDao) {
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDao, itemModel);
        itemModel.setPrice(new BigDecimal(itemDao.getPrice()));
        itemModel.setStock(itemStockDao.getStock());
        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        //加锁到 item_stock, 此时能够看到 把stock抽出来的好处, 由于item常用于展示, 所以对 item_stock表的操作, 不会影响到 item表的 展示
        /**
         * 新手程序员, 容易 写成2次sql, 一次取 stock, 再根据 amount< stock, 再update. 以下这么写可以在插入的同时, 检查插入条件
         * <update id="decreaseStock" >   <-- src\main\resources\mapping\ItemStockDaoMapper.xml
         *     update item_stock
         *     set stock = stock - #{amount}
         *     where item_id = #{itemId} and stock >= #{amount}
         *   </update>
         */
        int affectedRow = itemStockDaoMapper.decreaseStock(itemId, amount);
        if (affectedRow > 0) {
            // 更新库存成功
            return true;
        } else {
            // 更新库存失败
            return false;
        }
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemDaoMapper.increaseSales(itemId, amount);
    }
}
