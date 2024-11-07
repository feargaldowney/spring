package com.example.demo.controllers;

import com.example.demo.controllers.DTOS.NewUserDTO;
import com.example.demo.entities.MyUser;
import com.example.demo.repositories.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MyUserRepository myUserRepository;

    @PostMapping
    public MyUser addUser(@RequestBody NewUserDTO newUserDTO) {
        if (myUserRepository.findById(newUserDTO.email()).isPresent()) {
            throw new IllegalStateException("Email already in use");
        }

        MyUser newUser = new MyUser(
                newUserDTO.email(),
                newUserDTO.password(),
                newUserDTO.role(),
                newUserDTO.locked(),
                newUserDTO.phone(),
                newUserDTO.PPSN()
        );

        return myUserRepository.save(newUser);
    }
}
