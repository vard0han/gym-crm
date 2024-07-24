package com.example.service.dao_impls;

import com.example.Dao.repository.TraineeRepository;
import com.example.Dao.repository.TrainerRepository;
import com.example.Dao.repository.TrainingRepository;
import com.example.Dao.repository.UserRepository;
import com.example.model.AppUser;
import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.Training;
import com.example.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TrainerServiceDaoImpl implements TrainerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private TraineeRepository traineeRepository;
    @Override
    public void createTrainer(Trainer trainer) {
        AppUser user = new AppUser();
        user.setUsername(generateUsername(trainer));
        user.setPassword(generatePassword());
        user.setLastName(trainer.getLastName());
        user.setFirstName(trainer.getFirstName());
        user.setActive(trainer.isActive());
        user = userRepository.save(user);

        trainer.setUser(user);
        trainerRepository.save(trainer);
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        AppUser user = trainer.getUser();
        userRepository.save(user);
        trainerRepository.save(trainer);
    }

    @Override
    public void deleteTrainer(String username) {
        trainerRepository.findByUser_Username(username).ifPresent(trainer -> {
            trainerRepository.delete(trainer);
            userRepository.delete(trainer.getUser());
        });
    }

    @Override
    public Trainer getTrainer(String username) {
        return trainerRepository.findByUser_Username(username).get();
    }

    @Override
    public List<Trainer> getAllTrainer() {
        return trainerRepository.findAll();
    }

    @Override
    public void changePassword(String username, String newPassword) {
        Optional<Trainer> trainer = trainerRepository.findByUser_Username(username);
        trainer.ifPresent(t -> {
            t.getUser().setPassword(newPassword);
            trainerRepository.save(t);
        });
    }

    @Override
    public void activateDeactivateTrainer(String username, boolean isActive) {
        Optional<Trainer> trainer = trainerRepository.findByUser_Username(username);
        trainer.ifPresent(t -> {
            t.getUser().setActive(isActive);
            trainerRepository.save(t);
        });
    }

    @Override
    public List<Training> getTrainerTrainings(String username, LocalDate fromDate, LocalDate toDate, String traineeName) {
        return trainingRepository.findByTrainer_UsernameAndTrainingDateBetweenAndTrainee_Name(username,fromDate,toDate,traineeName);
    }

    @Override
    public List<Trainer> getAvailableTrainersForTrainee(String traineeUsername) {
        Trainee trainee = traineeRepository.findByUser_Username(traineeUsername)
                .orElseThrow(() -> new RuntimeException("Trainee not found"));
        List<Trainer> allTrainers = trainerRepository.findAll();
        Set<Trainer> assignedTrainers = trainee.getTrainers();
        List<Trainer> availableTrainers = allTrainers.stream()
                .filter(trainer -> !assignedTrainers.contains(trainer))
                .collect(Collectors.toList());

        return availableTrainers;
    }
    private String generateUsername(Trainer trainer) {
        String baseUsername = trainer.getFirstName() + "." + trainer.getLastName();
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
