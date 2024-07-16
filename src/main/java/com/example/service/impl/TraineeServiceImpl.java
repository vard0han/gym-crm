package com.example.service.impl;

import com.example.Dao.UserDao;
import com.example.model.Trainee;
import com.example.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    UserDao<Trainee> traineeUserDao;
    @Override
    public void CreateTrainee(Trainee trainee) {
        trainee.setUsername(generateUsername(trainee));
        trainee.setPassword(generatePassword());
        traineeUserDao.create(trainee);
    }

    @Override
    public void updateTrainee(Trainee trainee) {
        traineeUserDao.update(trainee);
    }

    @Override
    public void deleteTrainee(String username) {
        traineeUserDao.delete(username);
    }

    @Override
    public Trainee getTrainee(String username) {
        return traineeUserDao.select(username);
    }

    @Override
    public List<Trainee> getAllTrainee() {
        return traineeUserDao.selectAll();
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
