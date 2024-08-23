package com.daoTest;

import com.example.WebApplication;
import com.example.model.*;
import com.example.Dao.repository.*;
import com.example.service.TraineeService;
import com.example.service.dao_impls.TraineeServiceDaoImpl;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = WebApplication.class)
public class TraineeServiceDaoImplTest {
    @Autowired
    private TraineeServiceDaoImpl traineeService;

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    @BeforeEach
    public void setUp() {
        trainingRepository.deleteAll();
        traineeRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    @Transactional
    public void testCreateTrainee() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setActive(true);

        traineeService.createTrainee(trainee);

        Trainee found = traineeRepository.findByUser_Username("John.Doe").orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("John");
    }

    @Test
    @Transactional
    public void testUpdateTrainee() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setActive(true);

        traineeService.createTrainee(trainee);

        Trainee found = traineeRepository.findByUser_Username("John.Doe").orElse(null);
        found.setLastName("Smith");
        traineeService.updateTrainee(found);

        Trainee updated = traineeRepository.findByUser_Username("John.Doe").orElse(null);
        assertThat(updated.getLastName()).isEqualTo("Smith");
    }

    @Test
    @Transactional
    public void testGetTrainee() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setActive(true);

        traineeService.createTrainee(trainee);

        Trainee found = traineeService.getTrainee("John.Doe");
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("John");
    }

    @Test
    @Transactional
    public void testChangePassword() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setActive(true);

        traineeService.createTrainee(trainee);
        traineeService.changePassword("John.Doe", trainee.getUser().getPassword(), "newpassword");

        Trainee found = traineeRepository.findByUser_Username("John.Doe").orElse(null);
        assertThat(found.getUser().getPassword()).isEqualTo("newpassword");
    }

    @Test
    @Transactional
    public void testActivateDeactivateTrainee() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setActive(true);

        traineeService.createTrainee(trainee);
        traineeService.activateDeactivateTrainee("John.Doe", false);

        Trainee found = traineeRepository.findByUser_Username("John.Doe").orElse(null);
        assertThat(found.getUser().isActive()).isFalse();

        traineeService.activateDeactivateTrainee("John.Doe", null);
        assertThat(found.getUser().isActive()).isTrue();
    }

    @Test
    @Transactional
    public void testAlreadyExistUsername() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setActive(true);

        traineeService.createTrainee(trainee);
        Trainee found = traineeRepository.findByUser_Username("John.Doe").orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("John");

        Trainee trainee2 = new Trainee();
        trainee2.setFirstName("John");
        trainee2.setLastName("Doe");
        trainee2.setActive(true);
        traineeService.createTrainee(trainee2);

        Trainee found2 = traineeRepository.findByUser_Username("John.Doe1").orElse(null);
        assertThat(found2).isNotNull();
        assertThat(found2.getFirstName()).isEqualTo("John");
    }

    @Test
    @Transactional
    public void testInvalidFirstName(){
        Trainee trainee = new Trainee();
        trainee.setFirstName(null);
        trainee.setLastName(null);

        assertThrows(IllegalArgumentException.class, () -> {
            traineeService.createTrainee(trainee);
        }, "First name is required");

        Trainee trainee2 = new Trainee();
        trainee2.setFirstName("John");
        trainee2.setLastName(null);

        assertThrows(IllegalArgumentException.class, () -> {
            traineeService.createTrainee(trainee2);
        }, "Last name is required");
    }

    @Test
    @Transactional
    public void testGetAllTrainees() {
        Trainee trainee1 = new Trainee();
        trainee1.setFirstName("John");
        trainee1.setLastName("Doe");
        traineeService.createTrainee(trainee1);
        Trainee trainee2 = new Trainee();
        trainee2.setFirstName("Jane");
        trainee2.setLastName("Doe");
        traineeService.createTrainee(trainee2);
        List<Trainee> expectedTrainees = Arrays.asList(trainee1, trainee2);
        List<Trainee> actualTrainees = traineeService.getAllTrainee();
        for(int i=0;i<2;i++){
            assertEquals(expectedTrainees.get(i).getFirstName(),actualTrainees.get(i).getFirstName());
        }
    }

    @Test
    @Transactional
    public void testDeleteTrainee() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setUsername("johndoe");
        trainee.setPassword("password");
        trainee.setActive(true);

        traineeService.createTrainee(trainee);
        traineeService.deleteTrainee("johndoe");

        Trainee deleted = traineeRepository.findByUser_Username("johndoe").orElse(null);
        assertThat(deleted).isNull();
    }
}
