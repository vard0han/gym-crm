package com.example.Dao.DaoImpl;

import com.example.Dao.InMemoryStorage;
import com.example.Dao.UserDao;
import com.example.model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TraineeDaoImpl implements UserDao<Trainee> {
    @Autowired
    private InMemoryStorage storage;
    @Override
    public void create(Trainee user) {
        Map<String, Object> trainees =  storage.getNamespace("trainee");
        trainees.put(user.getUsername(),user);
    }

    @Override
    public void update(Trainee user) {
        Map<String, Object> trainees =  storage.getNamespace("trainee");
        trainees.put(user.getUsername(),user);
    }

    @Override
    public void delete(String username) {
        Map<String, Object> trainees =  storage.getNamespace("trainee");
        trainees.remove(username);
    }

    @Override
    public Trainee select(String username) {
        Map<String, Object> trainees =  storage.getNamespace("trainee");
        return (Trainee) trainees.get(username);
    }

    @Override
    public List<Trainee> selectAll() {
        Map<String, Object> trainees =  storage.getNamespace("trainee");
        return trainees.values().stream().map(t -> (Trainee) t).collect(Collectors.toList());
    }
}
