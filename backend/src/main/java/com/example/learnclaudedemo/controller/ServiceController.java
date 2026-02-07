package com.example.learnclaudedemo.controller;

import com.example.learnclaudedemo.dto.ServiceOrderRequest;
import com.example.learnclaudedemo.entity.ServiceOrder;
import com.example.learnclaudedemo.entity.ServicePublish;
import com.example.learnclaudedemo.service.ServiceOrderService;
import com.example.learnclaudedemo.service.ServicePublishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@Tag(name = "服务管理", description = "宠物服务相关接口")
public class ServiceController {

    private final ServicePublishService servicePublishService;
    private final ServiceOrderService serviceOrderService;

    @GetMapping
    @Operation(summary = "获取服务列表")
    public List<ServicePublish> list() {
        return servicePublishService.findByStatus(0);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取服务详情")
    public ServicePublish getById(@PathVariable Long id) {
        servicePublishService.incrementViewCount(id);
        return servicePublishService.findById(id);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户发布的服务")
    public List<ServicePublish> getByUserId(@PathVariable Long userId) {
        return servicePublishService.findByUserId(userId);
    }

    @GetMapping("/type/{typeId}")
    @Operation(summary = "按类型获取服务")
    public List<ServicePublish> getByType(@PathVariable Long typeId) {
        return servicePublishService.findByServiceTypeId(typeId);
    }

    @PostMapping
    @Operation(summary = "发布服务")
    public ServicePublish create(@RequestBody ServicePublish service) {
        return servicePublishService.create(service);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新服务")
    public ServicePublish update(@PathVariable Long id, @RequestBody ServicePublish service) {
        return servicePublishService.update(id, service);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "下架服务")
    public void delete(@PathVariable Long id) {
        servicePublishService.delete(id);
    }

    @PostMapping("/{id}/order")
    @Operation(summary = "创建服务订单")
    public ServiceOrder createOrder(@PathVariable Long id, @RequestBody ServiceOrderRequest request) {
        request.setServiceId(id);
        return serviceOrderService.createOrder(request);
    }
}

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "订单管理", description = "服务订单相关接口")
class OrderController {

    private final ServiceOrderService serviceOrderService;

    @GetMapping
    @Operation(summary = "获取订单列表")
    public List<ServiceOrder> list() {
        return serviceOrderService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取订单详情")
    public ServiceOrder getById(@PathVariable Long id) {
        return serviceOrderService.findById(id);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "获取客户的订单")
    public List<ServiceOrder> getByCustomerId(@PathVariable Long customerId) {
        return serviceOrderService.findByCustomerId(customerId);
    }

    @GetMapping("/provider/{providerId}")
    @Operation(summary = "获取服务提供者的订单")
    public List<ServiceOrder> getByProviderId(@PathVariable Long providerId) {
        return serviceOrderService.findByProviderId(providerId);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "按状态获取订单")
    public List<ServiceOrder> getByStatus(@PathVariable String status) {
        return serviceOrderService.findByStatus(status);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "更新订单状态")
    public ServiceOrder updateStatus(
            @PathVariable Long id,
            @Parameter(description = "状态") @RequestParam String status) {
        return serviceOrderService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除订单")
    public void delete(@PathVariable Long id) {
        serviceOrderService.deleteOrder(id);
    }
}
