package com.example.controller;

import com.example.dto.TrainerRegistrationDto;
import com.example.model.Trainer;
import com.example.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.example.Mapper.trainerMapper.trainerRegDtoToTrainee;

@RestController
@RequestMapping("api/trainer")
public class TrainerController {
    @Autowired
    TrainerService trainerService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerTrainer(@RequestBody TrainerRegistrationDto trainerDTO) {
        Trainer trainer = trainerRegDtoToTrainee(trainerDTO);
        trainerService.createTrainer(trainer);
        Map<String, String> response = new HashMap<>();
        response.put("username", trainer.getUsername());
        response.put("password", trainer.getPassword());
        return ResponseEntity.ok(response);
    }
}
