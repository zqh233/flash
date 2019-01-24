package com.flash.service.impl;

import com.flash.dao.UserDOMapper;
import com.flash.dao.UserPasswordDOMapper;
import com.flash.dataObject.UserDO;
import com.flash.dataObject.UserPasswordDO;
import com.flash.error.BusinessExecption;
import com.flash.error.EmBusinessError;
import com.flash.service.UserService;
import com.flash.service.model.UserModel;
import com.flash.validator.ValidationResult;
import com.flash.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserDOMapper userDOMapper;
  @Autowired
  private UserPasswordDOMapper userPasswordDOMapper;
  @Autowired
  private ValidatorImpl validator;

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
    /*if(StringUtils.isEmpty(userModel.getName())
        || userModel.getGender() == null
        || userModel.getAge() == null
        || StringUtils.isEmpty(userModel.getTelphone())
        || StringUtils.isEmpty(userModel.getEncrptPassword())) {
      throw new BusinessExecption(EmBusinessError.PARAMETER_VALIDATION_ERROR);
    }*/
    //优化校验为空
      ValidationResult result = validator.validate(userModel);
    if(result.getHasErrors()) {
        throw new BusinessExecption(EmBusinessError.PARAMETER_VALIDATION_ERROR);
    }

    //实现model-》dataObject
    UserDO userDO = UserDO.convertFromModel(userModel);
    try {
      userDOMapper.insertSelective(userDO);
    } catch (Exception e) {
      throw new BusinessExecption(EmBusinessError.TELPHOE_EXIST,result.getErrMsg());
    }
    userModel.setId(userDO.getId());

    UserPasswordDO userPasswordDO = UserPasswordDO.convertPasswordFromModel(userModel);
    userPasswordDOMapper.insertSelective(userPasswordDO);
  }

  @Override
  public UserModel validateLogin(String telphone, String encrptPassword) throws Exception {
      //获取用户信息
      UserDO userDO = userDOMapper.selectByTelphone(telphone);
      if(userDO == null) {
          throw new BusinessExecption(EmBusinessError.USER_LOGIN_ERROR);
      }
      //获取用户密码
      UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

      UserModel userModel = UserModel.convertFromDataObject(userDO, userPasswordDO);
      if(!StringUtils.equals(encrptPassword, userModel.getEncrptPassword())) {
          throw new BusinessExecption(EmBusinessError.USER_LOGIN_ERROR);
      }
      return userModel;
  }

}
