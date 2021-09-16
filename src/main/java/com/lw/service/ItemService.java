package com.lw.service;

import com.lw.error.BusinessException;
import com.lw.service.model.ItemModel;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 商品模块接口
 */

public interface ItemService {
    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表浏览
    List<ItemModel> listItems();

    //商品详情浏览
    ItemModel getItemById(Integer id);

    // item及promo model缓存模型
    ItemModel getItemByIdInCache(Integer id);

    //库存扣减
    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;

    //库存回补
    boolean increaseStock(Integer itemId, Integer amount) throws BusinessException;

    //商品销量增加
    void increaseSales(Integer itemId, Integer amount) throws BusinessException;
}
