package com.by.common.exception;

import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

/**
 * 自定义异常 用于后端验证 未通过时抛出的异常
 */
public class ValidateException extends Exception {

    //接收异常列表信息
    List<Map> list;

    public ValidateException(List list) {
        super("验证失败");
        this.list = list;
    }


    public List<Map> getList() {
        return list;
    }
}
