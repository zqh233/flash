package com.flash.error;

//设计模式
//包装器业务异常类实现
public class BusinessExecption extends Exception implements CommonError {

    private CommonError commonError;

    //直接接受BusinessExecption传参用于构造业务异常
    public BusinessExecption (CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    //接受自定义的errorMsg方式构造异常
    public BusinessExecption(CommonError commonError, String errorMsg) {
        super();
        this.commonError = commonError;
        this.setErrorMsg(errorMsg);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.commonError.setErrorMsg(errorMsg);
        return this;
    }
}
