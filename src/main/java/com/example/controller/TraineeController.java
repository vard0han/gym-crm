package com.example.controller;

import com.example.Mapper.TrainingMapper;
import com.example.dto.TraineeProfileDto;
import com.example.dto.TraineeRegistrationDto;
import com.example.dto.TrainerDto;
import com.example.dto.TrainingDto;
import com.example.model.Trainee;
import com.example.model.Training;
import com.example.model.TrainingType;
import com.example.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            @RequestParam(required = false) LocalDate dateOfBirth,
            @RequestParam(required = false) String address,
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

    @PutMapping("/trainers")
    public ResponseEntity<List<TrainerDto>> updateTraineeTrainers(@RequestParam String traineeUsername, @RequestBody List<String> trainerUsernames){
        List<TrainerDto> updatedTrainers = traineeService.updateTraineeTrainers(traineeUsername, trainerUsernames);
        return ResponseEntity.ok(updatedTrainers);
    }

    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingDto>> getTraineeTrainings(
            @RequestParam String username,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @RequestParam(required = false) String trainerName,
            @RequestParam(required = false) String trainingType)
    {
        List<Training> trainings = traineeService.getTraineeTrainings(username,from,to,trainerName,trainingType);
        List<TrainingDto> trainingDtoList = trainings.stream().map(TrainingMapper::trainingToDto).collect(Collectors.toList());
        return ResponseEntity.ok(trainingDtoList);
    }

    @PatchMapping("/activate")
    public ResponseEntity<String> activateTrainee(@RequestParam String username, @RequestParam boolean isActive){
        traineeService.activateDeactivateTrainee(username,isActive);
        return ResponseEntity.ok("200 ok");
    }
}
