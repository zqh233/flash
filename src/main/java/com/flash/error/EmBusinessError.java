package com.flash.error;

public enum EmBusinessError implements CommonError
{
    //通用错误类型
    PARAMETER_VALIDATION_ERROR(100001, "参数不合法"),
    //未知错误
    UNKNOWN_ERROR(100002,"未知错误"),

    USER_NOT_EXIST(200001, "用户不存在")
    ;

    private int errorCode;

    private String errorMsg;

    EmBusinessError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
