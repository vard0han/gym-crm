package com.example.Dao.repository;

import com.example.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer,Long> {
    Optional<Trainer> findByUser_Username(String username);
}
