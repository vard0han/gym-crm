package com.example.service;

import com.example.dto.TrainerDto;
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
    boolean changePassword(String username,String oldPassword, String newPassword);
    void activateDeactivateTrainee(String username, Boolean isActive);
    List<Training> getTraineeTrainings(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType);

    boolean validateLogin(String username, String password);

    List<TrainerDto> updateTraineeTrainers(String traineeUsername, List<String> trainerUsernames);
}
