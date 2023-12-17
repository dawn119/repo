package com.by.common.crud;

import com.by.common.bean.PageBean;

import java.util.Map;

public interface IService<T> extends ICrud<T> {
    /**
     * 分页查询
     *
     * @param map
     * @return
     */
    PageBean queryByPage(Map map);
}
