package com.codecool.queststore.dao;

import java.util.List;

public interface IDao<T> {

    void add(T t);
    void edit(T t, String[] params);
    void remove(T t);
    T get(int id);
}
