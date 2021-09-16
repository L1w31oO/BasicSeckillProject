package com.lw.error;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 错误信息枚举
 */
public enum EmBusinessError implements CommonError{
    //通用错误类型10000
    // PARAMETER_VALIDATION_ERROR, 参数不合法统一使用, 此通用错误码, 区别在于 errMsg里的信息
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),

    //20000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001,"用户不存在"),
    //这个信息 USER_LOGIN_FAIL 账户密码不能提示得太明显, 如果明确告诉它 手机号 或者 密码 不存在的话, 会被人异常攻击
    USER_LOGIN_FAIL(20002,"用户手机号或者密码不正确"),
    USER_NOT_LOGIN(20003,"用户未登录"),

    //30000开头为交易信息错误定义
    STOCK_NOT_ENOUGH(30001,"库存不足")
    ;

    //USER_NOT_EXIST 需要一个构造函数, 否则会报错
    private EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    //枚举类, 本质是一个普通java类, 所以可以有成员变量
    private int errCode;
    private String errMsg;
    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
