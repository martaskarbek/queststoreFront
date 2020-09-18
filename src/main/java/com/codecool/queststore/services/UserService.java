package com.codecool.queststore.services;

import com.codecool.queststore.dao.SessionPostgreSQLDAO;
import com.codecool.queststore.dao.UserPostgreSQLDAO;
import com.codecool.queststore.models.Password;
import com.codecool.queststore.models.Session;
import com.codecool.queststore.models.users.Mentor;
import com.codecool.queststore.models.users.User;

import java.util.UUID;

public class UserService {

    private UserPostgreSQLDAO userDAO;
    private SessionPostgreSQLDAO sessionDAO;

    public UserService(UserPostgreSQLDAO userDAO, SessionPostgreSQLDAO sessionDAO) {
        this.userDAO = userDAO;
        this.sessionDAO = sessionDAO;
    }

    public User login(String email, String password) {
        User user = this.userDAO.getByCredentials(email);
        // if user does not exists by email
        if (user.getId() == 0) {
            return user;
        }

        // validate password
        if (!user.getSalt().equals("salt") && !Password.checkPasswords(password, user)) {
            return user;
        }

        if (user.getId() != 0) {
            UUID uuid = UUID.randomUUID();
            Session session = new Session(uuid.toString(), user.getId());
            user.setSession(session);
            sessionDAO.add(session);
        }
        return user;
    }

    public User getBySessionId(String sessionId) {
        Session session = sessionDAO.getSessionBySessionId(sessionId);
        return userDAO.get(session.getUserId());
    }

    public void logout(String sessionId) {
        Session session = sessionDAO.getSessionBySessionId(sessionId);
        sessionDAO.remove(session);
    }

    public void addUser(User user){
        userDAO.add(user);
    }

    public User getByCredentials(String email){
        return userDAO.getByCredentials(email);
    }

    public void updateUserStudent(User userStudent) {
        userDAO.edit(userStudent);
    }
}

