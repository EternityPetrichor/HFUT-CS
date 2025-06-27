package com.base.manager.response;

import com.base.manager.common.IStatusMessage;

import java.io.Serializable;


public class ResponseResult implements Serializable{

    private String code;
    private String message;
    private Object obj;

    public ResponseResult() {
        this.code = IStatusMessage.SystemStatus.SUCCESS.getCode();
        this.message = IStatusMessage.SystemStatus.SUCCESS.getMessage();
    }

    public ResponseResult(IStatusMessage statusMessage){
        this.code = statusMessage.getCode();
        this.message = statusMessage.getMessage();

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override public String toString() {
        return "ResponseResult{" + "code='" + code + '\'' + ", message='"
                + message + '\'' + ", obj=" + obj + '}';
    }

}
