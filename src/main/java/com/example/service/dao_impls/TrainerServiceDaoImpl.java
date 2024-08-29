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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrainerServiceDaoImpl implements TrainerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private void validateTrainer(Trainer trainer) {
        if (trainer.getFirstName() == null || trainer.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (trainer.getLastName() == null || trainer.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (trainer.getSpecialization() == null) {
            throw new IllegalArgumentException("Specialization is required");
        }

    }
    @Override
    @Transactional
    public void createTrainer(Trainer trainer) {
        validateTrainer(trainer);

        AppUser user = new AppUser();
        user.setUsername(generateUsername(trainer));
        user.setPassword(passwordEncoder.encode(generatePassword()));
        user.setLastName(trainer.getLastName());
        user.setFirstName(trainer.getFirstName());
        user.setActive(trainer.isActive());
        user = userRepository.save(user);

        trainer.setUser(user);
        trainerRepository.save(trainer);
    }

    @Override
    @Transactional
    public void updateTrainer(Trainer trainer) {
        validateTrainer(trainer);

        AppUser user = trainer.getUser();
        userRepository.save(user);
        trainerRepository.save(trainer);
    }

    @Override
    @Transactional
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
    @Transactional
    public boolean changePassword(String username,String oldPassword, String newPassword) {
        Optional<Trainer> trainer = trainerRepository.findByUser_Username(username);
        if(!validateLogin(username,oldPassword)) {
            return false;
        }
        trainer.ifPresent(t -> {
            t.getUser().setPassword(passwordEncoder.encode(newPassword));
            trainerRepository.save(t);
        });
        return true;
    }

    @Override
    @Transactional
    public void activateDeactivateTrainer(String username, Boolean isActive) {
        Optional<Trainer> trainer = trainerRepository.findByUser_Username(username);
        trainer.ifPresent(t -> {
            AppUser user = t.getUser();
            if (isActive == null) {
                user.setActive(!user.isActive());
            } else {
                user.setActive(isActive);
            }
            userRepository.save(user);
            trainerRepository.save(t);
        });
    }

    @Override
    public List<Training> getTrainerTrainings(String username, LocalDate fromDate, LocalDate toDate, String traineeName) {
        return trainingRepository.findTrainerTrainings(username,fromDate,toDate,traineeName);
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

    @Override
    public boolean validateLogin(String username, String password) {
        Trainer trainer = trainerRepository.findByUser_Username(username).get();
        return passwordEncoder.matches(password, trainer.getUser().getPassword());
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
