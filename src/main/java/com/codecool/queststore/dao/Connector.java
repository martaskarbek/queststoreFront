package com.codecool.queststore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {

//    private final String URL = System.getenv("URL");
//    private final String USER = System.getenv("USER");
//    private final String PASSWORD = System.getenv("PASSWORD");

    // TODO
    //  hide database login details

    private String user = "wt80";
    private String password = "dupa123";
    private static final String CONNECTION_STRING = "jdbc:postgresql://localhost:5432/QUESTstore6";

//    private String user = "ysbidcpezviicd";
//    private String password = "53e426929003e252f789e33bfdba58b73e4a4f0f24f49d1d5248d7061f3cb729";
//    private String CONNECTION_STRING = "jdbc:postgresql://ec2-34-254-24-116.eu-west-1.compute.amazonaws.com" +
//            ":5432/de0sdte5vtesls";

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
