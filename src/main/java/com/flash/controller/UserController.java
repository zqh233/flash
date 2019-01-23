package com.flash.controller;

import com.alibaba.druid.util.StringUtils;
import com.flash.Response.CommonReturn;
import com.flash.Utils.MD5;
import com.flash.controller.viewObject.UserVO;
import com.flash.error.BusinessExecption;
import com.flash.error.EmBusinessError;
import com.flash.service.UserService;
import com.flash.service.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

//@CrossOrigin解决跨域问题
//未写注解@ResponseBody会造成ajax请求发起了，但是前端接受返回报404错误

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /***
     * 拥有ThreadLocal，存放了每个线程对应的request，避免了线程不安全
     */
    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturn login(@RequestParam(name = "telphone") String telphone, @RequestParam(name="password") String password) throws Exception{
        if(StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)) {
            throw  new BusinessExecption(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserModel userModel = userService.validateLogin(telphone, MD5.encodeByMD5(password));

        //将用户登录凭证加入到用户登录的session里
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);

        //用户登录服务，用来检验用户登录是否合法
        return CommonReturn.create(null);
    }

    /****
     * 用户注册借口
     * 跨越请求问题：ajax请求不能对session共享
     * crossOrigion无法对session共享，共享需要指定范围(设置allowCredentials,allowedHeaders)
     * ajax请求中需要增加   xhrFields:{withCredentials:true}   解决跨域
     */
    @RequestMapping(value="/register",method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturn register(@RequestParam(name="name") String name,@RequestParam(name = "telphone") String telphone,
                                 @RequestParam(name="otpCode") String otpCode,
                                 @RequestParam(name = "age") Integer age,
                                 @RequestParam(name="gender")Integer gender,
                                 @RequestParam(name="password")String password) throws Exception {

        //验证手机号和otpcode相符合
        String isSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if(!StringUtils.equals(otpCode,isSessionOtpCode)) {
            throw new BusinessExecption(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码错误");
        }
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("bytelphone");
        userModel.setEncrptPassword(MD5.encodeByMD5(password));
        userService.register(userModel);
        return CommonReturn.create(null);
    }



    /****
     * 用户获取otp短信
     * @param telephone
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getotp",method={RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturn getOtp(@RequestParam(name="telphone")String telephone) throws Exception{
        //根据需要的规则生成Otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(999999);
        randomInt += 100000;
        String otpCode = String.valueOf(randomInt);
        //将Otp验证码和用户手机号进行关联,使用httpSession的方式绑定手机号和otpCode
        httpServletRequest.getSession().setAttribute(telephone, otpCode);

        //将otp验证码通过短信通道发给用户，需要短信通道（没钱买）
        /*System.out.println(telephone);
        System.out.println(otpCode);*/
        logger.info("会员手机号:{}", telephone);
        logger.info("otpCode为:{}", otpCode);

        return CommonReturn.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturn getUser(@RequestParam(name = "id") Integer id) throws Exception{
        //根据service层根据id获取信息返回前端
        UserModel userModel = userService.getUserById(id);
        if(userModel == null) {
            throw new BusinessExecption(EmBusinessError.USER_NOT_EXIST);
        }

        //将核心领域模型对象转换为可拱UI使用的viewObject
        UserVO userVO = UserVO.convertFromModel(userModel);
        //返回通用对象
        return CommonReturn.create(userVO);
    }


}
