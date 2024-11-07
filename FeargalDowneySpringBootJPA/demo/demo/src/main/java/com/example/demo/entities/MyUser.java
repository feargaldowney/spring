package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class MyUser {

    @Id
    @Column(nullable = false, unique = true)
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String role;
    @JsonIgnore
    private boolean locked;
    @Column(nullable = false, unique = true)
    private int phone;
    @Column(nullable = false, unique = true)
    private String PPSN;
    // Phone number and PPSN should probably be unique so I added that here.

    public MyUser(String email, String password, String role, boolean locked, int phone, String PPSN) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.locked = locked;
        this.phone = phone;
        this.PPSN = PPSN;
    }
}
