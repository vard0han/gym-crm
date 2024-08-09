package com.example.dto;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class TrainingDto {
    private String trainingName;
    private LocalDate trainingDate;
    private TrainingTypeDto trainingType;
    private Duration trainingDuration;
    private TrainerDto trainer;
}
