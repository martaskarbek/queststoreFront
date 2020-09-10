package com.codecool.queststore.dao;

import com.codecool.queststore.models.Category;
import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.Reward;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            preparedStatement.setInt(6, Category.getCategoryValue(quest.getCategory()));
            preparedStatement.setBoolean(7, quest.getActive());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void edit(Quest quest) {
        postgreSQLJDBC.connect();
        try {
            PreparedStatement preparedStatement = postgreSQLJDBC.connection.prepareStatement("UPDATE quests SET name=?, description=?, coins_to_earn=?, category_id=?, mentor_id=?, module_id=?, isactive=? WHERE id=?");
            preparedStatement.setString(1, quest.getName());
            preparedStatement.setString(2, quest.getDescription());
            preparedStatement.setInt(3, quest.getCoinsToEarn());
            preparedStatement.setInt(4, Category.getCategoryValue(quest.getCategory()));
            preparedStatement.setInt(5, quest.getMentorId());
            preparedStatement.setInt(6, quest.getModuleId());
            preparedStatement.setBoolean(7, quest.getActive());
            preparedStatement.setInt(8, quest.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remove(Quest quest) {

    }

    public Quest create(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        int coinsToEarn = rs.getInt("coins_to_earn");
        int categoryId = rs.getInt("category_id");
        int mentorId = rs.getInt("mentor_id");
        int moduleId = rs.getInt("module_id");
        Boolean isActive = rs.getBoolean("isactive");

        Quest quest = new Quest(id, name, description, coinsToEarn, moduleId, mentorId, Category.valueOf(categoryId), isActive);

        try {
            rs.findColumn("author");
            String author = rs.getString("author");
            quest.setAuthor(author);
            return quest;
        } catch (SQLException sqlex){
            return quest;
        }
    }

    @Override
    public List<Quest> getAll() throws Exception {
        List<Quest> quests = new ArrayList<>();

        try {
            postgreSQLJDBC.connect();
            ResultSet rs = postgreSQLJDBC.statement.executeQuery("select quests.id, quests.name, quests.description, quests.coins_to_earn, quests.module_id, quests.mentor_id, quests.category_id, quests.isactive, CONCAT(users.first_name, ' ', users.last_name) as author\n" +
                    "from quests\n" +
                    "inner join mentors on quests.mentor_id=mentors.id\n" +
                    "inner join users on mentors.user_id=users.id\n" +
                    "group by quests.id, author;");
            while (rs.next()) {
                Quest quest = create(rs);
                quests.add(quest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quests;
    }

    @Override
    public Quest get(int id) {
        postgreSQLJDBC.connect();
        Quest quest = new Quest();
        try {
            PreparedStatement preparedStatement = postgreSQLJDBC.connection.prepareStatement("SELECT * FROM quests WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                quest = create(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quest;
    }
}