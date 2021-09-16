package com.lw.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.lw.dao.UserDaoMapper;
import com.lw.dao.UserPasswordDaoMapper;
import com.lw.dataObject.UserDao;
import com.lw.dataObject.UserPasswordDao;
import com.lw.error.BusinessException;
import com.lw.error.EmBusinessError;
import com.lw.service.model.UserModel;
import com.lw.service.UserService;
import com.lw.validator.ValidationResult;
import com.lw.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 用户模块接口实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDaoMapper userDaoMapper;

    @Autowired
    private UserPasswordDaoMapper userPasswordDaoMapper;

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public UserModel getUserById(Integer id) {
        // 调用userDaoMapper获取到对应用户的daoObject
        UserDao userDao = userDaoMapper.selectByPrimaryKey(id);
        if (userDao == null) {
            return null;
        }
        /**
         *  UserPasswordDao userPasswordDao = userPasswordDaoMapper.**selectByPrimaryKey**(userDao.getId());
         *  mybatis帮我们生成的方法里边, 仅仅只有 selectByPrimaryKey ,
         *  但是业务逻辑要求 通过对应用户id ,查询到加密的密码.
         *  userPassword表中, 各字段分别是 (主键)id, encrypt_password, user_id
         *  所以, 改造一下 UserPasswordDaoMapper.xml
         */
        //通过 用户id 获取对应的用户加密密码信息
        UserPasswordDao userPasswordDao = userPasswordDaoMapper.selectByUserId(userDao.getId());
        return convertFromDataObject(userDao, userPasswordDao);
    }

    @Override
    // 注册用户使用事务
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // if (StringUtils.isEmpty(userModel.getName())
        //        || userModel.getGender() == null
        //        || userModel.getAge() == null
        //        || StringUtils.isEmpty(userModel.getTelephone())) {
        //    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        // }
        /**
         *  校验过于繁琐, pom引入hibernate的 validator, 并新建 validator.ValidationResult + validator.ValidatorImpl
         *  以
         */
        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }
        // 实现model->dataObject方法
        // 为什么使用 insertSelective 而不是 insert 方法?
        // insertSelective, 如果字段为null则不更新这个字段, 讲了一段设计数据库的经验, 没大听懂.
        UserDao userDao = convertFromModel(userModel);
        try {
            userDaoMapper.insertSelective(userDao);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号已重复注册 ");
        }

        //获取userid, 以便存入userPasswordDao
        userModel.setId(userDao.getId());
        UserPasswordDao userPasswordDao = convertPasswordFromModel(userModel);
        userPasswordDaoMapper.insertSelective(userPasswordDao);
    }

    @Override
    public UserModel getUserByIdInCache(Integer id) {
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get("user_validate_"+id);
        if(userModel == null){
            userModel = this.getUserById(id);
            redisTemplate.opsForValue().set("user_validate_"+id, userModel);
            redisTemplate.expire("user_validate_"+id, 10, TimeUnit.MINUTES);
        }
        return userModel;
    }

    @Override
    public UserModel validationLogin(String telephone, String encryptPassword) throws BusinessException {
        //通过用户的手机 获取用户信息
        UserDao userDao = userDaoMapper.selectByTelephone(telephone);
        if (userDao == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDao userPasswordDO = userPasswordDaoMapper.selectByUserId(userDao.getId());
        UserModel userModel = convertFromDataObject(userDao, userPasswordDO);

        //加密密码 比对
        if (!StringUtils.equals(encryptPassword, userModel.getEncrptPassword())) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    private UserDao convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserDao userDao = new UserDao();
        BeanUtils.copyProperties(userModel, userDao);
        return userDao;
    }

    private UserPasswordDao convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDao userPasswordDao = new UserPasswordDao();
        userPasswordDao.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDao.setUserId(userModel.getId());
        return userPasswordDao;
    }

    private UserModel convertFromDataObject(UserDao userDao, UserPasswordDao userPasswordDao) {
        //处理一下空的问题
        if (userDao == null) {
            return null;
        }

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDao, userModel);

        if (userPasswordDao != null) {
            //这里不能使用 copyProperties了, 因为对应内部还有 重名字段, 所以用 简单的set
            userModel.setEncrptPassword(userPasswordDao.getEncrptPassword());
        }
        return userModel;
    }
}
