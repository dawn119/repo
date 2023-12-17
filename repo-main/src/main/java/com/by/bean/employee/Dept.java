package com.by.bean.employee;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("部门")
public class Dept {
int id;
String deptName;
}
