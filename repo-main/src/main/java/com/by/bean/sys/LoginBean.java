package com.by.bean.sys;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginBean {
    @NotBlank(message = "用户名不能为空！")
    String username;

    @NotBlank(message = "密码不能为空！")
    String password;
}
