package com.example;

import com.example.Dao.DaoImpl.TrainingDaoImpl;
import com.example.Dao.InMemoryStorage;
import com.example.model.Training;
import com.example.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingDaoImplTest {

    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private TrainingDaoImpl trainingDao;

    private Training training;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        training = TestUtil.createDummyTraining();
        when(storage.getNamespace("training")).thenReturn(new java.util.HashMap<>());
    }

    @Test
    void create() {
        trainingDao.create(training);
        Map<String, Object> trainings = storage.getNamespace("training");
        assertEquals(training, trainings.get(training.getTrainingName()));
    }

    @Test
    void select() {
        trainingDao.create(training);
        Training selectedTraining = trainingDao.select(training.getTrainingName());
        assertEquals(training, selectedTraining);
    }

    @Test
    void selectAll() {
        trainingDao.create(training);
        List<Training> trainings = trainingDao.selectAll();
        assertEquals(1, trainings.size());
        assertEquals(training, trainings.get(0));
    }
}
