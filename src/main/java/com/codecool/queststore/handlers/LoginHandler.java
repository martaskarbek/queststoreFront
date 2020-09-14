package com.codecool.queststore.handlers;

import com.codecool.queststore.dao.Connector;
import com.codecool.queststore.dao.SessionPostgreSQLDAO;
import com.codecool.queststore.dao.UserPostgreSQLDAO;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.models.users.User;
import com.codecool.queststore.services.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class LoginHandler implements HttpHandler {
    private HttpExchange exchange;
    private String response;
    private Connector connector = new Connector();
    private UserService userService = new UserService(new UserPostgreSQLDAO(connector), new SessionPostgreSQLDAO(connector));

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.exchange = exchange;
        this.response = "";
        String method = this.exchange.getRequestMethod();

        if(method.equals("GET")) {
            sendPage("templates/login-page.twig", null);
        }
        if (method.equals("POST")) {
            tryUserLogin(exchange);
        }
     }

    private void tryUserLogin(HttpExchange exchange) throws IOException {
        String formData = transformBodyToString();
        Map<String, String> inputs = parseFormData(formData);
        User user = userService.login(inputs.get("email"), inputs.get("password"));
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
        //HttpResponse
        sendResponse(HttpHelper.OK);
    }

    private void sendResponse(int rCode) throws IOException {
        exchange.sendResponseHeaders(rCode, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String transformBodyToString() throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        return br.readLine();
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}
