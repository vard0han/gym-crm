package com.daoTest;

import com.example.Dao.repository.TraineeRepository;
import com.example.Dao.repository.TrainingTypeRepository;
import com.example.WebApplication;
import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.Training;
import com.example.Dao.repository.TrainingRepository;
import com.example.model.TrainingType;
import com.example.service.TrainingService;
import com.example.service.dao_impls.TraineeServiceDaoImpl;
import com.example.service.dao_impls.TrainerServiceDaoImpl;
import com.example.service.dao_impls.TrainingServiceDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = WebApplication.class)
public class TrainingServiceDaoImplTest {

    @Autowired
    private TrainingServiceDaoImpl trainingService;

    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    private TraineeServiceDaoImpl traineeService;
    @Autowired
    private TrainerServiceDaoImpl trainerService;
    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @BeforeEach
    public void setUp() {
        trainingRepository.deleteAll();
        traineeRepository.deleteAll();
    }

    private Trainee createTrainee(){
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        traineeService.createTrainee(trainee);
        return trainee;
    }
    private Training createTraining(String name){
        Training training = new Training();
        training.setTrainingName(name);
        training.setTrainee(createTrainee());

        TrainingType specialization = new TrainingType();
        specialization.setTrainingTypeName("Cardio");
        trainingTypeRepository.save(specialization);

        Trainer trainer = new Trainer();
        trainer.setFirstName("Jane");
        trainer.setLastName("Doe");
        trainer.setSpecialization(specialization);
        trainer.setActive(true);

        trainerService.createTrainer(trainer);

        training.setTrainer(trainer);
        training.setTrainingType(specialization);
        trainingService.addTraining(training);
        return training;
    }
    @Test
    @Transactional
    public void testAddTraining() {
        createTraining("Pilates");

        Training found = trainingRepository.findBytrainingName("Pilates").orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getTrainingName()).isEqualTo("Pilates");
    }

    @Test
    @Transactional
    public void testGetTraining() {
        createTraining("Pilates");

        Training found = trainingService.getTraining("Pilates");
        assertThat(found).isNotNull();
        assertThat(found.getTrainingName()).isEqualTo("Pilates");
    }

    @Test
    @Transactional
    public void testGetAllTraining() {
        Training training1 = createTraining("Yoga");
        Training training2 = createTraining("Pilates");

        List<Training> expectedTrainings = Arrays.asList(training1, training2);
        List<Training> actualTrainings = trainingService.getAllTraining();
        assertThat(actualTrainings).hasSize(2);
        assertThat(actualTrainings).containsAll(expectedTrainings);
    }

    @Test
    @Transactional
    public void testAddTrainingWithExistingName() {
        Training training1 = createTraining("Yoga");

        assertThrows(Exception.class, () -> {
            trainingService.addTraining(training1);
        });
    }

}
