package com.codecool.queststore.dao;

import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.services.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

public class LogoutHandler implements HttpHandler {
    private Connector connector = new Connector();
    private UserService userService = new UserService(new UserPostgreSQLDAO(connector), new SessionPostgreSQLDAO(connector));

    public void handle(HttpExchange exchange) throws IOException {
        String cookieStr = exchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = parseCookies(cookieStr);
        String sessionId = findSessionFromCookie(cookies);
        userService.logout(sessionId);
        HttpCookie cookie = new HttpCookie(HttpHelper.SESSION_COOKIE_NAME, "");
        cookie.setMaxAge(-1);
        exchange.getResponseHeaders().add("Location", "/login");
        exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
        sendResponse(exchange, 301, "");
    }

    private List<HttpCookie> parseCookies(String cookieStr) {
        List<HttpCookie> cookies = new ArrayList<>();
        if(cookieStr == null || cookieStr.isEmpty()){
            return cookies;
        }
        for(String cookie : cookieStr.split(";")){
            int indexOfEq = cookie.indexOf('=');
            String cookieName = cookie.substring(0, indexOfEq);
            String cookieValue = cookie.substring(indexOfEq + 1);
            cookies.add(new HttpCookie(cookieName, cookieValue));
        }
        return cookies;
    }

    private String findSessionFromCookie(List<HttpCookie> cookies) {
        for (HttpCookie cookie : cookies) {
            if (cookie.getName().equals(HttpHelper.SESSION_COOKIE_NAME)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void sendResponse(HttpExchange exchange, int rCode, String response) throws IOException {
        exchange.sendResponseHeaders(rCode, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
