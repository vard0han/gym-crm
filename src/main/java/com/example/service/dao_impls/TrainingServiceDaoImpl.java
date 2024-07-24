package com.example.service.dao_impls;

import com.example.Dao.repository.TrainingRepository;
import com.example.model.Training;
import com.example.service.TrainingService;
import com.example.service.TrainingServiceInMemory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TrainingServiceDaoImpl implements TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;
    @Override
    public void addTraining(Training training) {
        trainingRepository.save(training);
    }

    @Override
    public Training getTraining(String trainingName) {
        return trainingRepository.findByName(trainingName).get();
    }

    @Override
    public List<Training> getAllTraining() {
        return trainingRepository.findAll();
    }
}
