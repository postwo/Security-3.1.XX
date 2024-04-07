package com.example.testsecurity.dto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class User {
    private String username;
    private String password;

}
