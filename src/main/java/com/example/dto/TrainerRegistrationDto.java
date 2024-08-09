package com.example.dto;

import com.example.model.TrainingType;
import lombok.Data;

@Data
public class TrainerRegistrationDto {
    private String firstName;
    private String lastName;
    private TrainingType specialization;
}
