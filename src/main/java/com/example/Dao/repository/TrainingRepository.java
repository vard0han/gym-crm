package com.example.Dao.repository;

import com.example.model.Trainee;
import com.example.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepository extends JpaRepository<Training,Long> {
    @Query("SELECT t FROM Training t WHERE t.trainee.user.username = :username AND " +
            "(:fromDate IS NULL OR t.trainingDate >= :fromDate) AND " +
            "(:toDate IS NULL OR t.trainingDate <= :toDate) AND " +
            "(:trainerName IS NULL OR t.trainer.user.username = :trainerName) AND " +
            "(:trainingTypeName IS NULL OR t.trainingType.trainingTypeName = :trainingTypeName)")
    List<Training> findTraineeTrainings(@Param("username") String username,
                                        @Param("fromDate") LocalDate fromDate,
                                        @Param("toDate") LocalDate toDate,
                                        @Param("trainerName") String trainerName,
                                        @Param("trainingTypeName") String trainingTypeName);

    @Query("SELECT t FROM Training t WHERE t.trainer.user.username = :username AND " +
            "(:fromDate IS NULL OR t.trainingDate >= :fromDate) AND " +
            "(:toDate IS NULL OR t.trainingDate <= :toDate) AND " +
            "(:traineeName IS NULL OR t.trainee.user.username = :traineeName)")
    List<Training> findTrainerTrainings(@Param("username") String username,
                                        @Param("fromDate") LocalDate fromDate,
                                        @Param("toDate") LocalDate toDate,
                                        @Param("traineeName") String traineeName);

    Optional<Training> findBytrainingName(String name);

}
