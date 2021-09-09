package com.lw.service;

import com.lw.service.model.PromoModel;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 秒杀活动模块接口
 */
public interface PromoService {
    // 根据itemId获取正在进行或者即将进行的秒杀活动
    PromoModel getPromoByItemId(Integer itemId);
}
