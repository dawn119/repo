package com.by.bean.employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("员工")
public class Employees {
    @ApiModelProperty("员工id")
    int id;
    @ApiModelProperty("员工姓名")
    String empName;
    @ApiModelProperty("员工性别")
    int sex;
    @ApiModelProperty("员工年龄")
    int age;
    @ApiModelProperty("员工部门")
    int deptName;
    @ApiModelProperty("员工学历")
    int empDegreeName;

}
