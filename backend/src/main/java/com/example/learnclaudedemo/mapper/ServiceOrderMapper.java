package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.ServiceOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ServiceOrderMapper {
    ServiceOrder selectById(@Param("id") Long id);

    ServiceOrder selectByOrderNo(@Param("orderNo") String orderNo);

    List<ServiceOrder> selectByCustomerId(@Param("customerId") Long customerId);

    List<ServiceOrder> selectByProviderId(@Param("providerId") Long providerId);

    List<ServiceOrder> selectAll();

    int insert(ServiceOrder order);

    int update(ServiceOrder order);

    int deleteById(@Param("id") Long id);
}
