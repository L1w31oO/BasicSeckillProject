package com.lw.dao;

import com.lw.dataObject.OrderDao;
import org.springframework.stereotype.Repository;
/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 订单mapper
 */
@Repository
public interface OrderDaoMapper {

    int deleteByPrimaryKey(String id);

    int insert(OrderDao record);

    int insertSelective(OrderDao record);

    OrderDao selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderDao record);

    int updateByPrimaryKey(OrderDao record);

}