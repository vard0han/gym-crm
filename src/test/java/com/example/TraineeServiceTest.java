package com.example;

import com.example.Dao.UserDao;
import com.example.model.InMemory.Trainee;
import com.example.service.impl.TraineeServiceImpl;
import com.example.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TraineeServiceTest {

    @Mock
    private UserDao<Trainee> traineeUserDao;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateTrainee() {
        Trainee trainee = TestUtil.createDummyTrainee();
        traineeService.createTrainee(trainee);
        verify(traineeUserDao, times(1)).create(trainee);
    }

    @Test
    void testUpdateTrainee() {
        Trainee trainee = TestUtil.createDummyTrainee();
        doNothing().when(traineeUserDao).update(trainee);

        traineeService.updateTrainee(trainee);

        verify(traineeUserDao, times(1)).update(trainee);
    }

    @Test
    void testDeleteTrainee() {
        String username = "John.Doe";
        doNothing().when(traineeUserDao).delete(username);

        traineeService.deleteTrainee(username);

        verify(traineeUserDao, times(1)).delete(username);
    }

    @Test
    void testGetTrainee() {
        String username = "John.Doe";
        Trainee expectedTrainee = TestUtil.createDummyTrainee();
        when(traineeUserDao.select(username)).thenReturn(expectedTrainee);

        Trainee actualTrainee = traineeService.getTrainee(username);

        verify(traineeUserDao, times(1)).select(username);
        assertEquals(expectedTrainee, actualTrainee);
    }

    @Test
    void testGetAllTrainee() {
        List<Trainee> expectedTrainees = Arrays.asList(
                TestUtil.createDummyTrainee(),
                TestUtil.createDummyTrainee()
        );
        when(traineeUserDao.selectAll()).thenReturn(expectedTrainees);

        List<Trainee> actualTrainees = traineeService.getAllTrainee();

        verify(traineeUserDao, times(1)).selectAll();
        assertEquals(expectedTrainees.size(), actualTrainees.size());
        assertEquals(expectedTrainees, actualTrainees);
    }
}
