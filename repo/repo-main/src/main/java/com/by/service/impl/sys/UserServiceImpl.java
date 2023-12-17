package com.by.service.impl.sys;

import com.by.bean.sys.ModifyPwd;
import com.by.bean.sys.User;
import com.by.common.crud.BaseServiceImpl;
import com.by.mapper.sys.UserMapper;
import com.by.service.sys.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserMapper> implements UserService {
    @Override
    public int save(User entity) {
        //保存之前检查唯一性
        int rs=baseMapper.queryByAccount(entity.getAccount());
                if(rs==1)
                {
                    return -1;
                }
        return super.save(entity);
    }

    @Override
    public int checkUser(String account) {
        return baseMapper.queryByAccount(account);
    }

    @Override
    public int update(User entity) {
        return super.update(entity);
    }

    @Override
    public User queryByAccount(String account) {
        return baseMapper.queryUserByAccount(account);
    }

    @Override
    public int updateLoginTime(String id) {
        return baseMapper.updateLoginTime(id);
    }

    @Override
    public int modifyPwd(ModifyPwd modifyPwd) {
        return baseMapper.modifyPwd(modifyPwd);
    }
}
