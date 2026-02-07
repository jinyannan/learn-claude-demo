package com.example.learnclaudedemo.service;

import com.example.learnclaudedemo.dto.ServiceOrderRequest;
import com.example.learnclaudedemo.entity.ServiceOrder;

import java.util.List;

public interface ServiceOrderService {

    List<ServiceOrder> findAll();

    ServiceOrder findById(Long id);

    List<ServiceOrder> findByCustomerId(Long customerId);

    List<ServiceOrder> findByProviderId(Long providerId);

    List<ServiceOrder> findByStatus(String status);

    ServiceOrder createOrder(ServiceOrderRequest request);

    ServiceOrder updateStatus(Long id, String status);

    void deleteOrder(Long id);
}
