package com.codecool.queststore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    java.sql.Connection connection;

    private String user = "xmrjilbigxplni";
    private String password = "1a08ce40551a4345ec91ed018f0228060232574e369c314df5385f3377125549";
    private static final String CONNECTION_STRING = "jdbc:postgresql://ec2-46-137-79-235.eu-west-1.compute.amazonaws.com:5432/d8c6uv529kaagk";


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
