package com.lw.dao;

import com.lw.dataObject.UserDao;
import org.springframework.stereotype.Repository;
/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 用户mapper
 */
@Repository
public interface UserDaoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserDao record);

    int insertSelective(UserDao record);

    UserDao selectByTelephone(String telephone);

    UserDao selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserDao record);

    int updateByPrimaryKey(UserDao record);
}