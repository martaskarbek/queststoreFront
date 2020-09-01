package com.codecool.queststore.dao;

import com.codecool.queststore.models.Session;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SessionPostgreSQLDAO implements IDAO<Session> {
    private  PostgreSQLJDBC postgreSQLJDBC;
    public SessionPostgreSQLDAO(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    @Override
    public void add(Session session) {
        postgreSQLJDBC.connect();
        try {
            PreparedStatement preparedStatement = postgreSQLJDBC.connection.prepareStatement("INSERT INTO sessions (uuid, user_id) VALUES (?, ?)");
            preparedStatement.setString(1, session.getUuid());
            preparedStatement.setInt(2, session.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void edit(Session session, String[] params) {

    }

    @Override
    public void remove(Session session) {

    }

    @Override
    public List<Session> getAll() throws Exception {
        return null;
    }

    @Override
    public Session get(int id) {
        return null;
    }
}
