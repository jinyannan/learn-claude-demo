package com.example.learnclaudedemo.service;

import com.example.learnclaudedemo.dto.NearbySearchRequest;
import com.example.learnclaudedemo.entity.Pet;
import com.example.learnclaudedemo.entity.ServicePublish;
import com.example.learnclaudedemo.entity.User;

import java.util.List;

public interface LocationService {

    List<Pet> findNearbyPets(NearbySearchRequest request);

    List<User> findNearbyUsers(NearbySearchRequest request);

    List<ServicePublish> findNearbyServices(NearbySearchRequest request);

    double calculateDistance(double lat1, double lon1, double lat2, double lon2);
}
