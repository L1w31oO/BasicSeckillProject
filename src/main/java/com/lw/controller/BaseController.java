package com.lw.controller;

import com.lw.error.BusinessException;
import com.lw.error.EmBusinessError;
import com.lw.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 基本视图控制器
 */
public class BaseController {

    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    /**
     * SpringBoot 提供了
     * 定义exceptionHandler解决未被controller层吸收的exception
     * 根据OOP方式, 把公共的类从UserController, 提取到基类 BaseController 里, 然后让 UserController 去继承 BaseController
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest request, Exception e){
        Map<String, Object> responseData = new HashMap<>();  // 提出公共代码
        //CommonReturnType commonReturnType = new CommonReturnType();  // 可简化
        //commonReturnType.setStatus("fail");  // 可简化
        //commonReturnType.setData(ex);
        /**
         * 由于 返回的json的data中的ex的错误信息, 是 stackTrace + BusinessException,
         * 从 UserController.getUser() 也可知抛出的是 BusinessException
         * 故强转 BusinessException， 取出 errCode 和 errMsg，并塞回 commonReturnType.setData()
         */
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException)e;
            responseData.put("errCode", businessException.getErrCode());
            responseData.put("errMsg", businessException.getErrMsg());
            //commonReturnType.setData(responseData);//可简化
        } else {
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData,"Fail");
    }
}
