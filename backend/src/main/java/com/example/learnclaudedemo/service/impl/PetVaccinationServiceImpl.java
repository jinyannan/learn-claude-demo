package com.example.learnclaudedemo.service.impl;

import com.example.learnclaudedemo.entity.PetVaccination;
import com.example.learnclaudedemo.repository.PetVaccinationRepository;
import com.example.learnclaudedemo.service.PetVaccinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetVaccinationServiceImpl implements PetVaccinationService {

    private final PetVaccinationRepository vaccinationRepository;

    @Override
    public List<PetVaccination> getVaccinationsByPetId(Long petId) {
        return vaccinationRepository.findByPetId(petId);
    }

    @Override
    @Transactional
    public PetVaccination addVaccination(PetVaccination vaccination) {
        return vaccinationRepository.save(vaccination);
    }

    @Override
    @Transactional
    public PetVaccination updateVaccination(Long id, PetVaccination vaccination) {
        PetVaccination existing = vaccinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccination record not found"));

        existing.setVaccineName(vaccination.getVaccineName());
        existing.setVaccinationDate(vaccination.getVaccinationDate());
        existing.setNextDoseDate(vaccination.getNextDoseDate());
        existing.setProvider(vaccination.getProvider());
        existing.setRemarks(vaccination.getRemarks());

        return vaccinationRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteVaccination(Long id) {
        vaccinationRepository.deleteById(id);
    }

    @Override
    public PetVaccination getVaccinationById(Long id) {
        return vaccinationRepository.findById(id).orElse(null);
    }
}
