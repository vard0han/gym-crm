package daoTest;

import com.example.WebApplication;
import com.example.model.*;
import com.example.Dao.repository.*;
import com.example.service.TrainerService;
import com.example.service.dao_impls.TraineeServiceDaoImpl;
import com.example.service.dao_impls.TrainerServiceDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = WebApplication.class)
public class TrainerServiceDaoImplTest {

    @Autowired
    private TrainerServiceDaoImpl trainerService;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private TraineeServiceDaoImpl traineeService;

    @BeforeEach
    public void setUp() {
        trainingRepository.deleteAll();
        trainerRepository.deleteAll();
        userRepository.deleteAll();
        trainingTypeRepository.deleteAll();
        traineeRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testCreateTrainer() {
        TrainingType specialization = new TrainingType();
        specialization.setTrainingTypeName("Cardio");
        trainingTypeRepository.save(specialization);

        Trainer trainer = new Trainer();
        trainer.setFirstName("Jane");
        trainer.setLastName("Doe");
        trainer.setSpecialization(specialization);
        trainer.setActive(true);

        trainerService.createTrainer(trainer);

        Trainer found = trainerRepository.findByUser_Username("Jane.Doe").orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("Jane");
        assertThat(found.getSpecialization().getTrainingTypeName()).isEqualTo("Cardio");
    }

    @Test
    @Transactional
    public void testUpdateTrainer() {
        TrainingType specialization = new TrainingType();
        specialization.setTrainingTypeName("Cardio");
        trainingTypeRepository.save(specialization);

        Trainer trainer = new Trainer();
        trainer.setFirstName("Jane");
        trainer.setLastName("Doe");
        trainer.setSpecialization(specialization);
        trainer.setActive(true);

        trainerService.createTrainer(trainer);

        Trainer found = trainerRepository.findByUser_Username("Jane.Doe").orElse(null);
        found.setLastName("Smith");
        TrainingType newSpecialization = new TrainingType();
        newSpecialization.setTrainingTypeName("Strength");
        trainingTypeRepository.save(newSpecialization);
        found.setSpecialization(newSpecialization);
        trainerService.updateTrainer(found);

        Trainer updated = trainerRepository.findByUser_Username("Jane.Doe").orElse(null);
        assertThat(updated.getLastName()).isEqualTo("Smith");
        assertThat(updated.getSpecialization().getTrainingTypeName()).isEqualTo("Strength");
    }

    @Test
    @Transactional
    public void testDeleteTrainer() {
        TrainingType specialization = new TrainingType();
        specialization.setTrainingTypeName("Cardio");
        trainingTypeRepository.save(specialization);

        Trainer trainer = new Trainer();
        trainer.setFirstName("Jane");
        trainer.setLastName("Doe");
        trainer.setUsername("janedoe");
        trainer.setPassword("password");
        trainer.setSpecialization(specialization);
        trainer.setActive(true);

        trainerService.createTrainer(trainer);
        trainerService.deleteTrainer("janedoe");

        Trainer deleted = trainerRepository.findByUser_Username("janedoe").orElse(null);
        assertThat(deleted).isNull();
    }

    @Test
    @Transactional
    public void testGetTrainer() {
        TrainingType specialization = new TrainingType();
        specialization.setTrainingTypeName("Cardio");
        trainingTypeRepository.save(specialization);

        Trainer trainer = new Trainer();
        trainer.setFirstName("Jane");
        trainer.setLastName("Doe");
        trainer.setSpecialization(specialization);
        trainer.setActive(true);

        trainerService.createTrainer(trainer);

        Trainer found = trainerService.getTrainer("Jane.Doe");
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("Jane");
    }

    @Test
    @Transactional
    public void testChangePassword() {
        TrainingType specialization = new TrainingType();
        specialization.setTrainingTypeName("Cardio");
        trainingTypeRepository.save(specialization);

        Trainer trainer = new Trainer();
        trainer.setFirstName("Jane");
        trainer.setLastName("Doe");
        trainer.setSpecialization(specialization);
        trainer.setActive(true);

        trainerService.createTrainer(trainer);
        trainerService.changePassword("Jane.Doe", "newpassword");

        Trainer found = trainerRepository.findByUser_Username("Jane.Doe").orElse(null);
        assertThat(found.getUser().getPassword()).isEqualTo("newpassword");
    }

    @Test
    @Transactional
    public void testActivateDeactivateTrainer() {
        TrainingType specialization = new TrainingType();
        specialization.setTrainingTypeName("Cardio");
        trainingTypeRepository.save(specialization);

        Trainer trainer = new Trainer();
        trainer.setFirstName("Jane");
        trainer.setLastName("Doe");
        trainer.setSpecialization(specialization);
        trainer.setActive(true);

        trainerService.createTrainer(trainer);
        trainerService.activateDeactivateTrainer("Jane.Doe", false);

        Trainer found = trainerRepository.findByUser_Username("Jane.Doe").orElse(null);
        assertThat(found.getUser().isActive()).isFalse();

        trainerService.activateDeactivateTrainer("Jane.Doe", null);
        assertThat(found.getUser().isActive()).isTrue();
    }

    @Test
    @Transactional
    public void testGetAllTrainers() {
        TrainingType specialization1 = new TrainingType();
        specialization1.setTrainingTypeName("Cardio");
        trainingTypeRepository.save(specialization1);

        TrainingType specialization2 = new TrainingType();
        specialization2.setTrainingTypeName("Strength");
        trainingTypeRepository.save(specialization2);

        Trainer trainer1 = new Trainer();
        trainer1.setFirstName("Jane");
        trainer1.setLastName("Doe");
        trainer1.setSpecialization(specialization1);
        trainerService.createTrainer(trainer1);

        Trainer trainer2 = new Trainer();
        trainer2.setFirstName("John");
        trainer2.setLastName("Smith");
        trainer2.setSpecialization(specialization2);
        trainerService.createTrainer(trainer2);

        List<Trainer> expectedTrainers = Arrays.asList(trainer1, trainer2);
        List<Trainer> actualTrainers = trainerService.getAllTrainer();
        for (int i = 0; i < 2; i++) {
            assertEquals(expectedTrainers.get(i).getFirstName(), actualTrainers.get(i).getFirstName());
        }
    }
    @Test
    @Transactional
    public void testAlreadyExistUsername() {
        TrainingType specialization = new TrainingType();
        specialization.setTrainingTypeName("Cardio");
        trainingTypeRepository.save(specialization);

        Trainer trainer = new Trainer();
        trainer.setFirstName("Jane");
        trainer.setLastName("Doe");
        trainer.setSpecialization(specialization);
        trainer.setActive(true);

        trainerService.createTrainer(trainer);

        Trainer found = trainerRepository.findByUser_Username("Jane.Doe").orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("Jane");

        TrainingType specialization2 = new TrainingType();
        specialization2.setTrainingTypeName("Cardio");
        trainingTypeRepository.save(specialization2);

        Trainer trainer2 = new Trainer();
        trainer2.setFirstName("Jane");
        trainer2.setLastName("Doe");
        trainer2.setSpecialization(specialization2);
        trainer2.setActive(true);

        trainerService.createTrainer(trainer2);

        Trainer found2 = trainerRepository.findByUser_Username("Jane.Doe1").orElse(null);
        assertThat(found2).isNotNull();
        assertThat(found2.getFirstName()).isEqualTo("Jane");
    }

    @Test
    @Transactional
    public void testInvalidArguments(){
        TrainingType specialization = new TrainingType();
        specialization.setTrainingTypeName("Cardio");
        trainingTypeRepository.save(specialization);

        Trainer trainer = new Trainer();
        trainer.setFirstName(null);
        trainer.setLastName("Doe");
        trainer.setSpecialization(specialization);
        trainer.setActive(true);

        assertThrows(IllegalArgumentException.class, () -> {
            trainerService.createTrainer(trainer);
        }, "First name is required");


        Trainer trainer2 = new Trainer();
        trainer2.setFirstName("jane");
        trainer2.setLastName(null);
        trainer2.setSpecialization(specialization);
        trainer2.setActive(true);

        assertThrows(IllegalArgumentException.class, () -> {
            trainerService.createTrainer(trainer2);
        }, "Last name is required");

        Trainer trainer3 = new Trainer();
        trainer3.setFirstName("jane");
        trainer3.setLastName("Doe");
        trainer3.setSpecialization(null);
        trainer3.setActive(true);

        assertThrows(IllegalArgumentException.class, () -> {
            trainerService.createTrainer(trainer3);
        }, "Specialization is required");
    }
    @Test
    @Transactional
    public void testGetAvailableTrainersForTrainee() {
        TrainingType specialization1 = new TrainingType();
        specialization1.setTrainingTypeName("Cardio");
        trainingTypeRepository.save(specialization1);

        // Create and save trainers
        Trainer trainer1 = new Trainer();
        trainer1.setFirstName("Jane");
        trainer1.setLastName("Doe");
        trainer1.setSpecialization(specialization1);
        trainer1.setActive(true);
        trainerService.createTrainer(trainer1);

        TrainingType specialization2 = new TrainingType();
        specialization2.setTrainingTypeName("Strength");
        trainingTypeRepository.save(specialization2);

        Trainer trainer2 = new Trainer();
        trainer2.setFirstName("John");
        trainer2.setLastName("Smith");
        trainer2.setSpecialization(specialization2);
        trainer2.setActive(true);
        trainerService.createTrainer(trainer2);

        Trainer trainer3 = new Trainer();
        trainer3.setFirstName("Alice");
        trainer3.setLastName("Johnson");
        trainer3.setSpecialization(specialization1);
        trainer3.setActive(true);
        trainerService.createTrainer(trainer3);

        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setActive(true);

        trainee.setTrainers(new HashSet<>(Arrays.asList(trainer1, trainer2)));
        traineeService.createTrainee(trainee);

        List<Trainer> availableTrainers = trainerService.getAvailableTrainersForTrainee("John.Doe");

        assertThat(availableTrainers).hasSize(1); // Only trainer3 should be available
        assertThat(availableTrainers).contains(trainer3); // trainer3 should be available
        assertThat(availableTrainers).doesNotContain(trainer1, trainer2); // trainer1 and trainer2 should not be available
    }
}
