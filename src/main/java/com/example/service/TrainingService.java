package com.example.service;

import com.example.model.Training;

import java.util.List;

public interface TrainingService {
    void addTraining(Training training);

    Training getTraining(String trainingName);

    List<Training> getAllTraining();
}
