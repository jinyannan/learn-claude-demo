package com.example.learnclaudedemo.service;

import com.example.learnclaudedemo.entity.ServicePublish;

import java.util.List;

public interface ServicePublishService {

    List<ServicePublish> findAll();

    ServicePublish findById(Long id);

    List<ServicePublish> findByUserId(Long userId);

    List<ServicePublish> findByServiceTypeId(Long serviceTypeId);

    List<ServicePublish> findByStatus(Integer status);

    ServicePublish create(ServicePublish service);

    ServicePublish update(Long id, ServicePublish service);

    void delete(Long id);

    void incrementViewCount(Long id);
}
