package com.example.dto;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class TrainingRequestDto {
    private String traineeUsername;
    private String trainerUsername;
    private String trainingName;
    private LocalDate trainingDate;
    private Duration trainingDuration;
}
