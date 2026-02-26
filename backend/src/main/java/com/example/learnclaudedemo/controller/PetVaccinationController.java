package com.example.learnclaudedemo.controller;

import com.example.learnclaudedemo.config.Result;
import com.example.learnclaudedemo.entity.PetVaccination;
import com.example.learnclaudedemo.service.PetVaccinationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets/vaccinations")
@RequiredArgsConstructor
@Tag(name = "宠物疫苗接口", description = "提供宠物疫苗接种记录的增删改查功能")
public class PetVaccinationController {

    private final PetVaccinationService vaccinationService;

    @GetMapping("/pet/{petId}")
    @Operation(summary = "获取宠物的疫苗记录", description = "根据宠物ID查询其所有的疫苗接种历史")
    public Result<List<PetVaccination>> getByPetId(@PathVariable Long petId) {
        return Result.success(vaccinationService.getVaccinationsByPetId(petId));
    }

    @PostMapping
    @Operation(summary = "新增疫苗记录", description = "为特定宠物新增一条疫苗接种记录")
    public Result<PetVaccination> create(@RequestBody PetVaccination vaccination) {
        return Result.success(vaccinationService.addVaccination(vaccination));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新疫苗记录", description = "修改已有的疫苗接种记录信息")
    public Result<PetVaccination> update(@PathVariable Long id, @RequestBody PetVaccination vaccination) {
        return Result.success(vaccinationService.updateVaccination(id, vaccination));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除疫苗记录", description = "根据记录ID删除疫苗接种记录")
    public Result<Void> delete(@PathVariable Long id) {
        vaccinationService.deleteVaccination(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取单条记录详情", description = "根据记录ID获取详细的疫苗接种信息")
    public Result<PetVaccination> getById(@PathVariable Long id) {
        return Result.success(vaccinationService.getVaccinationById(id));
    }
}
