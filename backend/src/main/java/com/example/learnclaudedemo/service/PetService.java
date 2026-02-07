package com.example.learnclaudedemo.service;

import com.example.learnclaudedemo.entity.Pet;
import com.example.learnclaudedemo.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet findById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public List<Pet> findByUserId(Long userId) {
        return petRepository.findByUserId(userId);
    }

    public Pet create(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet update(Pet pet) {
        return petRepository.save(pet);
    }

    public void delete(Long id) {
        petRepository.deleteById(id);
    }
}
