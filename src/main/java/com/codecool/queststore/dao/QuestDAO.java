package com.codecool.queststore.dao;

import com.codecool.queststore.models.Category;
import com.codecool.queststore.models.Quest;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class QuestDAO implements IQuestDAO {

    private final PostgreSQLJDBC postgreSQLJDBC;

    public QuestDAO(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }


    @Override
    public void add(Quest quest) {
        postgreSQLJDBC.connect();
        try {
            PreparedStatement preparedStatement = postgreSQLJDBC.connection.prepareStatement("INSERT INTO quests" +
                    "(name, description, coins_to_earn, module_id, mentor_id, category_id, isactive) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, quest.getName());
            preparedStatement.setString(2, quest.getDescription());
            preparedStatement.setInt(3, quest.getCoinsToEarn());
            preparedStatement.setInt(4, quest.getModuleId());
            preparedStatement.setInt(5, quest.getMentorId());
            preparedStatement.setInt(6, quest.getCategoryId());
            preparedStatement.setBoolean(7, quest.getActive());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void edit(Quest quest) {

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