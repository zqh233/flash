package com.flash.service;

import com.flash.service.model.UserModel;

public interface UserService {

    //根据用户id获取对象方法
    UserModel getUserById(Integer id);
}
