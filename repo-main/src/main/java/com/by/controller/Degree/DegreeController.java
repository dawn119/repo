package com.by.controller.Degree;

import com.by.bean.employee.Degree;
import com.by.common.bean.PageBean;
import com.by.common.bean.ResultBean;
import com.by.common.controller.BaseController;
import com.by.common.group.Save;
import com.by.common.group.Update;
import com.by.service.degree.DegreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "学历管理")
@RestController
@RequestMapping("/degree")
public class DegreeController extends BaseController {
    @Autowired
    DegreeService degreeService;

    @ApiOperation("保存学历")
    @PostMapping("/save")
    public ResultBean save(@Validated(Save.class) @RequestBody Degree degree, BindingResult result) {
        validate(result);
        int rs = degreeService.save(degree);
        return saveResult(rs);
    }

    @ApiOperation("查询学历")
    @PostMapping("/query")
    public ResultBean query(@RequestBody Map map) {
        PageBean pageBean = degreeService.queryByPage(map);
        return success("学历列表", pageBean);
    }

    @ApiOperation("更新学历")
    @PutMapping("/update")
    public ResultBean update(@Validated(Update.class) @RequestBody Degree degree, BindingResult result) {
        validate(result);
        int rs = degreeService.update(degree);
        return updateResult(rs);
    }

    @ApiOperation("删除学历")
    @DeleteMapping("/delete")
    public ResultBean delete(@RequestParam(name = "id", defaultValue = "") String id) {
        int rs = degreeService.delete(id);
        return deleteResult(rs);
    }
}
