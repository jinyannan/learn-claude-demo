package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    List<UserAddress> findByUserIdOrderByCreateTimeDesc(Long userId);

    List<UserAddress> findByUserIdAndIsDefault(Long userId, Integer isDefault);

    UserAddress findFirstByUserIdOrderByCreateTimeDesc(Long userId);

}
