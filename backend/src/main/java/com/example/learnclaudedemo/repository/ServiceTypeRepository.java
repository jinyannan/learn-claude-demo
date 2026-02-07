package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    Optional<ServiceType> findByTypeCode(String typeCode);

    List<ServiceType> findByStatusOrderBySortOrderAsc(Integer status);

    List<ServiceType> findAllByOrderBySortOrderAsc();

}
