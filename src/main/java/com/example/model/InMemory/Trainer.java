package com.example.model.InMemory;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Trainer extends User{
    private String specialization;
}
