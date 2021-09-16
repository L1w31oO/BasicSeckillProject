package com.lw.service.model;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 订单实体类
 */
public class OrderModel {
    //2018102100012828
    //orderModel对应的流水号, 有自己的生成规则, 故不自增
    private String id;

    // 用户Id
    private Integer userId;

    //购买的 商品id
    private Integer itemId;

    //若promo非空, 则表示秒杀商品价格
    //购买的 商品 的 下单时的 单价, 以后原商品价格变化, 订单中的这个价格不会变
    private BigDecimal itemPrice;

    //购买金额
    private BigDecimal orderPrice;

    //购买数量
    private Integer amount;

    //若非空, 则表示是以秒杀商品方式下单
    private Integer promoId;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }
}
