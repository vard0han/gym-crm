package com.example.dto;

import com.example.model.TrainingType;
import lombok.Data;

import java.util.List;

@Data
public class TrainerProfileDto {
    private String firstName;
    private String lastName;
    private TrainingType specialization;
    private boolean isActive;
    private List<TraineeDto> trainees;
}
