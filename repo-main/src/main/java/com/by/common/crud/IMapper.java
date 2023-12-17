package com.by.common.crud;

import org.apache.ibatis.annotations.Mapper;

/**
 * mapper接口
 * @param <T>
 */
@Mapper
public interface IMapper<T> extends ICrud<T>{
}
