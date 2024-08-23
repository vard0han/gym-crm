package com.example.dto;

import com.example.model.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerRegistrationDto {
    private String firstName;
    private String lastName;
    private TrainingType specialization;
}
