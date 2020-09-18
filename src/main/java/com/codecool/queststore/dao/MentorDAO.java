package com.codecool.queststore.dao;

import com.codecool.queststore.models.users.Mentor;
import com.codecool.queststore.models.users.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MentorDAO implements IMentorDAO{

    private Connector connector;

    public MentorDAO(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void add(Mentor mentor) {
    }

    @Override
    public void edit(Mentor mentor) {
    }

    @Override
    public void remove(Mentor mentor) {
    }

    @Override
    public List<Mentor> getAll() throws Exception {
        return null;
    }

    public Mentor create(ResultSet rs) throws SQLException {
//        int id = rs.getInt("id");
//        String name = rs.getString("name");
//        String description = rs.getString("description");
//        int price = rs.getInt("price");
//        int categoryId = rs.getInt("category_id");
//        int mentorId = rs.getInt("mentor_id");
        return null;
    }

    @Override
    public Mentor get(int id) {
        connector.connect();
        Mentor mentor = new Mentor();
        try {
            PreparedStatement preparedStatement = connector.connection.prepareStatement("SELECT * FROM users WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mentor = create(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mentor;
    }

    @Override
    public int getMentorId(User user) {
        connector.connect();
        int mentorId = 0;
        try {
            PreparedStatement preparedStatement = connector.connection.prepareStatement("select mentors.id as mentor_id\n" +
                    "from mentors, users\n" +
                    "where users.id = mentors.user_id\n" +
                    "and\n" +
                    "users.id = ?;");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mentorId = resultSet.getInt("mentor_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mentorId;
    }
}



