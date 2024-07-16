package com.example.service.impl;

import com.example.Dao.TrainingDao;
import com.example.model.Training;
import com.example.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingDao trainingDao;

    @Autowired
    public TrainingServiceImpl(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }
    @Override
    public void createTraining(Training training) {
        trainingDao.create(training);
    }

    @Override
    public Training getTraining(String trainingName) {
        return trainingDao.select(trainingName);
    }

    @Override
    public List<Training> getAllTraining() {
        return trainingDao.selectAll();
    }
}
