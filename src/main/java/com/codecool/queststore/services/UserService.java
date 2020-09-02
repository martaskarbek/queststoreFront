package com.codecool.queststore.services;

import com.codecool.queststore.dao.SessionPostgreSQLDAO;
import com.codecool.queststore.dao.UserPostgreSQLDAO;
import com.codecool.queststore.models.Credentials;
import com.codecool.queststore.models.Session;
import com.codecool.queststore.models.users.User;

import java.util.UUID;

public class UserService {

    private UserPostgreSQLDAO userDAO;
    private SessionPostgreSQLDAO sessionDAO;

    public UserService(UserPostgreSQLDAO userDAO, SessionPostgreSQLDAO sessionDAO) {
        this.userDAO = userDAO;
        this.sessionDAO = sessionDAO;
    }

    public User login(Credentials credentials) {
        User user = this.userDAO.getByCredentials(credentials);

        if (user.getId() != 0) {
            UUID uuid = UUID.randomUUID();
            Session session = new Session(uuid.toString(), user.getId());
            user.setSession(session);
            sessionDAO.add(session);
        }
        return user;
    }
}
