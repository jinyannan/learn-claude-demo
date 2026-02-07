package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysConfigMapper {
    SysConfig selectByPrimaryKey(@Param("customsCode") String customsCode, @Param("customsType") String customsType);

    List<SysConfig> selectAll();

    int insert(SysConfig config);

    int update(SysConfig config);

    int deleteByPrimaryKey(@Param("customsCode") String customsCode, @Param("customsType") String customsType);
}
