package com.example.util;

import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.Training;

import java.time.Duration;
import java.time.LocalDate;

public class TestUtil {
    public static Trainee createDummyTrainee() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setDateOfBirth(LocalDate.of(1990, 5, 15));
        trainee.setAddress("123 Test St, Testville");
        return trainee;
    }

    public static Trainer createDummyTrainer() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Jane");
        trainer.setLastName("Smith");
        trainer.setSpecialization("Fitness");
        return trainer;
    }

    public static Training createDummyTraining() {
        Training training = new Training();
        training.setTrainingName("Test Training");
        training.setTrainingType("Cardio");
        training.setTrainingDate(LocalDate.now());
        training.setTrainingDuration(Duration.ofMinutes(60));
        training.setTraineeId("testuser");
        training.setTrainerId("testtrainer");
        return training;
    }
}
