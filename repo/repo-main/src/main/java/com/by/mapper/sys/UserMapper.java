package com.by.mapper.sys;

import com.by.bean.sys.ModifyPwd;
import com.by.bean.sys.User;
import com.by.common.crud.IMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends IMapper<User> {
    /**
     * 根据账号名查询用户
     * @param account
     * @return
     */
    int queryByAccount(String account);
    /**
     * 根据账号查询全部用户
     * @param account
     * @return
     */
    User queryUserByAccount(String account);
    /*更新时间*/
    int updateLoginTime(String id);
    /*修改密码*/
    int modifyPwd(ModifyPwd modifyPwd);
}
