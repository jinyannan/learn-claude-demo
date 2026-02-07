package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {

    Optional<ServiceOrder> findByOrderNo(String orderNo);

    List<ServiceOrder> findByProviderIdOrderByCreateTimeDesc(Long providerId);

    List<ServiceOrder> findByCustomerIdOrderByCreateTimeDesc(Long customerId);

    List<ServiceOrder> findByServiceId(Long serviceId);

    List<ServiceOrder> findByStatus(String status);

    List<ServiceOrder> findByProviderIdAndStatus(Long providerId, String status);

    List<ServiceOrder> findByCustomerIdAndStatus(Long customerId, String status);

}
