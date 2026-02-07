package com.example.learnclaudedemo.mapper;

import com.example.learnclaudedemo.entity.Pet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PetMapper {
    Pet selectById(@Param("id") Long id);

    List<Pet> selectByUserId(@Param("userId") Long userId);

    List<Pet> selectAll();

    int insert(Pet pet);

    int update(Pet pet);

    int deleteById(@Param("id") Long id);
}
