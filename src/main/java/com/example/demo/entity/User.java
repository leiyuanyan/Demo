package com.example.demo.entity;

import com.example.demo.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

/**
 * @author leiyuanyan
 * @Description
 * @since 2021/1/19 16:28
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自动递增策略
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String account;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String password;

    @Column
    private EntityStatus status = EntityStatus.ENABLE;
}
