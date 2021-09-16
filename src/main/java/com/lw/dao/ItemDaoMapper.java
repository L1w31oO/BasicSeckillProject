package com.lw.dao;

import com.lw.dataObject.ItemDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 商品mapper
 */
@Repository
public interface ItemDaoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ItemDao record);

    int insertSelective(ItemDao record);

    List<ItemDao> listItem();

    ItemDao selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemDao record);

    int updateByPrimaryKey(ItemDao record);

    int increaseSales(@Param("id") Integer id, @Param("amount") Integer amount);
}