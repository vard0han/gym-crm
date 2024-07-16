package com.example.service.impl;

import com.example.Dao.UserDao;
import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    UserDao<Trainer> trainerUserDao;
    @Override
    public void CreateTrainer(Trainer trainer) {
        trainer.setUsername(generateUsername(trainer));
        trainer.setPassword(generatePassword());
        trainerUserDao.create(trainer);
    }
    @Override
    public void updateTrainer(Trainer trainer) {
        trainerUserDao.update(trainer);
    }

    @Override
    public void deleteTrainer(String username) {
        trainerUserDao.delete(username);
    }

    @Override
    public Trainer getTrainer(String username) {
        return trainerUserDao.select(username);
    }

    @Override
    public List<Trainer> getAllTrainer() {
        return trainerUserDao.selectAll();
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
