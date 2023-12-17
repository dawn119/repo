package com.by.controller.sys;

import com.by.bean.sys.LoginBean;
import com.by.bean.sys.ModifyPwd;
import com.by.bean.sys.User;
import com.by.common.bean.PageBean;
import com.by.common.bean.ResultBean;
import com.by.common.controller.BaseController;
import com.by.common.group.Save;
import com.by.common.group.Update;
import com.by.service.sys.UserService;
import com.by.token.TokenFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/sys")
public class UserController extends BaseController {

    @Autowired
    UserService userService;
    @Autowired(required = false)
    TokenFactory tokenFactory;

    @ApiOperation("查询用户")
    @PostMapping("/user/query")
    public ResultBean query(@RequestBody Map map) {
        PageBean pageBean = userService.queryByPage(map);
        return success("用户列表", pageBean);
    }

    @ApiOperation("保存用户")
    @PostMapping("/user")
    public ResultBean save(@Validated(Save.class) @RequestBody User user, BindingResult result) {
        validate(result);
        int rs = userService.save(user);
        if(rs==-1)
        {
           return fail("账号已存在！");
        }
        return saveResult(rs);
    }


    @ApiOperation("更新用户")
    @PutMapping("/user")
    public ResultBean update(@Validated(Update.class) @RequestBody User user, BindingResult result) {
        validate(result);
        int rs = userService.update(user);
        return updateResult(rs);
    }


    @DeleteMapping("/user")
    @ApiOperation("删除用户")
    public ResultBean delete(@RequestParam(name = "id", defaultValue = "") String id) {
        int rs = userService.delete(id);
        return deleteResult(rs);
    }

    @ApiOperation("账号唯一检查")
    @GetMapping("/user/check")
    public ResultBean check(String account) {
        int rs = userService.checkUser(account);
        return success("账号唯一检查", rs);
    }
    @PostMapping("/user/login")
    public ResultBean login(@Validated @RequestBody LoginBean loginBean, BindingResult result) {
        validate(result);
        User user = userService.queryByAccount(loginBean.getUsername());
        if (null == user)
            return fail("账号或密码不存确！");

        //如果账号存在 判断密码是否正确
        //加密用户输入的密码
        String tmp = DigestUtils.md5DigestAsHex(loginBean.getPassword().getBytes());
        if (!tmp.equals(user.getPassword())) {
            return fail("账号或密码不存确！");
        }

        if (user.getIsLock() == 1) {
            return fail("账号已锁定，请联系管理员！");
        }

        //更新登录 时间
        userService.updateLoginTime(user.getId());

        //取消密码
        user.setPassword(null);

        //生成token
        Map map = new HashMap();
        map.put("id", user.getId());
        map.put("account", user.getAccount());
        map.put("eid", user.getEid());
        map.put("name", user.getName());
        map.put("roles", user.getRoles());
        String token = tokenFactory.generateToken(map);
        map.put("token", token);

        return success("登录成功", map);
    }

    @PutMapping("/user/pwd")
    public ResultBean modifypwd(@Validated @RequestBody ModifyPwd modifyPwd, BindingResult result) {
        validate(result);
        if (!modifyPwd.getNewpwd().equals(modifyPwd.getConfirmpwd()))
            return fail("两次密码输入不一致！");

        //查询旧密码
        User user = userService.getById(modifyPwd.getId());
        //加密旧密码
        String old = DigestUtils.md5DigestAsHex(modifyPwd.getOldpwd().getBytes());
        //判断密码是否正确
        if (!old.equals(user.getPassword()))
            return fail("原始密码不正确!");
        //修改密码
        int rs = userService.modifyPwd(modifyPwd);
        return rs == 1 ? success("修改成功") : fail("修改失败");
    }
    @PostMapping("/user/token")
    public ResultBean refreshToken(@RequestBody Map map) {
        String token = tokenFactory.generateToken(map);
        map.put("token", token);
        return success("换取token", map);
    }

}
