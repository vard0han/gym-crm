package com.example.Dao;

import com.example.model.Trainee;
import com.example.model.Trainer;
import com.example.model.Training;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryStorage {
    private final Map<String, Map<String, Object>> storage = new HashMap<>();
    @Value("${data.file.path}")
    private String dataFilePath;

    public InMemoryStorage() {
        storage.put("trainee", new HashMap<>());
        storage.put("trainer", new HashMap<>());
        storage.put("training", new HashMap<>());
    }

    public Map<String, Object> getNamespace(String namespace) {
        return storage.get(namespace);
    }
    @PostConstruct
    public void init() {
        try {
            String data = new String(Files.readAllBytes(Paths.get(dataFilePath)));
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            Map<String, List<Map<String, Object>>> jsonData = mapper.readValue(data, new TypeReference<Map<String, List<Map<String, Object>>>>() {});

            // Parse and initialize trainees
            List<Trainee> trainees = mapper.convertValue(jsonData.get("trainees"), new TypeReference<List<Trainee>>() {});
            trainees.forEach(trainee -> storage.get("trainee").put(trainee.getUsername(), trainee));

            // Parse and initialize trainers
            List<Trainer> trainers = mapper.convertValue(jsonData.get("trainers"), new TypeReference<List<Trainer>>() {});
            trainers.forEach(trainer -> storage.get("trainer").put(trainer.getUsername(), trainer));

            // Parse and initialize trainings
            List<Training> trainings = mapper.convertValue(jsonData.get("trainings"), new TypeReference<List<Training>>() {});
            trainings.forEach(training -> storage.get("training").put(training.getTrainingName(), training));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
