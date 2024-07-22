package com.example.Dao;

import com.example.model.Training;

import java.util.List;

public interface TrainingDao {
    void create(Training training);
    Training select(String trainingName);
    List<Training> selectAll();
}
