package com.example.service;

import com.example.model.InMemory.Training;

import java.util.List;

public interface TrainingServiceInMemory {
    void createTraining(Training training);

    Training getTraining(String trainingName);

    List<Training> getAllTraining();
}