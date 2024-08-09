package com.example.dto;

import lombok.Data;

import java.util.List;

@Data
public class TrainerProfileDto {
    private String firstName;
    private String lastName;
    private TrainingTypeDto specialization;
    private boolean isActive;
    private List<TraineeDto> trainees;
}
