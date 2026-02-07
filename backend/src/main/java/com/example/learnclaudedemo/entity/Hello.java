package com.example.learnclaudedemo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "hello")
public class Hello {

    @Id
    @Column(name = "ID", nullable = false, length = 64)
    private String id;

    @Column(name = "CODE", length = 45)
    private String code;

    @Column(name = "NAME", length = 45)
    private String name;

}
