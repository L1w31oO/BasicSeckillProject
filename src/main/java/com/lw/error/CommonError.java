package com.lw.error;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 通用错误信息接口
 */
public interface CommonError {
    public int getErrCode();

    public String getErrMsg();

    public CommonError setErrMsg(String errMsg);
}
