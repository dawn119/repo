package com.by.service.sys;

import com.by.bean.sys.ModifyPwd;
import com.by.bean.sys.User;
import com.by.common.crud.IService;

public interface UserService extends IService<User> {
    /**
     * 检查账号是否存在
     * @param account  账号名
     * @return  1 代表存在 0不存在
     */
    int checkUser(String account);
    /**
     * 根据账号查询用户信息
     * @param account
     * @return
     */
    User queryByAccount(String account);
    /**
     * 根据用户id更新登录时间
     * @param id
     * @return
     */
    int updateLoginTime(String id);
/*修改密码*/
    int modifyPwd(ModifyPwd modifyPwd);
}
