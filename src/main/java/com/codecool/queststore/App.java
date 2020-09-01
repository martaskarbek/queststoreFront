package com.codecool.queststore;

import com.codecool.queststore.dao.PostgreSQLJDBC;
import com.codecool.queststore.dao.RewardDAO;
import com.codecool.queststore.handlers.LoginHandler;
import com.codecool.queststore.handlers.RewardHandler;
import com.codecool.queststore.handlers.Static;
import com.codecool.queststore.models.Reward;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.List;

public class App {

    public static void main(String[] args) throws Exception {


        // create a server on port 8001
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);

        // set routes
        server.createContext("/login", new LoginHandler());
        server.createContext("/rewards", new RewardHandler());
        server.createContext("/static", new Static());
        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }}