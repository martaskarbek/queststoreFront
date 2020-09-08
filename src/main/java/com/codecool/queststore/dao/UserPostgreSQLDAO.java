package com.codecool.queststore.dao;

import com.codecool.queststore.models.Category;
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
        postgreSQLJDBC.connect();
        try {
            PreparedStatement preparedStatement = postgreSQLJDBC.connection.prepareStatement("INSERT INTO users" +
                    "(first_name, last_name, role_id, isactive, email, password) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setInt(3, Role.getRoleValue(user.getRole()));
            preparedStatement.setBoolean(4, user.isActive());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
    public User get(int userId) {
        postgreSQLJDBC.connect();
        User user = UserFactory.USER_NOT_FOUND;
        try {
            PreparedStatement preparedStatement = postgreSQLJDBC.connection.prepareStatement("SELECT * FROM users WHERE id = ?;");
            preparedStatement.setInt(1, userId);
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

    @Override
    public User getByCredentials(String email, String password) {
        postgreSQLJDBC.connect();
        User user = UserFactory.USER_NOT_FOUND;
        try {
            PreparedStatement preparedStatement = postgreSQLJDBC.connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?;");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Role role = Role.valueOf(resultSet.getInt("role_id"));
                boolean isActive = resultSet.getBoolean("isactive");
                user = UserFactory.create(id, firstName, lastName, role, isActive, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


}
