package com.lw.service.impl;

import com.lw.dao.PromoDaoMapper;
import com.lw.dataObject.PromoDao;
import com.lw.service.ItemService;
import com.lw.service.PromoService;
import com.lw.service.model.ItemModel;
import com.lw.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 秒杀活动模块接口实现
 */
@Service
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoDaoMapper promoDaoMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {

        //获取对应商品的秒杀信息
        PromoDao promoDao = promoDaoMapper.selectByItemId(itemId);

        //dataObject --> model
        PromoModel promoModel = convertModelFromDataObject(promoDao);
        if (promoDao == null) {
            return null;
        }

        //判断当前时间 是否 秒杀活动 即将开始, 或 正在进行
        DateTime dateTime = new DateTime();
        if (promoModel.getStartDate().isAfterNow()) {
            promoModel.setStatus(1);
        } else if (promoModel.getEndDate().isBeforeNow()) {
            promoModel.setStatus(3);
        } else {
            promoModel.setStatus(2);
        }

        return promoModel;
    }

    // @Override
    // public void publishPromo(Integer promoId) {
    //     //通过活动id获取活动
    //     PromoDao promoDao = promoDaoMapper.selectByPrimaryKey(promoId);
    //     if(promoDao.getItemId() == null || promoDao.getItemId().intValue() == 0){
    //         return;
    //     }
    //     ItemModel itemModel = itemService.getItemById(promoDao.getItemId());
    //
    //     //将库存同步到redis内
    //     redisTemplate.opsForValue().set("promo_item_stock_"+itemModel.getId(), itemModel.getStock());
    // }

    private PromoModel convertModelFromDataObject(PromoDao promoDao) {
        if (promoDao == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDao, promoModel);
        promoModel.setStartDate(new DateTime(promoDao.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDao.getEndDate()));
        promoModel.setPromoItemPrice(new BigDecimal(promoDao.getPromoItemPrice()));
        return promoModel;
    }
}
