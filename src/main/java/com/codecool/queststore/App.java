package com.codecool.queststore;

import com.codecool.queststore.handlers.LoginHandler;
import com.codecool.queststore.handlers.RewardHandler;
import com.codecool.queststore.handlers.Static;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/login", new LoginHandler());
        server.createContext("/rewards", new RewardHandler());
        server.createContext("/static", new Static());
        server.setExecutor(null);
        server.start();
    }
}