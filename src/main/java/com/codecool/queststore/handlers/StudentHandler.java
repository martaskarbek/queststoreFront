package com.codecool.queststore.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class StudentHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().getRawPath(); // /student/artifact
        //TODO remove first char from url string
        String[] actions = url.split("/");

        // https://github.com/adrianwii-codecool/web-basic-backend/blob/master/src/main/java/com/codecool/controller/UserController.java
        switch (actions[2]) {
            case "artifact":
                System.out.println("something");
                // if method === GET
                break;
            case "quests":
                break;
            default:
                System.out.println("default");
        }
    }
}
