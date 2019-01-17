package com.flash.Response;

public class CommonReturn {
    //表明返回值 success或者fail
    private String status;

    //返回数据
    //若为错误返回通用错误码格式
    private Object data;

    //通用的构建方法
    public static CommonReturn create(Object result){
        return CommonReturn.create(result, "success");
    }


    public static CommonReturn create(Object result, String status){
        CommonReturn type = new CommonReturn();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
