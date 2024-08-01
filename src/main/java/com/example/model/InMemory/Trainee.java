package com.example.model.InMemory;

import lombok.*;

import javax.annotation.sql.DataSourceDefinition;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Trainee extends User{
    private LocalDate dateOfBirth;
    private String address;
}
