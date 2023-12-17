package com.by.service.employee;

import com.by.bean.employee.Employees;
import com.by.common.crud.IService;
import com.by.dto.EmployeeExcelDto;

import java.util.List;


public interface  EmployeesService extends IService<Employees> {

    //根据员工名查询员工
    Employees querypById(int Id);

    int exportEmployee(List<EmployeeExcelDto> employeeExcelDto);
}
