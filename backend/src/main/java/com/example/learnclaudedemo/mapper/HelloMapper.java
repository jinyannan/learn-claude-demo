package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.Hello;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HelloMapper {
    Hello selectById(@Param("id") String id);

    int insert(Hello hello);

    int update(Hello hello);

    int deleteById(@Param("id") String id);
}
