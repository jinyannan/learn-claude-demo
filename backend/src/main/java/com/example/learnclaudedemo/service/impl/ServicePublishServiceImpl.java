package com.example.learnclaudedemo.service.impl;

import com.example.learnclaudedemo.entity.ServicePublish;
import com.example.learnclaudedemo.repository.ServicePublishRepository;
import com.example.learnclaudedemo.service.ServicePublishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicePublishServiceImpl implements ServicePublishService {

    private final ServicePublishRepository servicePublishRepository;

    @Override
    public List<ServicePublish> findAll() {
        return servicePublishRepository.findAll();
    }

    @Override
    public ServicePublish findById(Long id) {
        return servicePublishRepository.findById(id).orElse(null);
    }

    @Override
    public List<ServicePublish> findByUserId(Long userId) {
        return servicePublishRepository.findAll().stream()
                .filter(s -> s.getUserId().equals(userId))
                .toList();
    }

    @Override
    public List<ServicePublish> findByServiceTypeId(Long serviceTypeId) {
        return servicePublishRepository.findAll().stream()
                .filter(s -> s.getServiceTypeId().equals(serviceTypeId))
                .toList();
    }

    @Override
    public List<ServicePublish> findByStatus(Integer status) {
        return servicePublishRepository.findAll().stream()
                .filter(s -> s.getStatus() != null && s.getStatus().equals(status))
                .toList();
    }

    @Override
    @Transactional
    public ServicePublish create(ServicePublish service) {
        service.setViewCount(0);
        service.setStatus(0);
        return servicePublishRepository.save(service);
    }

    @Override
    @Transactional
    public ServicePublish update(Long id, ServicePublish service) {
        ServicePublish existing = servicePublishRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        if (service.getTitle() != null) {
            existing.setTitle(service.getTitle());
        }
        if (service.getDescription() != null) {
            existing.setDescription(service.getDescription());
        }
        if (service.getPrice() != null) {
            existing.setPrice(service.getPrice());
        }
        if (service.getAvailableTime() != null) {
            existing.setAvailableTime(service.getAvailableTime());
        }
        if (service.getPetTypes() != null) {
            existing.setPetTypes(service.getPetTypes());
        }
        return servicePublishRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        servicePublishRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        ServicePublish service = servicePublishRepository.findById(id).orElse(null);
        if (service != null) {
            Integer currentCount = service.getViewCount();
            service.setViewCount(currentCount == null ? 1 : currentCount + 1);
            servicePublishRepository.save(service);
        }
    }
}
