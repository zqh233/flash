package com.flash.service.impl;

import com.flash.dao.UserDOMapper;
import com.flash.dao.UserPasswordDOMapper;
import com.flash.dataObject.UserDO;
import com.flash.dataObject.UserPasswordDO;
import com.flash.service.UserService;
import com.flash.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Autowired private UserDOMapper userDOMapper;
  @Autowired private UserPasswordDOMapper userPasswordDOMapper;

  @Override
  public UserModel getUserById(Integer id) {
    // 调用mapper获取对应的用户
    UserDO userDO = userDOMapper.selectByPrimaryKey(id);
    if(userDO == null) {
        return null;
    }
    UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
    return convertFromDataObject(userDO, userPasswordDO);
  }

  private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
    if (userDO == null) {
      return null;
    }
    UserModel userModel = new UserModel();
    BeanUtils.copyProperties(userDO, userModel);
    if (userPasswordDO != null) {
      userModel.setEncrptPassword(userPasswordDO.getEncrpt());
    }
    return userModel;
  }
}
