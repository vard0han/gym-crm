package com.example.Dao.DaoImpl;

import com.example.Dao.InMemoryStorage;
import com.example.Dao.UserDao;
import com.example.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TrainerDaoImpl implements UserDao<Trainer> {
    private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);

    @Autowired
    private InMemoryStorage storage;

    @Override
    public void create(Trainer user) {
        logger.debug("Creating trainer: {}", user.getUsername());
        Map<String, Object> trainers = storage.getNamespace("trainer");
        trainers.put(user.getUsername(), user);
        logger.info("Trainer created: {}", user.getUsername());
    }

    @Override
    public void update(Trainer user) {
        logger.debug("Updating trainer: {}", user.getUsername());
        Map<String, Object> trainers = storage.getNamespace("trainer");
        trainers.put(user.getUsername(), user);
        logger.info("Trainer updated: {}", user.getUsername());
    }

    @Override
    public void delete(String username) {
        logger.debug("Deleting trainer: {}", username);
        Map<String, Object> trainers = storage.getNamespace("trainer");
        trainers.remove(username);
        logger.info("Trainer deleted: {}", username);
    }

    @Override
    public Trainer select(String username) {
        logger.debug("Selecting trainer: {}", username);
        Map<String, Object> trainers = storage.getNamespace("trainer");
        Trainer trainer = (Trainer) trainers.get(username);
        logger.debug("Selected trainer: {}", trainer);
        return trainer;
    }

    @Override
    public List<Trainer> selectAll() {
        logger.debug("Selecting all trainers");
        Map<String, Object> trainers = storage.getNamespace("trainer");
        List<Trainer> trainerList = trainers.values().stream().map(t -> (Trainer) t).collect(Collectors.toList());
        logger.debug("Selected all trainers: {}", trainerList);
        return trainerList;
    }
}
