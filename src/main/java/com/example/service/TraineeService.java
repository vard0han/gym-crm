package com.example.service;

import com.example.model.Trainee;
import com.example.model.Training;

import java.time.LocalDate;
import java.util.List;

public interface TraineeService {
    void createTrainee(Trainee trainee);
    void updateTrainee(Trainee trainee);
    void deleteTrainee(String username);
    Trainee getTrainee(String username);
    List<Trainee> getAllTrainee();
    void changePassword(String username, String newPassword);
    void activateDeactivateTrainee(String username, Boolean isActive);
    List<Training> getTraineeTrainings(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType);
}
