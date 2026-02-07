package com.example.learnclaudedemo.controller;

import com.example.learnclaudedemo.entity.Pet;
import com.example.learnclaudedemo.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pet samplePet;

    @BeforeEach
    void setUp() {
        samplePet = new Pet();
        samplePet.setId(1L);
        samplePet.setUserId(1001L);
        samplePet.setPetName("小白");
        samplePet.setPetType("猫");
    }

    @Test
    void testList() throws Exception {
        List<Pet> pets = Arrays.asList(samplePet);
        when(petService.findAll()).thenReturn(pets);

        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].petName").value("小白"));

        verify(petService, times(1)).findAll();
    }

    @Test
    void testGetById() throws Exception {
        when(petService.findById(1L)).thenReturn(samplePet);

        mockMvc.perform(get("/api/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petName").value("小白"));

        verify(petService, times(1)).findById(1L);
    }

    @Test
    void testGetByUserId() throws Exception {
        when(petService.findByUserId(1001L)).thenReturn(Arrays.asList(samplePet));

        mockMvc.perform(get("/api/pets/user/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1001));

        verify(petService, times(1)).findByUserId(1001L);
    }

    @Test
    void testCreate() throws Exception {
        when(petService.create(any(Pet.class))).thenReturn(samplePet);

        mockMvc.perform(post("/api/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(samplePet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petName").value("小白"));

        verify(petService, times(1)).create(any(Pet.class));
    }

    @Test
    void testUpdate() throws Exception {
        when(petService.update(any(Pet.class))).thenReturn(samplePet);

        mockMvc.perform(put("/api/pets/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(samplePet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(petService, times(1)).update(any(Pet.class));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(petService).delete(1L);

        mockMvc.perform(delete("/api/pets/1"))
                .andExpect(status().isOk());

        verify(petService, times(1)).delete(1L);
    }
}
