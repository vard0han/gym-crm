package com.example.Dao;

import com.example.model.User;

import java.util.List;

public interface UserDao<T extends User> {
    void create(T user);
    void update(T user);
    void delete(String username);
    T select(String username);
    List<T> selectAll();
}
