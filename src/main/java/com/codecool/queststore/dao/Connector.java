package com.codecool.queststore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    java.sql.Connection connection;

    private String user = "wt80"; //TODO please provide your user password and connection string
    private String password = "dupa123";
    private static final String CONNECTION_STRING = "jdbc:postgresql://localhost:5432/adrianWebinar";


    public Connector() throws ClassNotFoundException {
        this.connection = Connect();
    }

    public Connection Connect() throws ClassNotFoundException {
        connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(CONNECTION_STRING, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
