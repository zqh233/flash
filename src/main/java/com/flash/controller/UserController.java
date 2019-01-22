package com.flash.controller;

import com.flash.Response.CommonReturn;
import com.flash.controller.viewObject.UserVO;
import com.flash.error.BusinessExecption;
import com.flash.error.EmBusinessError;
import com.flash.service.UserService;
import com.flash.service.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//@CrossOrigin解决跨域问题

@Controller("user")
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /***
     * 拥有ThreadLocal，存放了每个线程对应的request，避免了线程不安全
     */
    @Autowired
    private HttpServletRequest httpServletRequest;

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
        logger.info("会员手机号:", telephone);
        logger.info("otpCode为:", otpCode);

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
        UserVO userVO = convertFromModel(userModel);

        //返回通用对象
        return CommonReturn.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel) {
        if(userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }

}
