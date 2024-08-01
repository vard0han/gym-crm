package com.example.service;


import com.example.model.InMemory.Trainer;

import java.util.List;

public interface TrainerServiceInMemory {
    void createTrainer(Trainer trainer);
    void updateTrainer(Trainer trainer);
    void deleteTrainer(String username);
    Trainer getTrainer(String username);
    List<Trainer> getAllTrainer();
}
