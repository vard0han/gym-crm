package com.example;

import com.example.Dao.DaoImpl.TrainerDaoImpl;
import com.example.Dao.InMemoryStorage;
import com.example.model.InMemory.Trainer;
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

class TrainerDaoImplTest {

    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private TrainerDaoImpl trainerDao;

    private Trainer trainer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainer = TestUtil.createDummyTrainer();
        when(storage.getNamespace("trainer")).thenReturn(new java.util.HashMap<>());
    }

    @Test
    void create() {
        trainerDao.create(trainer);
        Map<String, Object> trainers = storage.getNamespace("trainer");
        assertEquals(trainer, trainers.get(trainer.getUsername()));
    }

    @Test
    void update() {
        trainerDao.create(trainer);
        trainer.setSpecialization("Advanced Fitness");
        trainerDao.update(trainer);
        Map<String, Object> trainers = storage.getNamespace("trainer");
        assertEquals("Advanced Fitness", ((Trainer) trainers.get(trainer.getUsername())).getSpecialization());
    }

    @Test
    void delete() {
        trainerDao.create(trainer);
        trainerDao.delete(trainer.getUsername());
        Map<String, Object> trainers = storage.getNamespace("trainer");
        assertNull(trainers.get(trainer.getUsername()));
    }

    @Test
    void select() {
        trainerDao.create(trainer);
        Trainer selectedTrainer = trainerDao.select(trainer.getUsername());
        assertEquals(trainer, selectedTrainer);
    }

    @Test
    void selectAll() {
        trainerDao.create(trainer);
        List<Trainer> trainers = trainerDao.selectAll();
        assertEquals(1, trainers.size());
        assertEquals(trainer, trainers.get(0));
    }
}
