package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeRegistrationDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
}
