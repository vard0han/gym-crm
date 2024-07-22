package com.example.service;


import com.example.model.Trainer;

import java.util.List;

public interface TrainerService {
    void createTrainer(Trainer trainer);
    void updateTrainer(Trainer trainer);
    void deleteTrainer(String username);
    Trainer getTrainer(String username);
    List<Trainer> getAllTrainer();
}