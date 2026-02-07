package com.example.learnclaudedemo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sys_config")
@IdClass(SysConfigId.class)
public class SysConfig {

    @Id
    @Column(name = "customs_code", nullable = false, length = 50)
    private String customsCode;

    @Id
    @Column(name = "customs_type", nullable = false, length = 50)
    private String customsType;

    @Column(length = 128)
    private String host;

    @Column
    private Integer port;

    @Column(length = 128)
    private String username;

    @Column(length = 128)
    private String password;

    @Column(name = "virtual_host", length = 128)
    private String virtualHost;

    @Column(name = "queue_name", length = 128)
    private String queueName;

}
