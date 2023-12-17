package com.by.controller.dept;

import com.by.bean.employee.Dept;
import com.by.common.bean.PageBean;
import com.by.common.bean.ResultBean;
import com.by.common.controller.BaseController;
import com.by.common.group.Save;
import com.by.common.group.Update;
import com.by.service.dept.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "部门管理")
@RestController
@RequestMapping("/dept")
public class DeptController  extends BaseController {
@Autowired
    DeptService deptService;
    @ApiOperation("保存部门")
    @PostMapping("/save")
    public ResultBean save(@Validated(Save.class) @RequestBody Dept dept, BindingResult result) {
        validate(result);
        int rs = deptService.save(dept);
        return saveResult(rs);
    }

    @ApiOperation("查询部门")
    @PostMapping("/query")
    public ResultBean query(@RequestBody Map map) {
        PageBean pageBean = deptService.queryByPage(map);
        return success("部门列表", pageBean);
    }

    @ApiOperation("更新部门")
    @PutMapping("/update")
    public ResultBean update(@Validated(Update.class) @RequestBody Dept dept, BindingResult result) {
        validate(result);
        int rs = deptService.update(dept);
        return updateResult(rs);
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/delete")
    public ResultBean delete(@RequestParam(name = "id", defaultValue = "") String id) {
        int rs = deptService.delete(id);
        return deleteResult(rs);
    }
}
