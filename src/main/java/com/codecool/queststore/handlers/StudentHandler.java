package com.codecool.queststore.handlers;

import com.codecool.queststore.dao.PostgreSQLJDBC;
import com.codecool.queststore.dao.SessionPostgreSQLDAO;
import com.codecool.queststore.dao.UserPostgreSQLDAO;
import com.codecool.queststore.helpers.CookieHelper;
import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.models.Role;
import com.codecool.queststore.models.users.User;
import com.codecool.queststore.services.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.List;
import java.util.Optional;

public class StudentHandler implements HttpHandler {
    private CookieHelper cookieHelper = new CookieHelper();
    private PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
    private UserService userService = new UserService(new UserPostgreSQLDAO(postgreSQLJDBC), new SessionPostgreSQLDAO(postgreSQLJDBC));
    private Helpers helpers = new Helpers();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Optional<HttpCookie> cookie = getSessionIdCookie(exchange);
        if (cookie.isPresent()) {
            String sessionId = cookieHelper.getSessionIdFromCookie(cookie.get());
            User user =  userService.getUserBySessionId(sessionId);
            if (user.getRole().equals(Role.STUDENT)) {
                String templatePath = "templates/student_menu.twig";
                sendUserPage(user, exchange, templatePath);
            }
        }
        String redirectURL = "/login";
        exchange.getResponseHeaders().add("Location", redirectURL);
        sendResponse(301, exchange);
    }

    private void sendResponse(int rCode, HttpExchange httpExchange) throws IOException {
        httpExchange.sendResponseHeaders(rCode, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }

    private void sendUserPage(User user, HttpExchange httpExchange, String templatePath) throws IOException {
        String response = "";
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("student", user);
        response = template.render(model);
        helpers.sendResponse(httpExchange, response, Helpers.OK);
    }

    private Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        System.out.println(cookieStr);
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        System.out.println(cookies);
        return cookieHelper.findCookieByName(Helpers.SESSION_COOKIE_NAME, cookies);

    }
}
