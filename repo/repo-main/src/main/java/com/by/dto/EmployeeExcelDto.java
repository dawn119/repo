package com.by.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
@ColumnWidth(20)
public class EmployeeExcelDto {

    @ExcelProperty("员工名称")
    private String empName;

    @ExcelProperty("性别")
    private String sexStr;
    @ExcelIgnore
    private int sex;
    @ExcelProperty("年龄")
    private int age;

    @ExcelProperty("部门名称")
    private String deptName;

    @ExcelProperty("学历")
    private String empDegreeName;
}
