package com.example.learnclaudedemo.repository;

import com.example.learnclaudedemo.entity.Hello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelloRepository extends JpaRepository<Hello, String> {

    List<Hello> findByCode(String code);

    List<Hello> findByNameContaining(String name);

}
