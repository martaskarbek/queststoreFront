package com.codecool.queststore.handlers;

import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.models.*;
import com.codecool.queststore.models.Module;
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
    public void handle(HttpExchange httpExchange) {
        init(httpExchange);
        String method = httpExchange.getRequestMethod();
        String url = httpExchange.getRequestURI().getRawPath();
        String[] actions = url.split("/");

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

        // to improve

        students = serviceFactory.getStudentService().getStudents();
        rewards = serviceFactory.getRewardService().getRewards();
        quests = serviceFactory.getQuestService().getQuests();
        mentor = serviceFactory.getMentorService().getMentorByUser(user);

        checkMethod(method, actions);
    }

    private void init(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
        this.response = "";
    }

    private void checkMethod(String method, String[] actions) {
        if (method.equals("GET")) {
            getActions(actions);
        }
        else if (method.equals("POST")) {
            postActions(actions);
        } else {
            sendResponse(HttpHelper.NOT_FOUND);
        }
    }

    private void postActions(String[] actions) {
        Map<String, String> formData = null;
        try {
            formData = helpers.getHttpHelper().getFormData(httpExchange);
        } catch (IOException e) {
            // send page 500
        }
        switch (actions[2]) {
            case "add_artifact" -> serviceFactory.getRewardService().createReward(formData, mentor);
            case "add_quest" -> serviceFactory.getQuestService().createQuest(formData, mentor);
            case "add_student" -> serviceFactory.getStudentService().createStudent(formData);
            case "rewards_mentor" -> serviceFactory.getRewardService().updateReward(formData, mentor);
            case "quests_mentor" -> serviceFactory.getQuestService().updateQuest(formData, mentor);
            case "students_mentor" -> serviceFactory.getStudentService().updateStudent(formData);
            case "mark_quest" -> serviceFactory.getStudentQuestService().updateStudentQuest(formData);
        }
        String redirectURL = "/mentor";
        httpExchange.getResponseHeaders().add("Location", redirectURL);
        sendResponse(HttpHelper.MOVED_PERMANENTLY);
    }

    private void getActions(String[] actions) {

        if (actions[1].equals("mentor") && actions.length == 2) {
            initializeMentorPage();
            return;
        }
        switch (actions[2]) {
            case "add_artifact" -> initializeAddReward();
            case "rewards_mentor" -> initializeMentorRewards(actions);
            case "add_quest" -> initializeAddQuest();
            case "add_student" -> initializeAddStudent();
            case "quests_mentor" -> initializeMentorQuests(actions);
            case "students_mentor" -> initializeMentorStudents(actions);
            case "mark_quest" -> initializeMarkQuest(actions);
        }
    }

    private void initializeMarkQuest(String[] actions) {

        if(actions[3].matches("\\d+") && actions[4].matches("\\d+")) {
            int studentId = Integer.parseInt(actions[3]);
            int questId = Integer.parseInt(actions[4]);
            StudentQuest studentQuest = serviceFactory.getStudentQuestService().getStudentQuest(studentId, questId);
            System.out.println(studentQuest);
            sendMarkQuestPage("templates/mark_quest.twig", studentQuest);

        }

    }


    private void initializeMentorStudents(String[] actions) {
        if(actions.length == 3){
            sendMentorPage("templates/students_mentor.twig");
        }
        else if(actions[3].equals("edit") && actions[4].matches("\\d+")) {
            Student student = serviceFactory.getStudentService().getStudent(Integer.parseInt(actions[4]));
            sendUpdateStudentPage("templates/edit_student.twig", student);
        }
        else if(actions[3].equals("view") && actions[4].matches("\\d+")) {
            Student student = serviceFactory.getStudentService().getStudent(Integer.parseInt(actions[4]));
            sendUpdateStudentPage("templates/view_student.twig", student);
        }
    }

    private void initializeMentorQuests(String[] actions) {
        if(actions.length == 3){
            sendMentorPage("templates/quests_mentor.twig");
        }
        else if(actions[3].equals("edit") && actions[4].matches("\\d+")) {
            Quest quest = serviceFactory.getQuestService().getQuest(Integer.parseInt(actions[4]));
            sendUpdateQuestPage("templates/edit_quest.twig", quest);
        }
    }

    private void initializeAddStudent() {
        sendMentorPage("templates/student_account.twig");
    }

    private void initializeAddQuest() {
        sendMentorPage("templates/add_quest.twig");
    }

    private void initializeMentorRewards(String[] actions) {
        if(actions.length == 3){
            sendMentorPage("templates/rewards_mentor.twig");
        }
        else if(actions[3].equals("edit") && actions[4].matches("\\d+")) {
            Reward reward = serviceFactory.getRewardService().getReward(Integer.parseInt(actions[4]));
            sendUpdateRewardPage("templates/edit_artifact.twig", reward);
        }
    }


    private void initializeAddReward() {
        sendMentorPage("templates/add_artifact.twig");
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

    private void initializeMentorPage() {
        List<Student> studentsWithQuestsToMark = serviceFactory.getStudentService().createStudentListWithQuestsToMark(mentor, students);
        sendMentorStartPage("templates/mentor_menu.twig", studentsWithQuestsToMark);
    }

    private void sendMentorPage(String templatePath) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", mentor);
        model.with("rewards", rewards);
        model.with("quests", quests);
        model.with("students", students);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }

    private void sendMarkQuestPage(String templatePath, StudentQuest studentQuest) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", mentor);
        model.with("studentQuest", studentQuest);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }

    private void sendMentorStartPage(String templatePath, List<Student> mentorStudents) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", mentor);
        model.with("mentorStudents", mentorStudents);
        response = template.render(model);
        sendResponse(HttpHelper.OK);

    }

    private void sendUpdateRewardPage(String templatePath, Reward reward) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", mentor);
        model.with("reward", reward);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }

    private void sendUpdateQuestPage(String templatePath, Quest quest) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", mentor);
        model.with("quest", quest);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }

    private void sendUpdateStudentPage(String templatePath, Student student) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("mentor", mentor);
        model.with("student", student);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }

    private void sendResponse(int statusCode) {
        try {
            helpers.getHttpHelper().sendResponse(httpExchange, response, statusCode);
        } catch (IOException e) {
            // send page 500
        }
    }
}
