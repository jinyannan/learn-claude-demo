package com.example.learnclaudedemo;

import com.example.learnclaudedemo.entity.Pet;
import com.example.learnclaudedemo.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DataViewer {

    @Autowired
    private PetRepository petRepository;

    @Test
    public void viewPets() {
        List<Pet> pets = petRepository.findAll();
        System.out.println("\n--- 数据库中的宠物数据 ---");
        System.out.printf("%-5s | %-10s | %-10s | %-15s | %-5s%n", "ID", "名称", "类型", "品种", "性别");
        System.out.println("------------------------------------------------------------");
        for (Pet pet : pets) {
            String genderStr = pet.getGender() == 1 ? "公" : (pet.getGender() == 2 ? "母" : "未知");
            System.out.printf("%-5d | %-10s | %-10s | %-15s | %-5s%n",
                    pet.getId(),
                    pet.getPetName(),
                    pet.getPetType(),
                    pet.getBreed(),
                    genderStr);
        }
        System.out.println("------------------------------------------------------------\n");
    }
}
