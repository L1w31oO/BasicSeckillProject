package com.lw.error;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 包装器 业务异常类 实现
 */

public class BusinessException  extends Exception implements CommonError{

    private CommonError commonError;

    // 直接接受 EmBusinessError的传参, 构造业务异常
    public BusinessException(CommonError commonError) {
        //调用 父类 Exception自身初始化机制
        super();
        this.commonError = commonError;
    }

    // 接收 自定义errMsg的方式, 构造业务异常
    public BusinessException(CommonError commonError, String errMsg) {
        super();
        this.commonError = commonError;
        // 二次改写 errMsg
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
