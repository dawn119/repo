package com.by.service.impl.employee;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.by.bean.employee.Employees;
import com.by.common.crud.BaseServiceImpl;
import com.by.dto.EmployeeExcelDto;
import com.by.mapper.employee.EmployeesMapper;
import com.by.service.employee.EmployeesService;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmployeesImpl extends BaseServiceImpl<Employees, EmployeesMapper> implements EmployeesService {


    @Override
    public Employees querypById(int Id) {
        return baseMapper.queryById(Id);
    }

    @Override
    public int exportEmployee(List<EmployeeExcelDto> employeeExcelDtos) {
        List<EmployeeExcelDto> dataList = new ArrayList<>();
        dataList = employeeExcelDtos;
        String currentDirectory = System.getProperty("user.dir");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd_HHmmss");
        String formatDate = simpleDateFormat.format(new Date());
        // 指定路径
        String directoryPath = currentDirectory + "/employeeExcel";
        Path path = Paths.get(directoryPath);
        // 判断路径是否存在
        if (!Files.exists(path)) {
            try {
                // 创建目录
                Files.createDirectories(path);
                System.out.println("目录已创建：" + directoryPath);
            } catch (Exception e) {
                // 处理创建目录失败的情况
                System.err.println("创建目录失败：" + e.getMessage());
            }
        } else {
            System.out.println("目录已存在：" + directoryPath);
        }
        String fileName = directoryPath + "/员工信息表_"+formatDate+".xlsx";
        ExcelWriterSheetBuilder excelWriterSheetBuilder = null;
        try {
            excelWriterSheetBuilder = EasyExcel.write(fileName, EmployeeExcelDto.class).sheet("Sheet1");
            // 执行写入
            excelWriterSheetBuilder.doWrite(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
