package com.example.testsecurity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true) // username은 절대 중복이 되면안되니까 이렇게 선언한거다
    private String username;
    
    private String password;
    
    private String role; // 권한
    
}
