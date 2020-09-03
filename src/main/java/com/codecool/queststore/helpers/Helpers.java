package com.codecool.queststore.helpers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class Helpers {

    public static final int CREATED = 201;
    public static final int OK = 200;

    public static final int TEAPOT = 418;

    public void sendResponse(HttpExchange httpExchange, String response, int status) throws IOException {
        httpExchange.sendResponseHeaders(status, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
