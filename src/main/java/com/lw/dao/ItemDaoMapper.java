package com.lw.dao;

import com.lw.dataObject.ItemDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDaoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Sep 07 11:44:56 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Sep 07 11:44:56 CST 2021
     */
    int insert(ItemDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Sep 07 11:44:56 CST 2021
     */
    int insertSelective(ItemDao record);

    List<ItemDao> listItem();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Sep 07 11:44:56 CST 2021
     */
    ItemDao selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Sep 07 11:44:56 CST 2021
     */
    int updateByPrimaryKeySelective(ItemDao record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Sep 07 11:44:56 CST 2021
     */
    int updateByPrimaryKey(ItemDao record);

    int increaseSales(@Param("id") Integer id, @Param("amount") Integer amount);
}