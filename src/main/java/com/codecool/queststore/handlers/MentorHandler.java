package com.codecool.queststore.handlers;

import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.users.Mentor;
import com.codecool.queststore.models.users.Student;
import com.codecool.queststore.models.users.User;
import com.codecool.queststore.services.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.util.*;

public class MentorHandler implements HttpHandler {

    private final ServiceFactory serviceFactory;
    private final Helpers helpers;

    private String response;
    private HttpExchange httpExchange;
    private User user;
    private Mentor mentor;
    private List<Reward> rewards;
    private List<Quest> quests;
    private List<Student> students;

    public MentorHandler(ServiceFactory serviceFactory, Helpers helpers) {
        this.serviceFactory = serviceFactory;
        this.helpers = helpers;
    }

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
            students = serviceFactory.getStudentService().getStudents();
            rewards = serviceFactory.getRewardService().getRewards();
            quests = serviceFactory.getQuestService().getQuests();
            mentor = serviceFactory.getMentorService().getMentorByUser(user);
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
            getActions(actions);
        }
        else if (method.equals("POST")) {
            postActions(actions);
        } else {
            sendResponse(HttpHelper.NOT_FOUND);
        }
    }

    private void postActions(String[] actions) throws IOException {
        Map<String, String> formData = helpers.getHttpHelper().getFormData(httpExchange);
        switch (actions[2]) {
            case "add_artifact" -> serviceFactory.getRewardService().createReward(formData, mentor);
            case "add_quest" -> serviceFactory.getQuestService().createQuest(formData, mentor);
            case "add_student" -> serviceFactory.getStudentService().createStudent(formData);
            case "rewards_mentor" -> serviceFactory.getRewardService().updateReward(formData, mentor);
            case "quests_mentor" -> serviceFactory.getQuestService().updateQuest(formData, mentor);
            case "students_mentor" -> serviceFactory.getStudentService().updateStudent(formData);
        }
        String redirectURL = "/mentor";
        httpExchange.getResponseHeaders().add("Location", redirectURL);
        helpers.getHttpHelper().sendResponse(httpExchange, response, HttpHelper.MOVED_PERMANENTLY);
    }

    private void getActions(String[] actions) throws Exception {

        if (actions[1].equals("mentor") && actions.length == 2) {
            String templatePath = "templates/mentor_menu.twig";
            sendMentorPage(templatePath);
            return;
        }
        switch (actions[2]) {
            case "add_artifact" -> {
                String addRewardPath = "templates/add_artifact.twig";
                sendMentorPage(addRewardPath);
            }
            case "rewards_mentor" -> {
                if(actions.length == 3){
                    String showRewardPath = "templates/rewards_mentor.twig";
                    sendMentorPage(showRewardPath);
                }
                else if(actions[3].equals("edit") && actions[4].matches("\\d+")) {
                    Reward reward = serviceFactory.getRewardService().getReward(Integer.parseInt(actions[4]));
                    System.out.println(reward);
                    String addRewardPath = "templates/edit_artifact.twig";
                    sendUpdateRewardPage(addRewardPath, reward);
                }
            }
            case "add_quest" -> {
                String addQuestPath = "templates/add_quest.twig";
                sendMentorPage(addQuestPath);
            }
            case "add_student" -> {
                String addStudentPath = "templates/student_account.twig";
                sendMentorPage(addStudentPath);
            }
            case "quests_mentor" -> {
                if(actions.length == 3){
                    String showRewardPath = "templates/quests_mentor.twig";
                    sendMentorPage(showRewardPath);
                }
                else if(actions[3].equals("edit") && actions[4].matches("\\d+")) {
                    Quest quest = serviceFactory.getQuestService().getQuest(Integer.parseInt(actions[4]));
                    System.out.println(quest);
                    String addRewardPath = "templates/edit_quest.twig";
                    sendUpdateQuestPage(addRewardPath, quest);
                }
            }
            case "students_mentor" -> {
                if(actions.length == 3){
                    String showRewardPath = "templates/students_mentor.twig";
                    sendMentorPage(showRewardPath);
                }
                else if(actions[3].equals("edit") && actions[4].matches("\\d+")) {
                    Student student = serviceFactory.getStudentService().getStudent(Integer.parseInt(actions[4]));
                    System.out.println(student);
                    String addRewardPath = "templates/edit_student.twig";
                    sendUpdateStudentPage(addRewardPath, student);
                }
                else if(actions[3].equals("view") && actions[4].matches("\\d+")) {
                    Student student = serviceFactory.getStudentService().getStudent(Integer.parseInt(actions[4]));
                    System.out.println(student.getRewardList());
                    System.out.println(student.getQuestList());
                    String addRewardPath = "templates/view_student.twig";
                    sendUpdateStudentPage(addRewardPath, student);
                }
            }
        }
    }

    private void checkUser(HttpExchange httpExchange) throws Exception {
        Optional<HttpCookie> cookie = helpers.getCookieHelper().getSessionIdCookie(httpExchange);
        if (cookie.isPresent()) {
            String sessionId = helpers.getCookieHelper().getSessionIdFromCookie(cookie.get());
            user =  serviceFactory.getUserService().getBySessionId(sessionId);
            }
        else {
            String redirectURL = "/login";
            httpExchange.getResponseHeaders().add("Location", redirectURL);
            helpers.getHttpHelper().sendResponse(httpExchange, response, HttpHelper.MOVED_PERMANENTLY);
        }
    }

    private void sendMentorPage(String templatePath) throws Exception {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", mentor);
        model.with("rewards", rewards);
        model.with("quests", quests);
        model.with("students", students);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }

    private void sendUpdateRewardPage(String templatePath, Reward reward) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", mentor);
        model.with("reward", reward);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }

    private void sendUpdateQuestPage(String templatePath, Quest quest) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", mentor);
        model.with("quest", quest);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }

    private void sendUpdateStudentPage(String templatePath, Student student) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", mentor);
        model.with("student", student);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }

    private void sendResponse(int statusCode) throws IOException {
        helpers.getHttpHelper().sendResponse(httpExchange, response, statusCode);
    }
}
