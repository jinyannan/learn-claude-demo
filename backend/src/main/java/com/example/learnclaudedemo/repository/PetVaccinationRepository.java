package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.PetVaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetVaccinationRepository extends JpaRepository<PetVaccination, Long> {
    List<PetVaccination> findByPetId(Long petId);
}
