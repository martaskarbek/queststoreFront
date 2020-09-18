package com.codecool.queststore.helpers;

import com.sun.net.httpserver.HttpExchange;

public class HandlerHelper {

    private HttpExchange httpExchange;
    private String response;

    public void init(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
        this.response = "";
    }
}

