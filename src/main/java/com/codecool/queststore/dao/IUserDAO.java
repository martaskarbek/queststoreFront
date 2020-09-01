package com.codecool.queststore.dao;

import com.codecool.queststore.models.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO extends IDao<User>{

    List<User> getAll(String param) throws SQLException;

}
