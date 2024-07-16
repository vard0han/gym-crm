package com.example.Dao.DaoImpl;

import com.example.Dao.InMemoryStorage;
import com.example.Dao.TrainingDao;
import com.example.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    @Autowired
    private InMemoryStorage storage;
    @Override
    public void create(Training training) {
        Map<String, Object> trainings =  storage.getNamespace("training");
        trainings.put(training.getTrainingName(),trainings);
    }

    @Override
    public Training select(String trainingName) {
        Map<String, Object> trainings =  storage.getNamespace("training");
        return (Training) trainings.get(trainingName);
    }

    @Override
    public List<Training> selectAll() {
        Map<String, Object> trainings =  storage.getNamespace("training");
        return trainings.values().stream().map(t-> (Training) t).collect(Collectors.toList());
    }
}
