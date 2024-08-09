package com.example.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TraineeRegistrationDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
}
