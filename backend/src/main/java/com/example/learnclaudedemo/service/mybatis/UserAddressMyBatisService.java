package com.example.learnclaudedemo.service.mybatis;

import com.example.learnclaudedemo.entity.UserAddress;
import com.example.learnclaudedemo.mapper.UserAddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAddressMyBatisService {

    private final UserAddressMapper userAddressMapper;

    public UserAddress getById(Long id) {
        return userAddressMapper.selectById(id);
    }

    public List<UserAddress> getByUserId(Long userId) {
        return userAddressMapper.selectByUserId(userId);
    }

    @Transactional
    public UserAddress saveAddress(UserAddress address) {
        if (address.getIsDefault() != null && address.getIsDefault() == 1) {
            userAddressMapper.resetDefaultByUserId(address.getUserId());
        }

        if (address.getId() == null) {
            userAddressMapper.insert(address);
        } else {
            userAddressMapper.update(address);
        }
        return address;
    }

    public void deleteAddress(Long id) {
        userAddressMapper.deleteById(id);
    }

    @Transactional
    public void setDefault(Long userId, Long addressId) {
        userAddressMapper.resetDefaultByUserId(userId);
        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setIsDefault(1);
        userAddressMapper.update(address);
    }
}
