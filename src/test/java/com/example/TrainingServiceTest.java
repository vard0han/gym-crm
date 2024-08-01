package com.example;

import com.example.Dao.TrainingDao;
import com.example.model.InMemory.Training;
import com.example.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceTest {

    @Mock
    private TrainingDao trainingDao;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private Training dummyTraining;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dummyTraining = new Training();
        dummyTraining.setTrainingName("Morning Yoga");
    }

    @Test
    void testCreateTraining() {
        trainingService.createTraining(dummyTraining);
        verify(trainingDao, times(1)).create(dummyTraining);
    }

    @Test
    void testGetTraining() {
        when(trainingDao.select("Morning Yoga")).thenReturn(dummyTraining);
        Training result = trainingService.getTraining("Morning Yoga");
        assertNotNull(result);
        assertEquals("Morning Yoga", result.getTrainingName());
        verify(trainingDao, times(1)).select("Morning Yoga");
    }

    @Test
    void testGetTrainingNotFound() {
        when(trainingDao.select("Non.Existent")).thenReturn(null);
        Training result = trainingService.getTraining("Non.Existent");
        assertNull(result);
        verify(trainingDao, times(1)).select("Non.Existent");
    }

    @Test
    void testGetAllTraining() {
        List<Training> trainings = new ArrayList<>();
        trainings.add(dummyTraining);
        when(trainingDao.selectAll()).thenReturn(trainings);
        List<Training> result = trainingService.getAllTraining();
        assertEquals(1, result.size());
        assertEquals("Morning Yoga", result.get(0).getTrainingName());
        verify(trainingDao, times(1)).selectAll();
    }
}
