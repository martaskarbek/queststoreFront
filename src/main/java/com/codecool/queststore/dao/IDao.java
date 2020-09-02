package com.codecool.queststore.dao;

import com.codecool.queststore.models.User;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {

    void add(T t);
    void edit(T t, String[] params);
    void remove(T t);
    T get(int id);
    List<T> getAll(String param) throws SQLException;
}
