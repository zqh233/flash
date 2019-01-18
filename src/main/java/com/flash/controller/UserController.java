package com.flash.controller;

import com.flash.Response.CommonReturn;
import com.flash.controller.viewObject.UserVO;
import com.flash.error.BusinessExecption;
import com.flash.error.EmBusinessError;
import com.flash.service.UserService;
import com.flash.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller("user")
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

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
