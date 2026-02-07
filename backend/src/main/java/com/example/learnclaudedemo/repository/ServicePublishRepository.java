package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.ServicePublish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicePublishRepository extends JpaRepository<ServicePublish, Long> {

    List<ServicePublish> findByUserIdOrderByCreateTimeDesc(Long userId);

    List<ServicePublish> findByServiceTypeId(Long serviceTypeId);

    List<ServicePublish> findByAddressId(Long addressId);

    List<ServicePublish> findByStatus(Integer status);

    List<ServicePublish> findByUserIdAndStatus(Long userId, Integer status);

}
