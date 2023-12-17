package com.by.common.crud;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 封装了基本的增删改查
 */
public interface ICrud<T> {

    /**
     * 保存
     * @param entity 要保存对象
     * @return 保存结果  1代表成功 0代表失败
     */
    int save(T entity);

    /**
     * 修改
     * @param entity 要修改的对象
     * @return  修改结果  1修改成功 0修改失败
     */
    int update(T entity);

    /**
     * 删除
     * @param id 要删除数据的id
     * @return
     */
    int delete(Serializable id);

    /**
     * 查询
     * @param map  查询条件
     * @return 查询结果
     */
    List<T> query(Map map);

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    T getById(Serializable id);

}
