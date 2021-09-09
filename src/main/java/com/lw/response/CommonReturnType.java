package com.lw.response;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 通用的请求响应类型
 */
public class CommonReturnType {
    // 表明对应请求的 返回结果 "Success" 或 "Fail"
    private String status;
    // 若status = "success", 则data内, 返回前端需要的json数据
    // 若status = "fail", 则data内 返回 通用的错误码格式.
    private Object data;

    //定义一个通用的创建方法
    public static CommonReturnType create(Object result) {
        // 重载 (Object) 调用 (Object, String)
        //如果 CommonReturnType.create() 不带任何status的话, 则直接添加Success的status,并返回
        return CommonReturnType.create(result, "Success");
    }

    public static CommonReturnType create(Object result, String status) {
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
