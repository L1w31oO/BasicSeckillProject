package com.lw.service.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 秒杀活动实体类
 */
public class PromoModel {
    private Integer id;

    //添加一个 跟 数据库 没有关系的字段
    //秒杀活动状态  1未开始 2进行中 3已结束
    private Integer status;

    //秒杀活动的 名称
    private String promoName;

    //joda-time的功能 --> DateTime

    //秒杀活动的 开始时间
    private DateTime startDate;

    //秒杀活动的 结束时间
    private DateTime endDate;


    //秒杀活动的 适用商品
    private Integer itemId;

    //秒杀活动的 商品价格
    private BigDecimal promoItemPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }
}
