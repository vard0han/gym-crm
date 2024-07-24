package com.example;

import com.example.facade.GymFacade;
import com.example.model.InMemory.Trainee;
import com.example.model.InMemory.Trainer;
import com.example.model.InMemory.Training;
import com.example.service.TraineeServiceInMemory;
import com.example.service.TrainerServiceInMemory;
import com.example.service.TrainingServiceInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GymFacadeTest {

    @Mock
    private TrainerServiceInMemory trainerService;

    @Mock
    private TraineeServiceInMemory traineeService;

    @Mock
    private TrainingServiceInMemory trainingService;

    @InjectMocks
    private GymFacade gymFacade;

    private Trainee dummyTrainee;
    private Trainer dummyTrainer;
    private Training dummyTraining;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dummyTrainee = new Trainee();
        dummyTrainee.setUsername("john_doe");

        dummyTrainer = new Trainer();
        dummyTrainer.setUsername("jane_doe");

        dummyTraining = new Training();
        dummyTraining.setTrainingName("Morning Yoga");
    }

    @Test
    void testCreateTrainee() {
        gymFacade.createTrainee(dummyTrainee);
        verify(traineeService, times(1)).createTrainee(dummyTrainee);
    }

    @Test
    void testUpdateTrainee() {
        gymFacade.updateTrainee(dummyTrainee);
        verify(traineeService, times(1)).updateTrainee(dummyTrainee);
    }

    @Test
    void testDeleteTrainee() {
        gymFacade.deleteTrainee("john_doe");
        verify(traineeService, times(1)).deleteTrainee("john_doe");
    }

    @Test
    void testGetTrainee() {
        when(traineeService.getTrainee("john_doe")).thenReturn(dummyTrainee);
        Trainee result = gymFacade.getTrainee("john_doe");
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(traineeService, times(1)).getTrainee("john_doe");
    }

    @Test
    void testGetAllTrainees() {
        List<Trainee> trainees = new ArrayList<>();
        trainees.add(dummyTrainee);
        when(traineeService.getAllTrainee()).thenReturn(trainees);

        List<Trainee> result = gymFacade.getAllTrainees();
        assertEquals(1, result.size());
        assertEquals("john_doe", result.get(0).getUsername());
        verify(traineeService, times(1)).getAllTrainee();
    }

    @Test
    void testCreateTrainer() {
        gymFacade.createTrainer(dummyTrainer);
        verify(trainerService, times(1)).createTrainer(dummyTrainer);
    }

    @Test
    void testUpdateTrainer() {
        gymFacade.updateTrainer(dummyTrainer);
        verify(trainerService, times(1)).updateTrainer(dummyTrainer);
    }

    @Test
    void testDeleteTrainer() {
        gymFacade.deleteTrainer("jane_doe");
        verify(trainerService, times(1)).deleteTrainer("jane_doe");
    }

    @Test
    void testGetTrainer() {
        when(trainerService.getTrainer("jane_doe")).thenReturn(dummyTrainer);
        Trainer result = gymFacade.getTrainer("jane_doe");
        assertNotNull(result);
        assertEquals("jane_doe", result.getUsername());
        verify(trainerService, times(1)).getTrainer("jane_doe");
    }

    @Test
    void testGetAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(dummyTrainer);
        when(trainerService.getAllTrainer()).thenReturn(trainers);

        List<Trainer> result = gymFacade.getAllTrainers();
        assertEquals(1, result.size());
        assertEquals("jane_doe", result.get(0).getUsername());
        verify(trainerService, times(1)).getAllTrainer();
    }

    @Test
    void testCreateTraining() {
        gymFacade.createTraining(dummyTraining);
        verify(trainingService, times(1)).createTraining(dummyTraining);
    }

    @Test
    void testGetTraining() {
        when(trainingService.getTraining("Morning Yoga")).thenReturn(dummyTraining);
        Training result = gymFacade.getTraining("Morning Yoga");
        assertNotNull(result);
        assertEquals("Morning Yoga", result.getTrainingName());
        verify(trainingService, times(1)).getTraining("Morning Yoga");
    }

    @Test
    void testGetAllTrainings() {
        List<Training> trainings = new ArrayList<>();
        trainings.add(dummyTraining);
        when(trainingService.getAllTraining()).thenReturn(trainings);

        List<Training> result = gymFacade.getAllTrainings();
        assertEquals(1, result.size());
        assertEquals("Morning Yoga", result.get(0).getTrainingName());
        verify(trainingService, times(1)).getAllTraining();
    }
}
