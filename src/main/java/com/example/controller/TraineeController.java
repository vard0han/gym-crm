package com.example.controller;

import com.example.dto.TraineeProfileDto;
import com.example.dto.TraineeRegistrationDto;
import com.example.model.Trainee;
import com.example.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.example.Mapper.traineeMapper.*;

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

    @GetMapping("/profile")
    public ResponseEntity <TraineeProfileDto> getTraineeProfile(@RequestParam String username){
        Trainee trainee = traineeService.getTrainee(username);
        TraineeProfileDto profileDto = traineeProfileToDto(trainee);

        return ResponseEntity.ok(profileDto);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<TraineeProfileDto> updateTraineeProfile(
            @RequestParam String Username,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam LocalDate dateOfBirth,
            @RequestParam String address,
            @RequestParam Boolean isActive
            ){
        Trainee trainee = traineeService.getTrainee(Username);
        trainee.setUsername(Username);
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setAddress(address);
        trainee.setActive(isActive);
        traineeService.updateTrainee(trainee);
        TraineeProfileDto profileDto = traineeProfileToDto(trainee);

        return ResponseEntity.ok(profileDto);
    }

    @DeleteMapping("/profile/delete")
    public ResponseEntity<String> deleteTraineeProfile(@RequestParam String username){
        traineeService.deleteTrainee(username);
        return ResponseEntity.ok("200 ok");
    }
}
