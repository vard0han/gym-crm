package com.example;

import com.example.Dao.DaoImpl.TraineeDaoImpl;
import com.example.Dao.InMemoryStorage;
import com.example.model.Trainee;
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

class TraineeDaoImplTest {

    @Mock
    private InMemoryStorage storage;

    @InjectMocks
    private TraineeDaoImpl traineeDao;

    private Trainee trainee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainee = TestUtil.createDummyTrainee();
        when(storage.getNamespace("trainee")).thenReturn(new java.util.HashMap<>());
    }

    @Test
    void create() {
        traineeDao.create(trainee);
        Map<String, Object> trainees = storage.getNamespace("trainee");
        assertEquals(trainee, trainees.get(trainee.getUsername()));
    }

    @Test
    void update() {
        traineeDao.create(trainee);
        trainee.setAddress("456 New Address");
        traineeDao.update(trainee);
        Map<String, Object> trainees = storage.getNamespace("trainee");
        assertEquals("456 New Address", ((Trainee) trainees.get(trainee.getUsername())).getAddress());
    }

    @Test
    void delete() {
        traineeDao.create(trainee);
        traineeDao.delete(trainee.getUsername());
        Map<String, Object> trainees = storage.getNamespace("trainee");
        assertNull(trainees.get(trainee.getUsername()));
    }

    @Test
    void select() {
        traineeDao.create(trainee);
        Trainee selectedTrainee = traineeDao.select(trainee.getUsername());
        assertEquals(trainee, selectedTrainee);
    }

    @Test
    void selectAll() {
        traineeDao.create(trainee);
        List<Trainee> trainees = traineeDao.selectAll();
        assertEquals(1, trainees.size());
        assertEquals(trainee, trainees.get(0));
    }
}
