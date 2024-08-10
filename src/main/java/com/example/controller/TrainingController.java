package com.example.controller;

import com.example.dto.TrainingRequestDto;
import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.Training;
import com.example.service.TraineeService;
import com.example.service.TrainerService;
import com.example.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.Mapper.TrainingMapper.DtoToTraining;

@RestController
@RequestMapping("api/training")
public class TrainingController {

    @Autowired
    TrainingService trainingService;
    @Autowired
    TrainerService trainerService;
    @Autowired
    TraineeService traineeService;
    @PostMapping("/training/add")
    public ResponseEntity<String> addTraining(@RequestBody TrainingRequestDto trainingRequestDTO){
        Trainer trainer = trainerService.getTrainer(trainingRequestDTO.getTrainerUsername());
        Trainee trainee = traineeService.getTrainee(trainingRequestDTO.getTraineeUsername());
        Training training = DtoToTraining(trainingRequestDTO,trainer,trainee);
        trainingService.addTraining(training);
        return ResponseEntity.ok("200 ok");
    }
}