package com.example.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Trainer extends User{
    private String specialization;
}
