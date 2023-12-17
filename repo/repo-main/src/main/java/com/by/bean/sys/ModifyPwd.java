package com.by.bean.sys;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("修改密码")
public class ModifyPwd {
    @NotBlank(message = "用户id不能为空！")
    String id;

    @NotBlank(message = "旧密码不能为空！")
    String oldpwd;

    @NotBlank(message = "新密码不能为空！")
    String newpwd;

    @NotBlank(message = "确认密码不能为空！")
    String confirmpwd;
}
