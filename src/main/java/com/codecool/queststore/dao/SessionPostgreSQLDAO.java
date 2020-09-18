package com.codecool.queststore.dao;

import com.codecool.queststore.models.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SessionPostgreSQLDAO implements ISessionDAO {
    private Connector connector;
    public SessionPostgreSQLDAO(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void add(Session session) {
        connector.connect();
        try {
            PreparedStatement preparedStatement = connector.connection.prepareStatement("INSERT INTO sessions (uuid, user_id) VALUES (?, ?)");
            preparedStatement.setString(1, session.getUuid());
            preparedStatement.setInt(2, session.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void edit(Session session) {
    }

    @Override
    public void remove(Session session) {
        connector.connect();
        try {
            PreparedStatement preparedStatement = connector.connection.prepareStatement("DELETE FROM sessions WHERE uuid = ?;");
            preparedStatement.setString(1, session.getUuid());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public Session getBySessionId(String sessionId) {
        connector.connect();
        Session session = new Session();
        try {
            PreparedStatement preparedStatement = connector.connection.prepareStatement("SELECT * FROM sessions WHERE uuid = ?;");
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


