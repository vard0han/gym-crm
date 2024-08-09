package com.example.controller;

import com.example.dto.TraineeRegistrationDto;
import com.example.model.Trainee;
import com.example.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.example.Mapper.traineeMapper.traineeRegDtoToTrainee;

@RestController
@RequestMapping("api/trainee")
public class TraineeController {
    @Autowired
    TraineeService traineeService;
    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> registerTrainee(@RequestBody TraineeRegistrationDto traineeDTO) {

        Trainee trainee = traineeRegDtoToTrainee(traineeDTO);
        traineeService.createTrainee(trainee);
        Map<String, String> response = new HashMap<>();
        response.put("username", trainee.getUsername());
        response.put("password", trainee.getPassword());
        return ResponseEntity.ok(response);
    }


}
