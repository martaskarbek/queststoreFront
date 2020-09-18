package com.codecool.queststore.handlers;

import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.models.users.User;
import com.codecool.queststore.services.ServiceFactory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginHandler implements HttpHandler {
    private final ServiceFactory serviceFactory;
    private final Helpers helpers;
    private HttpExchange exchange;
    private String response;
    private User user;

    public LoginHandler(ServiceFactory serviceFactory, Helpers helpers) {
        this.serviceFactory = serviceFactory;
        this.helpers = helpers;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.exchange = exchange;
        this.response = "";
        String method = this.exchange.getRequestMethod();

        checkUser();
        if (user != null && user.getId() != 0) {
            String redirectURL = null;
            switch (user.getRole()) {
                case STUDENT -> redirectURL = "/student";
                case MENTOR -> redirectURL = "/mentor";
            }
            if (redirectURL != null) {
                exchange.getResponseHeaders().add("Location", redirectURL);
                sendResponse(HttpHelper.MOVED_PERMANENTLY);
            }
        }
        if(method.equals("GET")) {
            sendPage("templates/login-page.twig", null);
        }
        if (method.equals("POST")) {
            tryUserLogin(exchange);
        }
     }

    private void checkUser(){
        Optional<HttpCookie> cookie = helpers.getCookieHelper().getSessionIdCookie(exchange);
        if (cookie.isPresent()) {
            String sessionId = helpers.getCookieHelper().getSessionIdFromCookie(cookie.get());
            user =  serviceFactory.getUserService().getBySessionId(sessionId);
        }
    }

    private void tryUserLogin(HttpExchange exchange) throws IOException {
        String formData = helpers.getHandlerHelper().transformBodyToString(exchange);
        Map<String, String> inputs = helpers.getHttpHelper().parseFormData(formData);
        User user = serviceFactory.getUserService().login(inputs.get("email"), inputs.get("password"));
        if (user.getSession() != null && user.getSession().getUuid() != null) {
            HttpCookie httpCookie = new HttpCookie(HttpHelper.SESSION_COOKIE_NAME, user.getSession().getUuid());
            exchange.getResponseHeaders().add("Set-Cookie", httpCookie.toString());
            String redirectURL = "";
            switch (user.getRole()) {
                case STUDENT -> redirectURL = "/student";
                case MENTOR -> redirectURL = "/mentor";
                case ADMIN -> redirectURL = "/admin";
            }
            exchange.getResponseHeaders().add("Location", redirectURL);
            sendResponse(301);
        } else {
            String message = "Wrong login data.";
            sendPage("templates/login-page.twig", message);
        }
    }

    private void sendPage(String templateName, String message) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templateName);
        JtwigModel model = JtwigModel.newModel();
        model.with("message", message);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }

    private void sendResponse(int statusCode) {
        try {
            helpers.getHttpHelper().sendResponse(exchange, response, statusCode);
        } catch (IOException e) {
            // send page 500
        }
    }
}
