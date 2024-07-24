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
    void changePassword(String username, String newPassword);
    void activateDeactivateTrainer(String username, boolean isActive);
    List<Training> getTrainerTrainings(String username, LocalDate fromDate, LocalDate toDate, String traineeName);
    List<Trainer> getAvailableTrainersForTrainee(String traineeUsername);
}
