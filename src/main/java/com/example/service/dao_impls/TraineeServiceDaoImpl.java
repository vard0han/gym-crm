package com.example.service.dao_impls;

import com.example.Dao.repository.TraineeRepository;
import com.example.Dao.repository.TrainingRepository;
import com.example.model.AppUser;
import com.example.model.Trainee;
import com.example.Dao.repository.UserRepository;
import com.example.model.Training;
import com.example.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TraineeServiceDaoImpl implements TraineeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    private TrainingRepository trainingRepository;
    @Override
    public void createTrainee(Trainee trainee) {
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
    public void updateTrainee(Trainee trainee) {
        AppUser user = trainee.getUser();
        userRepository.save(user);
        traineeRepository.save(trainee);
    }

    @Override
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
    public void changePassword(String username, String newPassword) {
        Optional<Trainee> trainee = traineeRepository.findByUser_Username(username);
        trainee.ifPresent(t -> {
            t.getUser().setPassword(newPassword);
            traineeRepository.save(t);
        });
    }

    @Override
    public void activateDeactivateTrainee(String username, boolean isActive) {
        Optional<Trainee> trainee = traineeRepository.findByUser_Username(username);
        trainee.ifPresent(t -> {
            t.getUser().setActive(isActive);
            traineeRepository.save(t);
        });
    }

    @Override
    public List<Training> getTraineeTrainings(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType) {
        return trainingRepository.findByTrainee_UsernameAndTrainingDateBetweenAndTrainer_NameAndTrainingType_TrainingTypeName(
                username, fromDate, toDate, trainerName, trainingType);
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
