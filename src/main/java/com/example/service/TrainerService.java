package com.example.service;

import com.example.model.Trainer;
import com.example.model.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainerService {
    void createTrainer(Trainer trainer);
    void updateTrainer(Trainer trainer);
    void deleteTrainer(String username);
    Trainer getTrainer(String username);
    List<Trainer> getAllTrainer();
    boolean changePassword(String username,String oldPassword, String newPassword);
    void activateDeactivateTrainer(String username, Boolean isActive);
    List<Training> getTrainerTrainings(String username, LocalDate fromDate, LocalDate toDate, String traineeName);
    List<Trainer> getAvailableTrainersForTrainee(String traineeUsername);

    boolean validateLogin(String username, String password);
}
