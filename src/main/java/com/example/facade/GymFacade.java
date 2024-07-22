package com.example.facade;

import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.Training;
import com.example.service.TraineeService;
import com.example.service.TrainerService;
import com.example.service.TrainingService;

import java.util.List;

public class GymFacade {
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingService trainingService;

    public GymFacade(TrainerService trainerService, TraineeService traineeService, TrainingService trainingService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.trainingService = trainingService;
    }

    public void createTrainee(Trainee trainee) {
        traineeService.createTrainee(trainee);
    }

    public void updateTrainee(Trainee trainee) {
        traineeService.updateTrainee(trainee);
    }

    public void deleteTrainee(String username) {
        traineeService.deleteTrainee(username);
    }

    public Trainee getTrainee(String username) {
        return traineeService.getTrainee(username);
    }

    public List<Trainee> getAllTrainees() {
        return traineeService.getAllTrainee();
    }
    public void createTrainer(Trainer trainer) {
        trainerService.createTrainer(trainer);
    }

    public void updateTrainer(Trainer trainer) {
        trainerService.updateTrainer(trainer);
    }

    public void deleteTrainer(String username) {
        trainerService.deleteTrainer(username);
    }

    public Trainer getTrainer(String username) {
        return trainerService.getTrainer(username);
    }

    public List<Trainer> getAllTrainers() {
        return trainerService.getAllTrainer();
    }
    public void createTraining(Training training) {
        trainingService.createTraining(training);
    }

    public Training getTraining(String trainingName) {
        return trainingService.getTraining(trainingName);
    }

    public List<Training> getAllTrainings() {
        return trainingService.getAllTraining();
    }
}