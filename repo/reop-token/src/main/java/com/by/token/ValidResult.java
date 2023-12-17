package com.by.token;

import java.util.Map;

public class ValidResult {
    int code;//验证状态 1成功 0失败
    String msg;//消息描述
    Map payload;

    public ValidResult() {
    }

    public ValidResult(int code, String msg, Map payload) {
        this.code = code;
        this.msg = msg;
        this.payload = payload;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map getPayload() {
        return payload;
    }

    public void setPayload(Map payload) {
        this.payload = payload;
    }
}
