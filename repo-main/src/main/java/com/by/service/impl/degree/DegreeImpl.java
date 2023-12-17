package com.by.service.impl.degree;

import com.by.bean.employee.Degree;
import com.by.common.crud.BaseServiceImpl;
import com.by.mapper.degree.DegreeMapper;
import com.by.service.degree.DegreeService;
import org.springframework.stereotype.Service;

@Service
public class DegreeImpl extends BaseServiceImpl<Degree, DegreeMapper> implements DegreeService {

}
