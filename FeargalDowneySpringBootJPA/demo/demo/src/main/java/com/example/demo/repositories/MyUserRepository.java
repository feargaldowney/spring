package com.example.demo.repositories;

import com.example.demo.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, String> {
    Optional<MyUser> findByEmail(String email);

}
