package com.example.Dao.DaoImpl;

import com.example.Dao.InMemoryStorage;
import com.example.Dao.UserDao;
import com.example.model.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TraineeDaoImpl implements UserDao<Trainee> {
    private static final Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);

    @Autowired
    private InMemoryStorage storage;

    @Override
    public void create(Trainee user) {
        logger.debug("Creating trainee: {}", user.getUsername());
        Map<String, Object> trainees = storage.getNamespace("trainee");
        trainees.put(user.getUsername(), user);
        logger.info("Trainee created: {}", user.getUsername());
    }

    @Override
    public void update(Trainee user) {
        logger.debug("Updating trainee: {}", user.getUsername());
        Map<String, Object> trainees = storage.getNamespace("trainee");
        trainees.put(user.getUsername(), user);
        logger.info("Trainee updated: {}", user.getUsername());
    }

    @Override
    public void delete(String username) {
        logger.debug("Deleting trainee: {}", username);
        Map<String, Object> trainees = storage.getNamespace("trainee");
        trainees.remove(username);
        logger.info("Trainee deleted: {}", username);
    }

    @Override
    public Trainee select(String username) {
        logger.debug("Selecting trainee: {}", username);
        Map<String, Object> trainees = storage.getNamespace("trainee");
        Trainee trainee = (Trainee) trainees.get(username);
        logger.debug("Selected trainee: {}", trainee);
        return trainee;
    }

    @Override
    public List<Trainee> selectAll() {
        logger.debug("Selecting all trainees");
        Map<String, Object> trainees = storage.getNamespace("trainee");
        List<Trainee> traineeList = trainees.values().stream().map(t -> (Trainee) t).collect(Collectors.toList());
        logger.debug("Selected all trainees: {}", traineeList);
        return traineeList;
    }
}
