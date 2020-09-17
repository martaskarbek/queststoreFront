package com.codecool.queststore;

import com.codecool.queststore.dao.Connector;
import com.codecool.queststore.dao.LogoutHandler;
import com.codecool.queststore.handlers.*;
import com.codecool.queststore.helpers.CookieHelper;
import com.codecool.queststore.helpers.HandlerHelper;
import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.services.ServiceFactory;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws Exception {

        HttpHelper httpHelper = new HttpHelper();
        CookieHelper cookieHelper = new CookieHelper();
        HandlerHelper handlerHelper = new HandlerHelper();
        Helpers helpers = new Helpers(httpHelper, cookieHelper, handlerHelper);

        Connector connector = new Connector();
        ServiceFactory serviceFactory = new ServiceFactory(connector);

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/login", new LoginHandler(serviceFactory, helpers));
        server.createContext("/logout", new LogoutHandler());
        server.createContext("/mentor", new MentorHandler(serviceFactory, helpers));
        server.createContext("/rewards", new RewardHandler(serviceFactory, helpers));
        /*
            /student/quests
            /student/artifact
         */
        server.createContext("/student", new StudentHandler(serviceFactory, helpers));

        /*
            /quest/{quest_id}/submit/{user_id}
         */
        server.createContext("/static", new Static());
        server.setExecutor(null);
        server.start();
    }
}