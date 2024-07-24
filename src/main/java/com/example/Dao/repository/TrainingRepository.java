package com.example.Dao.repository;

import com.example.model.Trainee;
import com.example.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepository extends JpaRepository<Training,Long> {
    List<Training> findByTrainee_UsernameAndTrainingDateBetweenAndTrainer_NameAndTrainingType_TrainingTypeName(
            String traineeUsername, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType);

    List<Training> findByTrainer_UsernameAndTrainingDateBetweenAndTrainee_Name(
            String trainerUsername, LocalDate fromDate, LocalDate toDate, String traineeName);

    Optional<Training> findByName(String name);

}
