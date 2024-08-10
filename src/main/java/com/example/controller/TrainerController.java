package com.example.controller;

import com.example.Mapper.trainerMapper;
import com.example.dto.TrainerDto;
import com.example.dto.TrainerProfileDto;
import com.example.dto.TrainerRegistrationDto;
import com.example.model.Trainer;
import com.example.model.TrainingType;
import com.example.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.Mapper.trainerMapper.trainerProfileToDto;
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

    @GetMapping("/profile")
    public ResponseEntity<TrainerProfileDto> getTrainerProfile(@RequestParam String username){
        Trainer trainer = trainerService.getTrainer(username);
        TrainerProfileDto profileDto = trainerProfileToDto(trainer);
        return ResponseEntity.ok(profileDto);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<TrainerProfileDto> updateTrainerProfile(
            @RequestParam String username,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam TrainingType trainingType,
            @RequestParam Boolean isActive
            ){
        Trainer trainer = trainerService.getTrainer(username);
        trainer.setFirstName(firstName);
        trainer.setUsername(username);
        trainer.setLastName(lastName);
        trainer.setSpecialization(trainingType);
        trainer.setActive(isActive);
        trainerService.updateTrainer(trainer);
        return ResponseEntity.ok(trainerProfileToDto(trainer));
    }

    @GetMapping("/not-assigned")
    public ResponseEntity<List<TrainerDto>> getNotAssignedTrainers(@RequestParam String username){
        List<Trainer> trainers = trainerService.getAvailableTrainersForTrainee(username);
        List<TrainerDto> dtoList = trainers.stream().map(trainerMapper::trainerToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

}
