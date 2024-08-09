package com.example.service.dao_impls;

import com.example.Dao.repository.TraineeRepository;
import com.example.Dao.repository.TrainingRepository;
import com.example.model.AppUser;
import com.example.model.Trainee;
import com.example.Dao.repository.UserRepository;
import com.example.model.Training;
import com.example.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceDaoImpl implements TraineeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    private TrainingRepository trainingRepository;

    private void validateTrainee(Trainee trainee) {
        if (trainee.getFirstName() == null || trainee.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (trainee.getLastName() == null || trainee.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }

    }
    @Override
    @Transactional
    public void createTrainee(Trainee trainee) {
        validateTrainee(trainee);

        AppUser user = new AppUser();
        user.setUsername(generateUsername(trainee));
        user.setPassword(generatePassword());
        user.setLastName(trainee.getLastName());
        user.setFirstName(trainee.getFirstName());
        user.setActive(trainee.isActive());
        user = userRepository.save(user);

        trainee.setUser(user);
        traineeRepository.save(trainee);
    }

    @Override
    @Transactional
    public void updateTrainee(Trainee trainee) {
        validateTrainee(trainee);

        AppUser user = trainee.getUser();
        userRepository.save(user);
        traineeRepository.save(trainee);
    }

    @Override
    @Transactional
    public void deleteTrainee(String username) {
        traineeRepository.findByUser_Username(username).ifPresent(trainee -> {
            traineeRepository.delete(trainee);
            userRepository.delete(trainee.getUser());
        });
    }

    @Override
    public Trainee getTrainee(String username) {
        return traineeRepository.findByUser_Username(username).get();
    }

    @Override
    public List<Trainee> getAllTrainee() {
        return traineeRepository.findAll();
    }

    @Override
    @Transactional
    public boolean changePassword(String username,String oldPassword, String newPassword) {
        Optional<Trainee> trainee = traineeRepository.findByUser_Username(username);
        if(!validateLogin(username,oldPassword))
            return false;
        trainee.ifPresent(t -> {
            t.getUser().setPassword(newPassword);
            traineeRepository.save(t);
        });
        return true;
    }

    @Override
    @Transactional
    public void activateDeactivateTrainee(String username, Boolean isActive) {
        Optional<Trainee> trainee = traineeRepository.findByUser_Username(username);
        trainee.ifPresent(t -> {
            AppUser user = t.getUser();
            if (isActive == null) {
                user.setActive(!user.isActive());
            } else {
                user.setActive(isActive);
            }
            userRepository.save(user);
            traineeRepository.save(t);
        });
    }


    @Override
    public List<Training> getTraineeTrainings(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType) {
        return trainingRepository.findTraineeTrainings(
                username, fromDate, toDate, trainerName, trainingType);
    }

    @Override
    public boolean validateLogin(String username, String password) {
        Trainee trainee = traineeRepository.findByUser_Username(username).get();
        if (trainee.getUser().getPassword().equals(password)){
            return true;
        }
        return false;
    }

    private String generateUsername(Trainee trainee) {
        String baseUsername = trainee.getFirstName() + "." + trainee.getLastName();
        String username = baseUsername;
        int count = 1;
        while (userRepository.findByUsername(username).isPresent()) {
            username = baseUsername + count;
            count++;
        }
        return username;
    }

    private String generatePassword() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    }
}
