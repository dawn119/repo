package com.by.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口返回统一结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class ResultBean {

    @ApiModelProperty("接口请求状态码200代表成功0代表失败")
    int code;//状态码 200成功 0失败

    @ApiModelProperty("消息描述")
    String msg;//消息描述

    @ApiModelProperty("接口返回的数据")
    Object data;//数据
}
