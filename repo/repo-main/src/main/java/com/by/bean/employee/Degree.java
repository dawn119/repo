package com.by.bean.employee;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("学历")
public class Degree {
int id;
String degreeName;
}
