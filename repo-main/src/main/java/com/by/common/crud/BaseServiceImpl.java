package com.by.common.crud;

import com.by.common.bean.PageBean;
import com.by.common.bean.PageMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 查询默认实现
 *
 * @param <T> 保修或修改的类型
 * @param <M> 当前业务类依赖的Mapper
 */
public class BaseServiceImpl<T, M extends IMapper> implements IService<T> {

    @Autowired(required = false)
    protected M baseMapper;

    @Override
    public int save(T entity) {
        return baseMapper.save(entity);
    }

    @Override
    public int update(T entity) {
        return baseMapper.update(entity);
    }

    @Override
    public int delete(Serializable id) {
        return baseMapper.delete(id);
    }

    @Override
    public List<T> query(Map map) {
        return baseMapper.query(map);
    }

    @Override
    public T getById(Serializable id) {
        return (T)baseMapper.getById(id);
    }

    @Override
    public PageBean queryByPage(Map map) {
        PageMap pageMap=new PageMap(map);

        PageHelper.startPage(pageMap.getPage(), pageMap.getPagesize());
        List list = baseMapper.query(map);
        //分页信息
        PageInfo pageInfo = new PageInfo(list);
        return new PageBean(pageMap.getPage(), pageMap.getPagesize(), pageInfo.getTotal(), list);
    }
}
