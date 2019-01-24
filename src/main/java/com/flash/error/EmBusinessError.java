package com.flash.error;

public enum EmBusinessError implements CommonError
{
    //通用错误类型
    PARAMETER_VALIDATION_ERROR(100001, "参数不合法"),
    //未知错误
    UNKNOWN_ERROR(100002,"未知错误"),

    USER_NOT_EXIST(200001, "用户不存在"),
    TELPHOE_EXIST(200002, "手机号已存在"),
    USER_LOGIN_ERROR(200003, "用户注册账号或密码错误"),

    ITEM_NOT_EXIST(200101,"商品不存在"),
    ITEM_STOCK_NOT_EXIST(200102,"商品库存不存在")
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
