package com.example.service.impl;

import com.example.Dao.UserDao;
import com.example.model.Trainer;
import com.example.service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    private final UserDao<Trainer> trainerUserDao;

    @Autowired
    public TrainerServiceImpl(UserDao<Trainer> trainerUserDao) {
        this.trainerUserDao = trainerUserDao;
    }

    @Override
    public void createTrainer(Trainer trainer) {
        trainer.setUsername(generateUsername(trainer));
        trainer.setPassword(generatePassword());
        trainerUserDao.create(trainer);
        logger.info("Created trainer: {}", trainer.getUsername());
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        trainerUserDao.update(trainer);
        logger.info("Updated trainer: {}", trainer.getUsername());
    }

    @Override
    public void deleteTrainer(String username) {
        trainerUserDao.delete(username);
        logger.info("Deleted trainer with username: {}", username);
    }

    @Override
    public Trainer getTrainer(String username) {
        Trainer trainer = trainerUserDao.select(username);
        if (trainer == null) {
            logger.warn("Trainer with username '{}' not found", username);
        }
        return trainer;
    }

    @Override
    public List<Trainer> getAllTrainer() {
        List<Trainer> trainers = trainerUserDao.selectAll();
        logger.debug("Fetched {} trainers", trainers.size());
        return trainers;
    }

    private String generateUsername(Trainer trainer) {
        String baseUsername = trainer.getFirstName() + "." + trainer.getLastName();
        String username = baseUsername;
        int count = 1;
        while(trainerUserDao.select(username) != null){
            username = baseUsername + count;
            count++;
        }
        return username;
    }

    private String generatePassword() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    }
}
