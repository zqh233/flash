package com.flash.service.impl;

import com.flash.dao.UserDOMapper;
import com.flash.dao.UserPasswordDOMapper;
import com.flash.dataObject.UserDO;
import com.flash.dataObject.UserPasswordDO;
import com.flash.error.BusinessExecption;
import com.flash.error.EmBusinessError;
import com.flash.service.UserService;
import com.flash.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    return UserModel.convertFromDataObject(userDO, userPasswordDO);
  }

  @Override
  @Transactional
  public void register(UserModel userModel) throws Exception {
    if(userModel == null) {
      throw new BusinessExecption(EmBusinessError.PARAMETER_VALIDATION_ERROR);
    }
    if(StringUtils.isEmpty(userModel.getName())
        || userModel.getGender() == null
        || userModel.getAge() == null
        || StringUtils.isEmpty(userModel.getTelphone())
        || StringUtils.isEmpty(userModel.getEncrptPassword())) {
      throw new BusinessExecption(EmBusinessError.PARAMETER_VALIDATION_ERROR);
    }

    //实现model-》dataObject
    UserDO userDO = UserDO.convertFromModel(userModel);
    try {
      userDOMapper.insertSelective(userDO);
    } catch (Exception e) {
      throw new BusinessExecption(EmBusinessError.TELPHOE_EXIST);
    }


    userModel.setId(userDO.getId());

    UserPasswordDO userPasswordDO = UserPasswordDO.convertPasswordFromModel(userModel);
    userPasswordDOMapper.insertSelective(userPasswordDO);
  }

}
