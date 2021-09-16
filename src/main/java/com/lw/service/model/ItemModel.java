package com.lw.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 商品实体类
 */
public class ItemModel implements Serializable {

    private Integer id;

    //商品名
    @NotBlank(message = "商品名称不能为空")
    private String title;

    @NotNull(message = "商品价格不能为空")
    @Min(value = 0, message = "商品价格必须大于0")
    private BigDecimal price;

    //商品的库存
    @NotNull(message = "库存必须填写")
    private Integer stock;

    /**
     * mysql数据库中, 有个 stock字段
     * //商品库存虽是一对一, 但后面考虑到商品库存是跟交易流水相关的, 就是说每次对商品表的操作就是对库存表的操作,
     * 以后会用到分库分表的一些策略, 以及一些数据问题, 那库存字段最好跟user_password设计思想一样, 拆到另一张表里面,
     * 以便于我们以后做一些性能优化以及水平的拆分
     */
    @NotBlank(message = "商品描述信息不能为空")
    private String Description;

    /**
     * 考虑一下sales表, 应该跟着哪一张表去走. 销量对应到商品模型之中, 它是属于当交易行为发生之后所产生的一个计数的累加,
     * 那么对应于这个sales, 是交易模型过来之后累加, 还是独立出另外一张表, 每次去求和呢? 暂且用于展示
     * 销量暂时放在item表里边, 当交易行为之后, 我们通过异步的方式, 给对应的item的销量值 +1, 而不会影响 下单主链路, 因此暂时把销量 放在这个位置.
     *
     */
    //商品的销量 销量不是创建的时候传进来的, 而是我们通过其他方式统计进来的
    private Integer sales;

    //描述商品的图片
    @NotBlank(message = "商品图片信息不能为空")
    private String imgUrl;

    //使用聚合模型, 如果 promoModel不为空, 则表示其拥有 还未结束的秒杀活动
    private PromoModel promoModel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public PromoModel getPromoModel() {
        return promoModel;
    }

    public void setPromoModel(PromoModel promoModel) {
        this.promoModel = promoModel;
    }
}
