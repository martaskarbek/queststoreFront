package com.codecool.queststore.dao;

import java.util.List;

public interface IDAO<T> {

    void add(T t);
    void edit(T t, String[] params);
    void remove(T t);
    List<T> getAll();
    T get(int id);
}
