package com.flash.service;

import com.flash.error.BusinessExecption;
import com.flash.service.model.UserModel;

public interface UserService {

    //根据用户id获取对象方法
    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws Exception;

    UserModel validateLogin(String telphone, String encrptPassword) throws Exception;
}
