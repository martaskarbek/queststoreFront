package com.codecool.queststore;

import com.codecool.queststore.dao.IUserDAO;
import com.codecool.queststore.dao.PostgreSQLJDBC;
import com.codecool.queststore.dao.UserPostgreSQLDAO;

public class App {
    public static void main(String[] args) {
        IUserDAO dao = new UserPostgreSQLDAO(new PostgreSQLJDBC());
        dao.getAll();
    }
}
