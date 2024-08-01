package com.example.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Training_Type")
public class TrainingType {
    private String trainingTypeName;
    @Id
    @GeneratedValue
    private Long id;
}
