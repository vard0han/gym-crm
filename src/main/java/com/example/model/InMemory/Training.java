package com.example.model.InMemory;

import lombok.*;

import java.time.Duration;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Training {
    private String traineeId;
    private String trainerId;
    private String trainingName;
    private String trainingType;
    private LocalDate trainingDate;
    private Duration trainingDuration;
}
