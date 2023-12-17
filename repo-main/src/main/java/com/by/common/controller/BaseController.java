package com.by.common.controller;

import com.by.common.bean.ResultBean;
import com.by.common.exception.ValidateException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {

    private final int SUCCESS = 200;
    private final int FAIL = 0;


    /**
     * 成功
     *
     * @param msg
     * @return
     */
    protected ResultBean success(String msg) {
        return handler(SUCCESS, msg, null);
    }

    /**
     * 处理用户保存的结果
     *
     * @param rs 1代表成功 0代表失败
     * @return
     */
    protected ResultBean saveResult(int rs) {
        return rs == 1 ? success("保存成功") : fail("保存失败");
    }

    protected ResultBean updateResult(int rs) {
        return rs == 1 ? success("修改成功") : fail("修改失败");
    }

    protected ResultBean deleteResult(int rs) {
        return rs == 0 ? fail("删除失败") :success("删除成功") ;
    }

    protected ResultBean excelResult(int rs) {
        return rs == 0 ? fail("导出失败") :success("导出成功") ;
    }

    /**
     * 成功
     *
     * @param msg
     * @param data
     * @return
     */
    protected ResultBean success(String msg, Object data) {
        return handler(SUCCESS, msg, data);
    }

    /**
     * 失败
     *
     * @param msg
     * @return
     */
    protected ResultBean fail(String msg) {
        return handler(FAIL, msg, null);
    }

    /**
     * 失败
     *
     * @param msg
     * @param data
     * @return
     */
    protected ResultBean fail(String msg, Object data) {
        return handler(FAIL, msg, data);
    }

    /**
     * 创建REsultBean
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    ResultBean handler(int code, String msg, Object data) {
        return new ResultBean(code, msg, data);
    }


    protected void validate(BindingResult result) {
        List<Map> error = new ArrayList<>();
        List<FieldError> list = result.getFieldErrors();
        if (list.size() > 0) {
            list.forEach(e -> {
                Map map = new HashMap();
                map.put(e.getField(), e.getDefaultMessage());
                error.add(map);
            });
        }
        if (error.size() > 0) {
            try {
                throw new ValidateException(error);
            } catch (ValidateException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
