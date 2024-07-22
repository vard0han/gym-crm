package com.example.service;

import com.example.model.Trainee;

import java.util.List;

public interface TraineeService {
    void createTrainee(Trainee trainee);
    void updateTrainee(Trainee trainee);
    void deleteTrainee(String username);
    Trainee getTrainee(String username);
    List<Trainee> getAllTrainee();
}
