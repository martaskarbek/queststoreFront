package com.codecool.queststore.dao;

import com.codecool.queststore.models.Credentials;
import com.codecool.queststore.models.Role;
import com.codecool.queststore.models.users.User;
import com.codecool.queststore.models.users.UserFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserPostgreSQLDAO implements IUserDAO {
    private  PostgreSQLJDBC postgreSQLJDBC;

    public UserPostgreSQLDAO(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    @Override
    public void add(User user) {

    }

    @Override
    public void edit(User user, String[] params) {

    }

    @Override
    public void remove(User user) {

    }

    @Override
    public List<User> getAll() {
        postgreSQLJDBC.connect();
        return null;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public User getByCredentials(Credentials credentials) {
        postgreSQLJDBC.connect();
        User user = UserFactory.USER_NOT_FOUND;
        try {
            PreparedStatement preparedStatement = postgreSQLJDBC.connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?;");
            preparedStatement.setString(1, credentials.getEmail());
            preparedStatement.setString(2, credentials.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Role role = Role.valueOf(resultSet.getInt("role_id"));
                boolean isActive = resultSet.getBoolean("isactive");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                user = UserFactory.create(id, firstName, lastName, role, isActive, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
