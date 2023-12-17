package com.by.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {
    int page;//当前页
    int pageSize;//每页记录数
    long total;//总记录数
    Object data;//查询分页结果
}
