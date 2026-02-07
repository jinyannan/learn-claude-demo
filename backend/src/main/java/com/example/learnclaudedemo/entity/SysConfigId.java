package com.example.learnclaudedemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysConfigId implements Serializable {
    private String customsCode;
    private String customsType;
}
