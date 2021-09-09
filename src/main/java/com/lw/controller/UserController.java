package com.lw.controller;

import com.lw.controller.viewObject.UserVO;
import com.lw.error.BusinessException;
import com.lw.error.EmBusinessError;
import com.lw.response.CommonReturnType;
import com.lw.service.UserService;
import com.lw.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 用户视图控制器
 */
@Controller("user")
@RequestMapping("/user")
// 后端跨域授信
@CrossOrigin(
        allowCredentials = "true",
        allowedHeaders = "*"
)

public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    //redis替代httpServletRequest
    //@Autowired
    //private HttpServletRequest httpServletRequest;

    @RequestMapping(
            value = "/login",
            method = {RequestMethod.POST},
            consumes = {CONTENT_TYPE_FORMED}
            )
    @ResponseBody
    // 用户登录接口
    public CommonReturnType login(@RequestParam(name = "telephone") String telephone,
                                  @RequestParam(name = "password") String password,
                                  HttpServletRequest request)
            throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        // 入参检验
        if (org.apache.commons.lang3.StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号或者密码为空");
        }

        // 用户登录服务, 校验 用户登录是否合法
        UserModel userModel = userService.validationLogin(telephone, this.EncodeByMd5(password));

        // 将登陆凭证加入到用户登陆成功的session内
        request.getSession().setAttribute("IS_LOGIN", true);
        request.getSession().setAttribute("LOGIN_USER", userModel);

        return CommonReturnType.create(null);
    }


    // 用户注册接口
    @RequestMapping(
            value = "/register",
            method = {RequestMethod.POST},
            consumes = {CONTENT_TYPE_FORMED}
            )
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telephone") String telephone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "password") String password,
                                     @RequestParam(name = "gender") Byte gender,
                                     @RequestParam(name = "age") Integer age,
                                     HttpServletRequest request)
            throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        // 1验证 手机号 和 对应的optCode 相符
        /**
         * inSessionOtpCode 实际使用中为 null , 为什么呢?
         * 因为　＠CrossOrigin　对ajax 无法对session共享
         * 看 ＠CrossOrigin 源码  -->     boolean DEFAULT_ALLOW_CREDENTIALS = false;
         *    DEFAULT_ALLOW_CREDENTIALS = ture, 需要配合前端设置xhrFields授信后使得跨域session共享
         */
        String inSessionOtpCode = (String) request.getSession().getAttribute(telephone);

        /**
         * if (a == null) {
         *     return b == null;
         * } else {
         *     return a.equals(b);
         * }
         */
        if (!StringUtils.equals(otpCode, inSessionOtpCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不正确");
        }

        // 2用户注册流程
        UserModel userModel = new UserModel();

        userModel.setName(name);

        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setTelephone(telephone);
        userModel.setRegisterMode("byphone");

        // MD5Encoder.encode(password.getBytes()) --> EncodeByMd5()
        userModel.setEncrptPassword(this.EncodeByMd5(password));
        // userModel.setEncryptPassword(MD5Encoder.encode(password.getBytes()));
        userService.register(userModel);

        // 将登陆凭证加入到用户登陆成功的session内, 注册等同于登录
        request.getSession().setAttribute("IS_LOGIN", true);
        request.getSession().setAttribute("LOGIN_USER", userModel);

        return CommonReturnType.create(null);
    }

    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        // 确定一个计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();

        // 加密字符串
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));

        return newStr;
    }

    // 用户获取otp短信的接口
    @RequestMapping(
            value = "/getotp",
            method = {RequestMethod.POST},
            consumes = {CONTENT_TYPE_FORMED}
    )
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telephone") String telephone, HttpServletRequest request) {

        // 1 需要按照 一定规则 生成 otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);    // 范围[0,99999)

        randomInt += 10000;

        String otpCode = String.valueOf(randomInt);

        // 2 将OTP验证码同对应用户的手机号关联 (分布式中,redis天然适用于otp与telephone对应,
        // 此处由于没有用到分布式, 适用httpsession绑定opt与telephone
        request.getSession().setAttribute(telephone, otpCode);

        /**
         *  企业项目中一般是log4j,此处图方便
         *  且用户敏感信息, 不应该暴露给企业端
         */
        // 3 将OTP验证码通过短信通道发送给用户, 省略, 有兴趣可以买第三方服务
        System.out.println("telephone = " + telephone + " & optCode = " + otpCode);

        //个人添加, 仅仅为了显示 验证码在前端, 毕竟没有买短信服务
        UserVO userVO = new UserVO();
        userVO.setTempUsefulOtpCode(otpCode);
        return CommonReturnType.create(userVO);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        // 调用service服务获取对应id 的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        // 若获取到的用户信息不存在
        if (userModel == null) {
            // userModel.setEncryptPassword("123");  // 测试未知错误
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        // 将核心领域模型用户对象, 转化为 可供UI使用的viewObject
        UserVO userVO = convertFromModel(userModel);

        // 返回通用对象
        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }

        UserVO userVO = new UserVO();

        BeanUtils.copyProperties(userModel, userVO);

        return userVO;
    }
}
