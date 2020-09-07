package com.codecool.queststore.handlers;

import com.codecool.queststore.dao.*;
import com.codecool.queststore.helpers.CookieHelper;
import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.models.Category;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.users.Mentor;
import com.codecool.queststore.models.users.User;
import com.codecool.queststore.services.MentorService;
import com.codecool.queststore.services.RewardService;
import com.codecool.queststore.services.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.*;

public class MentorHandler implements HttpHandler {

    private PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
    private UserService userService = new UserService(new UserPostgreSQLDAO(postgreSQLJDBC), new SessionPostgreSQLDAO(postgreSQLJDBC));
    private MentorService mentorService = new MentorService(new MentorDAO(postgreSQLJDBC), new RewardDAO(postgreSQLJDBC));
    private RewardService rewardService = new RewardService();

    private Helpers helpers = new Helpers();
    private CookieHelper cookieHelper = new CookieHelper();
    private String response;
    private HttpExchange httpExchange;
    private User user;
    private Mentor mentor;
    private List<Reward> rewards;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        this.httpExchange = httpExchange;
        this.response = "";
        String method = httpExchange.getRequestMethod();
        String url = httpExchange.getRequestURI().getRawPath();
        String[] actions = url.split("/");

            try {
                checkUser(httpExchange);
            } catch (Exception e) {
                e.printStackTrace();
            }

        try {
            rewards = rewardService.getRewards();
            mentor = mentorService.getMentorByUser(user);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            checkMethod(method, actions);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void checkMethod(String method, String[] actions) throws Exception {
        if (method.equals("GET")) {
            getActions(httpExchange, actions);
        }
        else if (method.equals("POST")) {
            postActions(httpExchange, actions);
        }

        sendResponse2(response, httpExchange, Helpers.NOT_FOUND);
    }

    private void postActions(HttpExchange httpExchange, String[] actions) throws IOException {
        switch (actions[2]) {
            case "add_artifact":
                postReward(httpExchange);
                break;
            case "add_quest":
                postQuest(httpExchange);
                break;

        }
        String redirectURL = "/mentor";
        httpExchange.getResponseHeaders().add("Location", redirectURL);
        sendResponse(301);
    }



    private void getActions(HttpExchange httpExchange, String[] actions) throws Exception {
        if (actions[1].equals("mentor") && actions.length == 2) {
            String templatePath = "templates/mentor_menu.twig";
            sendMentorPage(httpExchange, templatePath);
            return;
        }
        switch (actions[2]) {
            case "add_artifact":
                String addRewardPath = "templates/add_artifact.twig";
                sendMentorPage(httpExchange, addRewardPath);
                return;
            case "rewards_mentor":
                String showRewardPath = "templates/rewards_mentor.twig";
                sendMentorPage(httpExchange, showRewardPath);
                return;
            case "add_quest":
                String addQuestPath = "templates/add_quest.twig";
                sendMentorPage(httpExchange, addQuestPath);

        }
    }



    private void checkUser(HttpExchange httpExchange) throws Exception {
        Optional<HttpCookie> cookie = cookieHelper.getSessionIdCookie(httpExchange);
        if (cookie.isPresent()) {
            String sessionId = cookieHelper.getSessionIdFromCookie(cookie.get());
            user =  userService.getUserBySessionId(sessionId);
            }
        else {
            String redirectURL = "/login";
            httpExchange.getResponseHeaders().add("Location", redirectURL);
            sendResponse(Helpers.MOVED_PERMANENTLY);
        }
    }


    private void postReward(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        Map<String, String> data = parseFormData(br.readLine());
        Reward reward = createReward(data);
        mentorService.addRewardToDB(reward);
        response = "data saved";

    }

    private void postQuest(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        Map<String, String> data = parseFormData(br.readLine());
        System.out.println(data);

        response = "data saved";

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

    private Reward createReward(Map<String, String> data) {
        Reward reward = new Reward();
        reward.setName(data.get("name"));
        reward.setPrice(Integer.parseInt(data.get("price")));
        reward.setDescription(data.get("description"));
        reward.setCategory(Category.valueOf(Integer.parseInt(data.get("radio"))));
        reward.setMentorId(mentor.getMentorId());
        reward.setActive(Boolean.parseBoolean(data.get("checkbox")));
        return reward;

    }


    private void sendMentorPage(HttpExchange httpExchange, String templatePath) throws Exception {
        String response = "";
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", mentor);
        model.with("rewards", rewards);
        response = template.render(model);
        helpers.sendResponse(httpExchange, response, Helpers.OK);
    }



    private void sendResponse(int status) throws IOException {
        httpExchange.sendResponseHeaders(status, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    private void sendResponse2(String response, HttpExchange exchange, int status) throws IOException {
        if (status == 200) {
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));
            exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
        }
        exchange.sendResponseHeaders(status, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
