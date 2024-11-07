package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    // will be localhost:8080/ or localhost:8080 as below
    @GetMapping({"/", ""})
    public String index(){
        return "index"; // Looks for a file called index
    }
}
