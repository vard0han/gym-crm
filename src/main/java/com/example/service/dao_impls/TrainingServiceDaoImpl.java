package com.example.service.dao_impls;

import com.example.Dao.repository.TrainingRepository;
import com.example.model.Training;
import com.example.service.TrainingService;
import com.example.service.TrainingServiceInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrainingServiceDaoImpl implements TrainingService {
    @Autowired
    private TrainingRepository trainingRepository;
    @Override
    @Transactional
    public void addTraining(Training training) {
        if(trainingRepository.findBytrainingName(training.getTrainingName()).isPresent()){
            throw new IllegalArgumentException("Name is already used");
        }
        trainingRepository.save(training);
    }

    @Override
    public Training getTraining(String trainingName) {
        return trainingRepository.findBytrainingName(trainingName).get();
    }

    @Override
    public List<Training> getAllTraining() {
        return trainingRepository.findAll();
    }
}
