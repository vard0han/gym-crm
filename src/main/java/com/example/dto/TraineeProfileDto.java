package com.example.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TraineeProfileDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private boolean isActive;
    private List<TrainerDto> trainers;
}
