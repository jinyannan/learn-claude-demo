package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAddressMapper {

    /**
     * 根据ID查询地址
     */
    UserAddress selectById(@Param("id") Long id);

    /**
     * 根据用户ID查询所有地址
     */
    List<UserAddress> selectByUserId(@Param("userId") Long userId);

    /**
     * 插入新地址
     */
    int insert(UserAddress userAddress);

    /**
     * 更新地址信息
     */
    int update(UserAddress userAddress);

    /**
     * 删除地址
     */
    int deleteById(@Param("id") Long id);

    /**
     * 设置某个地址为默认地址（取消该用户其他默认地址）
     */
    int resetDefaultByUserId(@Param("userId") Long userId);
}
