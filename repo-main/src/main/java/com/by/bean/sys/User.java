package com.by.bean.sys;

import com.by.common.group.Save;
import com.by.common.group.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("用户")
public class User {

    @ApiModelProperty("用户id")
    @NotBlank(message = "id不能为空", groups = {Update.class})
    String id;

    @ApiModelProperty("账号")
    @NotBlank(message = "账号不能为空！", groups = {Save.class})
    String account;

    String password;

    @ApiModelProperty("账号是否锁定")
    int isLock;

    @ApiModelProperty("生效日期")
    String effectiveDate;

    @ApiModelProperty("签名图片")
    String signUrl;

    @ApiModelProperty("说明 ")
    String note;

    @ApiModelProperty("员工id")
    String eid;

    @ApiModelProperty("员工名称")
    String name;

    @ApiModelProperty("角色")
    String roles;

    String roleName;
}
