package com.example.service;

import com.example.model.InMemory.Trainee;

import java.util.List;

public interface TraineeServiceInMemory {
    void createTrainee(Trainee trainee);
    void updateTrainee(Trainee trainee);
    void deleteTrainee(String username);
    Trainee getTrainee(String username);
    List<Trainee> getAllTrainee();
}
