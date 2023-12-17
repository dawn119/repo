package com.by.common.controller;

import com.by.common.bean.ResultBean;
import com.by.common.exception.ValidateException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController extends BaseController {

    @ExceptionHandler(ValidateException.class)
    public ResultBean handerValidationExceptoin(ValidateException e) {
        return fail("验证失败", e.getList());
    }
}
