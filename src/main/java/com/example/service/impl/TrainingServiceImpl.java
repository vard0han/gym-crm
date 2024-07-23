package com.example.service.impl;

import com.example.Dao.TrainingDao;
import com.example.model.InMemory.Training;
import com.example.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private final TrainingDao trainingDao;

    @Autowired
    public TrainingServiceImpl(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    @Override
    public void createTraining(Training training) {
        trainingDao.create(training);
        logger.info("Created training: {}", training.getTrainingName());
    }

    @Override
    public Training getTraining(String trainingName) {
        Training training = trainingDao.select(trainingName);
        if (training == null) {
            logger.warn("Training with name '{}' not found", trainingName);
        }
        return training;
    }

    @Override
    public List<Training> getAllTraining() {
        List<Training> trainings = trainingDao.selectAll();
        logger.debug("Fetched {} trainings", trainings.size());
        return trainings;
    }
}
