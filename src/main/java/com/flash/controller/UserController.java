package com.flash.controller;

import com.flash.controller.viewObject.UserVO;
import com.flash.service.UserService;
import com.flash.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("user")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    @ResponseBody
    public UserVO getUser(@RequestParam(name = "id") Integer id) {
        //根据service层根据id获取信息返回前端
        UserModel userModel = userService.getUserById(id);
        //将核心领域模型对象转换为可拱UI使用的viewObject
        return convertFromModel(userModel);
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
