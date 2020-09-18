package com.codecool.queststore.handlers;

import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.Role;
import com.codecool.queststore.models.users.User;
import com.codecool.queststore.services.RewardService;
import com.codecool.queststore.services.ServiceFactory;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;
import java.util.Optional;

public class RewardHandler implements HttpHandler {

    private final ServiceFactory serviceFactory;
    private final Helpers helpers;
    private User user;
    private HttpExchange httpExchange;
    private String response;

    public RewardHandler(ServiceFactory serviceFactory, Helpers helpers) {
        this.serviceFactory = serviceFactory;
        this.helpers = helpers;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        init(httpExchange);

        String method = httpExchange.getRequestMethod();
        String requestURI = httpExchange.getRequestURI().toString();
        System.out.println(requestURI);

        try {
            checkUser(httpExchange);
            if (user.getId() == 0 || !user.getRole().equals(Role.MENTOR)) {
                String redirectURL = "/login";
                httpExchange.getResponseHeaders().add("Location", redirectURL);
                sendResponse(HttpHelper.MOVED_PERMANENTLY);
            }
        } catch (Exception e) {
            // send page you are not authorized 401
        }

        if (method.equals("GET")) {
            try {
                initializeGet(httpExchange);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (method.equals("POST")) {
            initializePost(httpExchange);
        } else if (requestURI.contains("logout")){
            redirectToLogin(httpExchange);
        }
    }

    private void init(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
        this.response = "";
    }

    private void checkUser(HttpExchange httpExchange) {
        Optional<HttpCookie> cookie = helpers.getCookieHelper().getSessionIdCookie(httpExchange);
        if (cookie.isPresent()) {
            String sessionId = helpers.getCookieHelper().getSessionIdFromCookie(cookie.get());
            user =  serviceFactory.getUserService().getBySessionId(sessionId);
        }
        else {
            String redirectURL = "/login";
            httpExchange.getResponseHeaders().add("Location", redirectURL);
            sendResponse(HttpHelper.MOVED_PERMANENTLY);
        }
    }

    private void redirectToLogin(HttpExchange httpExchange) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Location", "/rewards");
        httpExchange.sendResponseHeaders(302, 0);
    }

    private void initializePost(HttpExchange httpExchange) {
    }

    private void initializeGet(HttpExchange httpExchange) throws Exception {
        String response = "";
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/rewards_mentor.twig");
        JtwigModel model = JtwigModel.newModel();
        List<Reward> rewards = serviceFactory.getRewardService().getRewards();
        model.with("rewards", rewards);
        response = template.render(model);
        helpers.getHttpHelper().sendResponse(httpExchange, response, HttpHelper.OK);
    }

    private void sendResponse(int statusCode) {
        try {
            helpers.getHttpHelper().sendResponse(httpExchange, response, statusCode);
        } catch (IOException e) {
            // send page 500
        }
    }
}
