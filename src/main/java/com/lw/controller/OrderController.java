package com.lw.controller;

import com.lw.error.BusinessException;
import com.lw.error.EmBusinessError;
import com.lw.response.CommonReturnType;
import com.lw.service.OrderService;
import com.lw.service.model.OrderModel;
import com.lw.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 订单视图控制器
 */
@Controller("order")
@RequestMapping("/order")
@CrossOrigin(
        allowCredentials = "true",
        allowedHeaders = "*"
)
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired

    private HttpServletRequest httpServletRequest;

    //封装下单请求
    @RequestMapping(
            value = "/createOrder",
            method = {RequestMethod.POST},
            consumes = {CONTENT_TYPE_FORMED}
            )
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId") Integer itemId,
                                        @RequestParam(name = "amount") Integer amount,
                                        @RequestParam(name = "promoId", required = false) Integer promoId  // 如果不传则认定为平销价格
                                        ) throws BusinessException {
        //获取用户的登录信息
        Boolean isLogin = (Boolean)this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "用户未登录, 不能下单");
        }
        UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");

        // orderService.createOrder(userModel.getId(), itemId, amount);
        orderService.createOrder(userModel.getId(), itemId, amount, promoId);
        return CommonReturnType.create(null);
    }

}
