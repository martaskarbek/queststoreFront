package com.codecool.queststore.dao;

import com.codecool.queststore.models.User;

import java.util.List;

public class UserPostgreSQLDAO implements IUserDAO {
    private  PostgreSQLJDBC postgreSQLJDBC;

    public UserPostgreSQLDAO(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    @Override
    public void add(User user) {

    }

    @Override
    public void edit(User user, String[] params) {

    }

    @Override
    public void remove(User user) {

    }

    @Override
    public List<User> getAll() {
        postgreSQLJDBC.connect();
        return null;
    }

    @Override
    public User get(int id) {
        return null;
    }
}
