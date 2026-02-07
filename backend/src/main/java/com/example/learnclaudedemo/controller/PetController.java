package com.example.learnclaudedemo.controller;

import com.example.learnclaudedemo.entity.Pet;
import com.example.learnclaudedemo.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
@Tag(name = "宠物管理", description = "提供宠物的增删改查相关接口")
public class PetController {

    private final PetService petService;

    @GetMapping
    @Operation(summary = "获取所有宠物列表", description = "返回系统中注册的所有宠物信息")
    public List<Pet> list() {
        return petService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取宠物详情")
    public Pet getById(@PathVariable Long id) {
        return petService.findById(id);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取特定用户的宠物列表")
    public List<Pet> getByUserId(@PathVariable Long userId) {
        return petService.findByUserId(userId);
    }

    @PostMapping
    @Operation(summary = "创建新宠物")
    public Pet create(@RequestBody Pet pet) {
        return petService.create(pet);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新宠物信息")
    public Pet update(@PathVariable Long id, @RequestBody Pet pet) {
        pet.setId(id);
        return petService.update(pet);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除宠物")
    public void delete(@PathVariable Long id) {
        petService.delete(id);
    }
}
