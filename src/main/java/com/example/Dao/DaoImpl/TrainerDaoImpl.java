package com.example.Dao.DaoImpl;

import com.example.Dao.InMemoryStorage;
import com.example.Dao.UserDao;
import com.example.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class TrainerDaoImpl implements UserDao<Trainer> {
    @Autowired
    private InMemoryStorage storage;
    @Override
    public void create(Trainer user) {
        Map<String, Object> trainers =  storage.getNamespace("trainer");
        trainers.put(user.getUsername(),user);
    }

    @Override
    public void update(Trainer user) {
        Map<String, Object> trainers =  storage.getNamespace("trainer");
        trainers.put(user.getUsername(),user);
    }

    @Override
    public void delete(String username) {
        Map<String, Object> trainers =  storage.getNamespace("trainer");
        trainers.remove(username);
    }

    @Override
    public Trainer select(String username) {
        Map<String, Object> trainers =  storage.getNamespace("trainer");
        return (Trainer) trainers.get(username);
    }

    @Override
    public List<Trainer> selectAll() {
        Map<String, Object> trainers =  storage.getNamespace("trainer");
        return trainers.values().stream().map(t -> (Trainer) t).collect(Collectors.toList());
    }
}
