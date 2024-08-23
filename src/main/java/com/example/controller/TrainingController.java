package com.example.controller;

import com.example.dto.TrainingRequestDto;
import com.example.dto.TrainingTypeDto;
import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.Training;
import com.example.model.TrainingType;
import com.example.service.TraineeService;
import com.example.service.TrainerService;
import com.example.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @PostMapping("/add")
    public ResponseEntity<String> addTraining(@RequestBody TrainingRequestDto trainingRequestDTO){
        Trainer trainer = trainerService.getTrainer(trainingRequestDTO.getTrainerUsername());
        Trainee trainee = traineeService.getTrainee(trainingRequestDTO.getTraineeUsername());
        Training training = DtoToTraining(trainingRequestDTO,trainer,trainee);
        trainingService.addTraining(training);
        return ResponseEntity.ok("200 ok");
    }

    @GetMapping("/types")
    public ResponseEntity<List<TrainingTypeDto>> getTrainingTypes() {
        List<Training> trainings= trainingService.getAllTraining();
        List<TrainingTypeDto> trainingTypes = new ArrayList<>();
        for(int i=0;i<trainings.size();i++){
            TrainingTypeDto trainingTypeDto = new TrainingTypeDto();
            trainingTypeDto.setTrainingType(trainings.get(i).getTrainingType());
            trainingTypeDto.setTrainingTypeId(trainingTypeDto.getTrainingTypeId());
            trainingTypes.add(trainingTypeDto);
        }
        return ResponseEntity.ok(trainingTypes);
    }
}