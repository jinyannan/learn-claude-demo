package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.ServicePublish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ServicePublishMapper {
    ServicePublish selectById(@Param("id") Long id);

    List<ServicePublish> selectByUserId(@Param("userId") Long userId);

    List<ServicePublish> selectByServiceType(@Param("typeId") Long typeId);

    List<ServicePublish> selectAll();

    int insert(ServicePublish service);

    int update(ServicePublish service);

    int deleteById(@Param("id") Long id);
}
