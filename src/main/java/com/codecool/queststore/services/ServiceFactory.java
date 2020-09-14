package com.codecool.queststore.services;

import com.codecool.queststore.dao.*;

public class ServiceFactory {
    private final Connector connector;

    public ServiceFactory(Connector connector) {
        this.connector = connector;
    }

    public Connector getConnector() {
        return connector;
    }

    public UserService getUserService() {
        return new UserService(
                new UserPostgreSQLDAO(connector),
                new SessionPostgreSQLDAO(connector)
        );
    }

    public MentorService getMentorService() {
        return new MentorService(
                new MentorDAO(connector),
                new RewardDAO(connector),
                new ModuleDAO(connector)
        );
    }

    public RewardService getRewardService() {
        return new RewardService(
                new RewardDAO(connector)
        );
    }

    public QuestService getQuestService() {
        return new QuestService(
                new QuestDAO(connector)
        );
    }

    public StudentService getStudentService() {
        return new StudentService(
                new StudentDAO(connector),
                new UserPostgreSQLDAO(connector),
                new RewardDAO(connector),
                new QuestDAO(connector)
        );
    }
}
