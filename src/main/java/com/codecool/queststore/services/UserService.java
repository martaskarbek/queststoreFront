package com.codecool.queststore.services;

import com.codecool.queststore.dao.Dao;
import com.codecool.queststore.dao.UserDao;
import com.codecool.queststore.models.Role;
import com.codecool.queststore.models.User;


public class UserService {

    private final Dao<User> userDao = new UserDao();

    private void addUser(User user, int roleId) {
        userDao.add(user.setFirstName(user.getFirstName()).setLastName(user.getLastName())
                .setPassword(user.getPassword()).setEmail(user.getEmail())
                .setRole(Role.valueOf(roleId)).setActive(true));
    }

    public void addMentor(User mentor) {
        addUser(mentor, mentor.getRole().getRoleId());
    }


}
