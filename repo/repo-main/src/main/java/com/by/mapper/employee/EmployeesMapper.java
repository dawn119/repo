package com.by.mapper.employee;

import com.by.bean.employee.Employees;
import com.by.bean.sys.User;
import com.by.common.crud.IMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface  EmployeesMapper extends IMapper<Employees> {

   //根据员工名查询员工
    Employees queryById(int Id);
}
