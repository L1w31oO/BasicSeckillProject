package com.lw.dao;

import com.lw.dataObject.UserPasswordDao;
import org.springframework.stereotype.Repository;
/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 用户密码mapper
 */
@Repository
public interface UserPasswordDaoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserPasswordDao record);

    int insertSelective(UserPasswordDao record);

    UserPasswordDao selectByPrimaryKey(Integer id);

    UserPasswordDao selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserPasswordDao record);

    int updateByPrimaryKey(UserPasswordDao record);
}