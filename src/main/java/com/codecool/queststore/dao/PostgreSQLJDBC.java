package com.codecool.queststore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLJDBC {

//    private final String URL = System.getenv("URL");
//    private final String USER = System.getenv("USER");
//    private final String PASSWORD = System.getenv("PASSWORD");

    private String user = "xmrjilbigxplni";
    private String password = "1a08ce40551a4345ec91ed018f0228060232574e369c314df5385f3377125549";
    private static final String CONNECTION_STRING = "jdbc:postgresql://ec2-46-137-79-235.eu-west-1.compute.amazonaws.com:5432/d8c6uv529kaagk";

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
