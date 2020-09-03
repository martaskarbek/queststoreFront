package com.codecool.queststore.dao;

import com.codecool.queststore.models.users.Mentor;

import java.util.List;

public class MentorDAO implements IMentorDAO{

    private  PostgreSQLJDBC postgreSQLJDBC;

    public MentorDAO(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    @Override
    public void add(Mentor mentor) {

    }

    @Override
    public void edit(Mentor mentor, String[] params) {

    }

    @Override
    public void remove(Mentor mentor) {

    }

    @Override
    public List<Mentor> getAll() throws Exception {
        return null;
    }

    @Override
    public Mentor get(int id) {
        return null;
    }
}
