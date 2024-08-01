package com.example.Dao.repository;

import com.example.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee,Long> {
    Optional<Trainee> findByUser_Username(String username);
}
