package com.codecool.queststore.handlers;

import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.services.ServiceFactory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.List;

public class LogoutHandler implements HttpHandler {

    private ServiceFactory serviceFactory;
    private Helpers helpers;

    public LogoutHandler(ServiceFactory serviceFactory, Helpers helpers) {
        this.serviceFactory = serviceFactory;
        this.helpers = helpers;
    }

    public void handle(HttpExchange exchange) throws IOException {
        String cookieStr = exchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = helpers.getCookieHelper().parseCookies(cookieStr);
        String sessionId = helpers.getCookieHelper().findSessionFromCookie(cookies);
        serviceFactory.getUserService().logout(sessionId);
        HttpCookie cookie = new HttpCookie(HttpHelper.SESSION_COOKIE_NAME, "");
        cookie.setMaxAge(-1);
        System.out.println(cookie.toString());
        exchange.getResponseHeaders().add("Cache-Control", "no-store");
        exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
        exchange.getResponseHeaders().add("Location", "/login");
        sendResponse(exchange, 301, "");
    }

    private void sendResponse(HttpExchange exchange, int rCode, String response) throws IOException {
        exchange.sendResponseHeaders(rCode, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
