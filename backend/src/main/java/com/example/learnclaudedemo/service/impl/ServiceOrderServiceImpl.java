package com.example.learnclaudedemo.service.impl;

import com.example.learnclaudedemo.dto.ServiceOrderRequest;
import com.example.learnclaudedemo.entity.ServiceOrder;
import com.example.learnclaudedemo.repository.ServiceOrderRepository;
import com.example.learnclaudedemo.service.ServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceOrderServiceImpl implements ServiceOrderService {

    private final ServiceOrderRepository serviceOrderRepository;

    @Override
    public List<ServiceOrder> findAll() {
        return serviceOrderRepository.findAll();
    }

    @Override
    public ServiceOrder findById(Long id) {
        return serviceOrderRepository.findById(id).orElse(null);
    }

    @Override
    public List<ServiceOrder> findByCustomerId(Long customerId) {
        return serviceOrderRepository.findAll().stream()
                .filter(o -> o.getCustomerId().equals(customerId))
                .toList();
    }

    @Override
    public List<ServiceOrder> findByProviderId(Long providerId) {
        return serviceOrderRepository.findAll().stream()
                .filter(o -> o.getProviderId().equals(providerId))
                .toList();
    }

    @Override
    public List<ServiceOrder> findByStatus(String status) {
        return serviceOrderRepository.findAll().stream()
                .filter(o -> status.equals(o.getStatus()))
                .toList();
    }

    @Override
    @Transactional
    public ServiceOrder createOrder(ServiceOrderRequest request) {
        ServiceOrder order = new ServiceOrder();
        order.setOrderNo(generateOrderNo());
        order.setServiceId(request.getServiceId());
        order.setProviderId(request.getProviderId());
        order.setCustomerId(request.getCustomerId());
        order.setPetId(request.getPetId());
        order.setServiceTime(request.getServiceTime());
        order.setServiceAddress(request.getServiceAddress());
        order.setPrice(request.getPrice());
        order.setStatus("pending");
        order.setRemark(request.getRemark());
        return serviceOrderRepository.save(order);
    }

    @Override
    @Transactional
    public ServiceOrder updateStatus(Long id, String status) {
        ServiceOrder order = serviceOrderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(status);
            return serviceOrderRepository.save(order);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        serviceOrderRepository.deleteById(id);
    }

    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
