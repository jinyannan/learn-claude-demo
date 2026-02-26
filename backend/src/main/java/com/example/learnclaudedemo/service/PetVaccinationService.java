package com.example.learnclaudedemo.service;

import com.example.learnclaudedemo.entity.PetVaccination;
import java.util.List;

public interface PetVaccinationService {
    List<PetVaccination> getVaccinationsByPetId(Long petId);

    PetVaccination addVaccination(PetVaccination vaccination);

    PetVaccination updateVaccination(Long id, PetVaccination vaccination);

    void deleteVaccination(Long id);

    PetVaccination getVaccinationById(Long id);
}
