package com.example.learnclaudedemo.service.impl;

import com.example.learnclaudedemo.dto.NearbySearchRequest;
import com.example.learnclaudedemo.entity.Pet;
import com.example.learnclaudedemo.entity.ServicePublish;
import com.example.learnclaudedemo.entity.User;
import com.example.learnclaudedemo.repository.PetRepository;
import com.example.learnclaudedemo.repository.ServicePublishRepository;
import com.example.learnclaudedemo.repository.UserAddressRepository;
import com.example.learnclaudedemo.repository.UserRepository;
import com.example.learnclaudedemo.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final ServicePublishRepository servicePublishRepository;
    private final UserAddressRepository addressRepository;

    @Override
    public List<Pet> findNearbyPets(NearbySearchRequest request) {
        List<Pet> allPets = petRepository.findAll();

        return allPets.stream()
                .filter(pet -> {
                    if (request.getUserId() != null && pet.getUserId().equals(request.getUserId())) {
                        return false;
                    }
                    if (request.getPetType() != null && !request.getPetType().isEmpty()) {
                        return request.getPetType().equals(pet.getPetType());
                    }
                    return true;
                })
                .limit(20)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findNearbyUsers(NearbySearchRequest request) {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .filter(user -> {
                    if (request.getUserId() != null && user.getId().equals(request.getUserId())) {
                        return false;
                    }
                    return true;
                })
                .limit(20)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServicePublish> findNearbyServices(NearbySearchRequest request) {
        List<ServicePublish> allServices = servicePublishRepository.findAll();

        return allServices.stream()
                .filter(service -> {
                    if (service.getStatus() == null || service.getStatus() != 0) {
                        return false;
                    }
                    if (request.getServiceTypeId() != null &&
                            !request.getServiceTypeId().equals(service.getServiceTypeId())) {
                        return false;
                    }
                    return true;
                })
                .limit(20)
                .collect(Collectors.toList());
    }

    @Override
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
