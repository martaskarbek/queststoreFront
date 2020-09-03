package com.codecool.queststore.dao;

import com.codecool.queststore.models.Category;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.Role;
import com.codecool.queststore.models.Session;
import com.codecool.queststore.models.users.User;
import com.codecool.queststore.models.users.UserFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionPostgreSQLDAO implements ISessionDAO {
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


    @Override
    public Session getSessionBySessionId(String sessionId) throws Exception {
        postgreSQLJDBC.connect();
        Session session = new Session();
        try {
            PreparedStatement preparedStatement = postgreSQLJDBC.connection.prepareStatement("SELECT * FROM sessions WHERE uuid = ?;");
            preparedStatement.setString(1, sessionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String uuid = resultSet.getString("uuid");
                session =  new Session(uuid, userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return session;
    }

}


