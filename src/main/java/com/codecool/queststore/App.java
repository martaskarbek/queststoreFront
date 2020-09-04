package com.codecool.queststore;

import com.codecool.queststore.dao.LogoutHandler;
import com.codecool.queststore.handlers.LoginHandler;
import com.codecool.queststore.handlers.RewardHandler;
import com.codecool.queststore.handlers.Static;
import com.codecool.queststore.handlers.StudentHandler;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/login", new LoginHandler());
        server.createContext("/logout", new LogoutHandler());
        server.createContext("/mentor", new MentorHandler());
        server.createContext("/rewards", new RewardHandler());
        /*
            /student/quests
            /student/artifact
         */
        server.createContext("/student", new StudentHandler());

        /*
            /quest/{quest_id}/submit/{user_id}
         */
        server.createContext("/static", new Static());
        server.setExecutor(null);
        server.start();
    }
}