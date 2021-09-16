package com.lw.dataObject;

import java.util.Date;
/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description
 */
public class PromoDao {

    private Integer id;

    private String promoName;

    private Date startDate;

    private Date endDate;

    private Integer itemId;

    private Double promoItemPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Double getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(Double promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }
}