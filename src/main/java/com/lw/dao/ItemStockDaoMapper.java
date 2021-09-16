package com.lw.dao;

import com.lw.dataObject.ItemStockDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 商品库存mapper
 */
@Repository
public interface ItemStockDaoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDao record);

    int insertSelective(ItemStockDao record);

    ItemStockDao selectByItemId(Integer itemId);

    ItemStockDao selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemStockDao record);

    int updateByPrimaryKey(ItemStockDao record);

    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}