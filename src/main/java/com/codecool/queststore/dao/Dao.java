package com.codecool.queststore.dao;

import java.sql.SQLException;

public abstract class Dao<T> extends PostgreSQLJDBC implements IDao<T> {

    public void executeQuery(String  query){
        connect();
        try {
            statement.execute(query);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
