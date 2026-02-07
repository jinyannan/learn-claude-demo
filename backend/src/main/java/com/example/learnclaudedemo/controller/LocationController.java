package com.example.learnclaudedemo.controller;

import com.example.learnclaudedemo.dto.NearbySearchRequest;
import com.example.learnclaudedemo.entity.Pet;
import com.example.learnclaudedemo.entity.ServicePublish;
import com.example.learnclaudedemo.entity.User;
import com.example.learnclaudedemo.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nearby")
@RequiredArgsConstructor
@Tag(name = "附近功能", description = "基于位置的附近搜索相关接口")
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/pets")
    @Operation(summary = "查找附近的宠物")
    public List<Pet> findNearbyPets(@RequestBody NearbySearchRequest request) {
        return locationService.findNearbyPets(request);
    }

    @PostMapping("/users")
    @Operation(summary = "查找附近的用户")
    public List<User> findNearbyUsers(@RequestBody NearbySearchRequest request) {
        return locationService.findNearbyUsers(request);
    }

    @PostMapping("/services")
    @Operation(summary = "查找附近的服务")
    public List<ServicePublish> findNearbyServices(@RequestBody NearbySearchRequest request) {
        return locationService.findNearbyServices(request);
    }

    @PostMapping("/distance")
    @Operation(summary = "计算两点间距离")
    public double calculateDistance(
            @RequestParam Double lat1,
            @RequestParam Double lon1,
            @RequestParam Double lat2,
            @RequestParam Double lon2) {
        return locationService.calculateDistance(lat1, lon1, lat2, lon2);
    }
}
