package com.by.controller.employee;

import com.by.bean.employee.Employees;
import com.by.bean.sys.User;
import com.by.common.bean.PageBean;
import com.by.common.bean.ResultBean;
import com.by.common.controller.BaseController;
import com.by.common.group.Save;
import com.by.common.group.Update;
import com.by.dto.EmployeeExcelDto;
import com.by.service.employee.EmployeesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "员工管理")
@RestController
@RequestMapping("/emp")
public class EmployeesController extends BaseController {
     @Autowired
    EmployeesService employeesService;

    @ApiOperation("保存员工")
    @PostMapping("/employee")
    public ResultBean save(@Validated(Save.class) @RequestBody Employees employees, BindingResult result) {
        validate(result);
        int rs = employeesService.save(employees);
        return saveResult(rs);
    }

    @ApiOperation("查询员工")
    @PostMapping("/employee/query")
    public ResultBean query(@RequestBody Map map) {
        PageBean pageBean = employeesService.queryByPage(map);
        return success("员工列表", pageBean);
    }

    @ApiOperation("更新员工")
    @PutMapping("/employee")
    public ResultBean update(@Validated(Update.class) @RequestBody Employees employees, BindingResult result) {
        validate(result);
        int rs = employeesService.update(employees);
        return updateResult(rs);
    }


    @DeleteMapping("/employee")
    @ApiOperation("删除员工")
    public ResultBean delete(@RequestParam(name = "id", defaultValue = "") String id) {
        int rs = employeesService.delete(id);
        return deleteResult(rs);
    }

    @PostMapping("/excel")
    @ApiOperation("导出Excel")
    public ResultBean exportExcel(@RequestBody List<EmployeeExcelDto> employeeExcelDtos) {
        int rs = employeesService.exportEmployee(employeeExcelDtos);
        return excelResult(rs);
    }
}
