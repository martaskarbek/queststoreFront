package com.codecool.queststore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLJDBC {

//    private final String URL = System.getenv("URL");
//    private final String USER = System.getenv("USER");
//    private final String PASSWORD = System.getenv("PASSWORD");

    private String user = "aldona";
    private String password = "123";
    private static final String CONNECTION_STRING = "jdbc:postgresql://localhost:5432/qs";

    protected Connection connection = null;
    protected Statement statement;

    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(CONNECTION_STRING, user, password);
            System.out.println("Connected to DB");
            statement = connection.createStatement();
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Not connected");
        }
    }
}
