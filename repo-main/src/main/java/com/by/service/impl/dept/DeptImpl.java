package com.by.service.impl.dept;

import com.by.bean.employee.Dept;
import com.by.common.crud.BaseServiceImpl;
import com.by.mapper.dept.DeptMapper;
import com.by.service.dept.DeptService;
import org.springframework.stereotype.Service;

@Service
public class DeptImpl extends BaseServiceImpl<Dept, DeptMapper> implements DeptService {
}
