package com.example;

import com.example.Dao.UserDao;
import com.example.model.Trainer;
import com.example.service.impl.TrainerServiceImpl;
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

class TrainerServiceTest {

    @Mock
    private UserDao<Trainer> trainerUserDao;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    private Trainer dummyTrainer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dummyTrainer = new Trainer();
        dummyTrainer.setFirstName("Alice");
        dummyTrainer.setLastName("Johnson");
        dummyTrainer.setUsername("Alice.Johnson");
    }

    @Test
    void testCreateTrainer() {
        trainerService.createTrainer(dummyTrainer);
        verify(trainerUserDao, times(1)).create(any(Trainer.class));
    }

    @Test
    void testUpdateTrainer() {
        trainerService.updateTrainer(dummyTrainer);
        verify(trainerUserDao, times(1)).update(dummyTrainer);
    }

    @Test
    void testDeleteTrainer() {
        trainerService.deleteTrainer("Alice.Johnson");
        verify(trainerUserDao, times(1)).delete("Alice.Johnson");
    }

    @Test
    void testGetTrainer() {
        when(trainerUserDao.select("Alice.Johnson")).thenReturn(dummyTrainer);
        Trainer result = trainerService.getTrainer("Alice.Johnson");
        assertNotNull(result);
        assertEquals("Alice.Johnson", result.getUsername());
        verify(trainerUserDao, times(1)).select("Alice.Johnson");
    }

    @Test
    void testGetTrainerNotFound() {
        when(trainerUserDao.select("Non.Existent")).thenReturn(null);
        Trainer result = trainerService.getTrainer("Non.Existent");
        assertNull(result);
        verify(trainerUserDao, times(1)).select("Non.Existent");
    }

    @Test
    void testGetAllTrainer() {
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(dummyTrainer);
        when(trainerUserDao.selectAll()).thenReturn(trainers);
        List<Trainer> result = trainerService.getAllTrainer();
        assertEquals(1, result.size());
        assertEquals("Alice.Johnson", result.get(0).getUsername());
        verify(trainerUserDao, times(1)).selectAll();
    }
}
