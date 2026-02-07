package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper {
    SysUser selectById(@Param("id") Long id);

    SysUser selectByUsername(@Param("username") String username);

    int insert(SysUser user);

    int update(SysUser user);

    int deleteById(@Param("id") Long id);
}
