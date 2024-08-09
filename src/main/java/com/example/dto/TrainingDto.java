package com.example.dto;

import com.example.model.TrainingType;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class TrainingDto {
    private String trainingName;
    private LocalDate trainingDate;
    private TrainingType trainingType;
    private Duration trainingDuration;
    private TrainerDto trainer;
}
