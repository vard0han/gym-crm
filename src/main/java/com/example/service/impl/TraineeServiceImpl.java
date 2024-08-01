package com.example.service.impl;

import com.example.Dao.UserDao;

import com.example.model.InMemory.Trainee;
import com.example.service.TraineeServiceInMemory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeServiceInMemory {
    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    private final UserDao<Trainee> traineeUserDao;

    @Autowired
    public TraineeServiceImpl(UserDao<Trainee> traineeUserDao) {
        this.traineeUserDao = traineeUserDao;
    }

    @Override
    public void createTrainee(Trainee trainee) {
        trainee.setUsername(generateUsername(trainee));
        trainee.setPassword(generatePassword());
        traineeUserDao.create(trainee);
        logger.info("Created trainee: {}", trainee.getUsername());
    }

    @Override
    public void updateTrainee(Trainee trainee) {
        traineeUserDao.update(trainee);
        logger.info("Updated trainee: {}", trainee.getUsername());
    }

    @Override
    public void deleteTrainee(String username) {
        traineeUserDao.delete(username);
        logger.info("Deleted trainee with username: {}", username);
    }

    @Override
    public Trainee getTrainee(String username) {
        Trainee trainee = traineeUserDao.select(username);
        if (trainee == null) {
            logger.warn("Trainee with username '{}' not found", username);
        }
        return trainee;
    }

    @Override
    public List<Trainee> getAllTrainee() {
        List<Trainee> trainees = traineeUserDao.selectAll();
        logger.debug("Fetched {} trainees", trainees.size());
        return trainees;
    }

    private String generateUsername(Trainee trainee) {
        String baseUsername = trainee.getFirstName() + "." + trainee.getLastName();
        String username = baseUsername;
        int count = 1;
        while (traineeUserDao.select(username) != null) {
            username = baseUsername + count;
            count++;
        }
        return username;
    }

    private String generatePassword() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    }
}
