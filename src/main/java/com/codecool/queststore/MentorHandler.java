package com.codecool.queststore;

import com.codecool.queststore.dao.PostgreSQLJDBC;
import com.codecool.queststore.dao.SessionPostgreSQLDAO;
import com.codecool.queststore.dao.UserPostgreSQLDAO;
import com.codecool.queststore.helpers.CookieHelper;
import com.codecool.queststore.helpers.Helpers;
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

public class MentorHandler implements HttpHandler {

    private PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
    private UserService userService = new UserService(new UserPostgreSQLDAO(postgreSQLJDBC), new SessionPostgreSQLDAO(postgreSQLJDBC));

    private Helpers helpers = new Helpers();
    private CookieHelper cookieHelper = new CookieHelper();
    static final String SESSION_COOKIE_NAME = "SessionID";
    private String response;
    private HttpExchange httpExchange;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        this.httpExchange = httpExchange;
        this.response = "";
        String method = this.httpExchange.getRequestMethod();

        if(method.equals("GET")) {
            try {
                checkUser(httpExchange);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void checkUser(HttpExchange httpExchange) throws Exception {
        Optional<HttpCookie> cookie = getSessionIdCookie(httpExchange);
        if (cookie.isPresent()) {
            String sessionId = cookieHelper.getSessionIdFromCookie(cookie.get());
            User user =  userService.getUserBySessionId(sessionId);
            String templatePath = "templates/mentor_menu.twig";
//            sendPage(user, httpExchange, templatePath);
            userAction(user, httpExchange);
            }
        else {
            String redirectURL = "/login";
            httpExchange.getResponseHeaders().add("Location", redirectURL);
            sendResponse(301);

        }

    }

    private void userAction(User user, HttpExchange httpExchange) throws IOException {
        String url = httpExchange.getRequestURI().getRawPath();
        String[] actions = url.split("/");
        String action = actions.length == 2 ? "" : actions[2];
//        String action = actions[2];
        String response = "";

        try {
            switch (action) {
                case "add_artifact":
                    String addRewardPath = "templates/add_artifact.twig";
                    sendPage(user, httpExchange, addRewardPath);
                    //todo  add new user -> POST
                    break;
                case "details":
                    //np. http://localhost:8005/users/details/1
//                    Student student = this.studentsDao.getStudent(Integer.parseInt(actions[3]));
                    response = "";
                    break;
                default:
                    //np. http://localhost:8005/users
                    String templatePath = "templates/mentor_menu.twig";
                    sendPage(user, httpExchange, templatePath);
            }
            helpers.sendResponse(httpExchange, response,  Helpers.OK);

        } catch (Exception error) {
            helpers.sendResponse(httpExchange, response, Helpers.NOT_FOUND);
        }
    }


    private void sendPage(User user, HttpExchange httpExchange, String templatePath) throws IOException {
        String response = "";
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", user);
        response = template.render(model);
        helpers.sendResponse(httpExchange, response, Helpers.OK);

    }

    private void sendResponse(int rCode) throws IOException {
        httpExchange.sendResponseHeaders(rCode, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }



    private Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        System.out.println(cookieStr);
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        System.out.println(cookies);
        return cookieHelper.findCookieByName(SESSION_COOKIE_NAME, cookies);

    }
}
