package com.codecool.queststore.dao;

import com.codecool.queststore.models.Category;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.Session;
import com.codecool.queststore.models.users.Mentor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public Mentor create(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        int price = rs.getInt("price");
        int categoryId = rs.getInt("category_id");
        int mentorId = rs.getInt("mentor_id");

        return null;
    }

    @Override
    public Mentor get(int id) {
        postgreSQLJDBC.connect();
        Mentor mentor = new Mentor();
        try {
            PreparedStatement preparedStatement = postgreSQLJDBC.connection.prepareStatement("SELECT * FROM users WHERE id = ?;");
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
}



