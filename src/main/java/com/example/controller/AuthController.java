package com.example.controller;

import com.example.service.TraineeService;
import com.example.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AuthController {
    @Autowired
    TraineeService traineeService;

    @Autowired
    TrainerService trainerService;

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        boolean validLogin = traineeService.validateLogin(username, password) || trainerService.validateLogin(username, password);
        return validLogin ? ResponseEntity.ok("200 OK") : ResponseEntity.status(401).body("Invalid credentials");
    }
}
