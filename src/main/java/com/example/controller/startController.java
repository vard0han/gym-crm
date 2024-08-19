package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class startController {

    @GetMapping("/")
    public String startPage(){
        return "index";
    }
}
