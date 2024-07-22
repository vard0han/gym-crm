package com.example.Dao.DaoImpl;

import com.example.Dao.InMemoryStorage;
import com.example.Dao.TrainingDao;
import com.example.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    private static final Logger logger = LoggerFactory.getLogger(TrainingDaoImpl.class);

    @Autowired
    private InMemoryStorage storage;

    @Override
    public void create(Training training) {
        logger.debug("Creating training: {}", training.getTrainingName());
        Map<String, Object> trainings = storage.getNamespace("training");
        trainings.put(training.getTrainingName(), training);
        logger.info("Training created: {}", training.getTrainingName());
    }

    @Override
    public Training select(String trainingName) {
        logger.debug("Selecting training: {}", trainingName);
        Map<String, Object> trainings = storage.getNamespace("training");
        Training training = (Training) trainings.get(trainingName);
        logger.debug("Selected training: {}", training);
        return training;
    }

    @Override
    public List<Training> selectAll() {
        logger.debug("Selecting all trainings");
        Map<String, Object> trainings = storage.getNamespace("training");
        List<Training> trainingList = trainings.values().stream().map(t -> (Training) t).collect(Collectors.toList());
        logger.debug("Selected all trainings: {}", trainingList);
        return trainingList;
    }
}
