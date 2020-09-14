package com.codecool.queststore.services;

import com.codecool.queststore.dao.SessionPostgreSQLDAO;
import com.codecool.queststore.dao.UserPostgreSQLDAO;
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
        User user = this.userDAO.getByCredentials(email, password);

        if (user.getId() != 0) {
            UUID uuid = UUID.randomUUID();
            Session session = new Session(uuid.toString(), user.getId());
            user.setSession(session);
            sessionDAO.add(session);
        }
        return user;
    }

    public User getUserBySessionId(String sessionId) {
        Session session = sessionDAO.getSessionBySessionId(sessionId);
        System.out.println(session);
        return userDAO.get(session.getUserId());

    }

    public void logout(String sessionId) {
        Session session = sessionDAO.getSessionBySessionId(sessionId);
        sessionDAO.remove(session);
    }

    public void addUserToDB(User user){
        userDAO.add(user);
    }

    public User getUserByCredentials(String email, String password){
        return userDAO.getByCredentials(email, password);
    }

    public void updateUserStudent(User userStudent) {
        userDAO.edit(userStudent);
    }
}

