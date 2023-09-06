package com.dev.community.springbatchdemo.dal.jpa.entities;


import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;
}