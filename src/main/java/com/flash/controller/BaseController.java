package com.flash.controller;

import com.flash.Response.CommonReturn;
import com.flash.error.BusinessExecption;
import com.flash.error.EmBusinessError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    /**
     *  定义execptionhandler解决未被controller层吸收的Execption
     *
     *  备注  ResponseBody   会直接将enum类型转换为未知错误
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object HandlerExecption(HttpServletRequest request, Exception ex) {
        Map<String, Object> responseData = new HashMap<>();
        if(ex instanceof BusinessExecption) {
            BusinessExecption businessExecption = (BusinessExecption) ex;
            responseData.put("errorCode", businessExecption.getErrorCode());
            responseData.put("errorMsg", businessExecption.getErrorMsg());
        } else {
            responseData.put("errorCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errorMsg", EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
        }
        return CommonReturn.create(responseData,"fail");
    }
}
