package com.example.dto;

import lombok.Data;

@Data
public class TrainerDto {
    private String username;
    private String firstName;
    private String lastName;
    private TrainingTypeDto specialization;
}
