package com.codecool.queststore.dao;

import com.codecool.queststore.models.Quest;

import java.util.List;

public class QuestDAO implements IQuestDAO {

    private final PostgreSQLJDBC postgreSQLJDBC;

    public QuestDAO(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }


    @Override
    public void add(Quest quest) {

    }

    @Override
    public void edit(Quest quest, String[] params) {

    }

    @Override
    public void remove(Quest quest) {

    }

    @Override
    public List<Quest> getAll() throws Exception {
        return null;
    }

    @Override
    public Quest get(int id) {
        return null;
    }
}