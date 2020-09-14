package com.codecool.queststore.helpers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HttpHelper {

    public static final String SESSION_COOKIE_NAME = "SessionID";
    public static final int CREATED = 201;
    public static final int OK = 200;
    public static final int NOT_FOUND = 404;
    public static final int TEAPOT = 418;
    public static final int MOVED_PERMANENTLY = 301;

    public void sendResponse(HttpExchange httpExchange, String response, int status) throws IOException {
        httpExchange.sendResponseHeaders(status, response.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}
