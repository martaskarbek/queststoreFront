package com.codecool.queststore.dao;

import com.codecool.queststore.models.Role;
import com.codecool.queststore.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends Dao<User> implements IUserDAO {

    @Override
    public void add(User user) {
        String query = String.format(
                "INSERT INTO users (first_name, last_name, role_id, isActive, email, password) values ('%s', '%s', %d, %b, '%s', '%s');",
                user.getFirstName(),
                user.getLastName(),
                user.getRole().getRoleId(),
                user.isActive(),
                user.getEmail(),
                user.getPassword()
        );
        executeQuery(query);
    }

    @Override
    public void edit(User user, String[] params) {

    }

    @Override
    public void remove(User user) {

    }


    @Override
    public List<User> getAll(String condition) throws SQLException {
        String query = String.format("SELECT * FROM users WHERE %s;", condition);
        List<User> users = new ArrayList<>();
        connect();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = createUser(resultSet);
                users.add(user);
            }
            resultSet.close();
            statement.close();
            disconnect();

            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new SQLException("Data not found");

    }

    private User createUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        Role role = Role.valueOf(resultSet.getInt("role_id"));
        boolean isActive = resultSet.getBoolean("isActive");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");

        User user = new User();

        return user.setUser(id, firstName, lastName, role, isActive, email, password);
    }


    @Override
    public User get(int id) {
        return null;
    }
}
