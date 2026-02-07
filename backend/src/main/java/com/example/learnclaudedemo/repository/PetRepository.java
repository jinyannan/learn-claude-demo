package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByUserId(Long userId);

    List<Pet> findByPetType(String petType);

    List<Pet> findByUserIdAndStatus(Long userId, Integer status);

    List<Pet> findByStatus(Integer status);

}
