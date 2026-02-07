package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.ServiceType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ServiceTypeMapper {
    ServiceType selectById(@Param("id") Long id);

    ServiceType selectByTypeCode(@Param("typeCode") String typeCode);

    List<ServiceType> selectAllEnabled();

    int insert(ServiceType type);

    int update(ServiceType type);

    int deleteById(@Param("id") Long id);
}
