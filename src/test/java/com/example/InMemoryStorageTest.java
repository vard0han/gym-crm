package com.example;

import com.example.Dao.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InMemoryStorageTest {

    @InjectMocks
    private InMemoryStorage storage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNamespace() {
        Map<String, Object> traineeNamespace = storage.getNamespace("trainee");
        assertNotNull(traineeNamespace);
        assertTrue(traineeNamespace.isEmpty());
    }

    @Test
    void testInitialization() {
        // Simulate the init method to check if storage is populated correctly
        storage.init();

        Map<String, Object> traineeNamespace = storage.getNamespace("trainee");
        Map<String, Object> trainerNamespace = storage.getNamespace("trainer");
        Map<String, Object> trainingNamespace = storage.getNamespace("training");

        assertNotNull(traineeNamespace);
        assertNotNull(trainerNamespace);
        assertNotNull(trainingNamespace);
        assertTrue(traineeNamespace.size() > 0);
        assertTrue(trainerNamespace.size() > 0);
        assertTrue(trainingNamespace.size() > 0);
    }

    @Test
    void testGetNamespaceNonExistent() {
        Map<String, Object> nonExistentNamespace = storage.getNamespace("nonexistent");
        assertNull(nonExistentNamespace);
    }
}
