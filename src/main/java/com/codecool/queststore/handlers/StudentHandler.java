package com.codecool.queststore.handlers;

import com.codecool.queststore.helpers.Helpers;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.StudentQuest;
import com.codecool.queststore.models.users.Student;
import com.codecool.queststore.models.users.User;
import com.codecool.queststore.models.users.UserFactory;
import com.codecool.queststore.services.ServiceFactory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StudentHandler implements HttpHandler {

    private final ServiceFactory serviceFactory;
    private final Helpers helpers;

    private String response;
    private HttpExchange httpExchange;
    private User user;
    private Student student;

    public StudentHandler(ServiceFactory serviceFactory, Helpers helpers) {
        this.serviceFactory = serviceFactory;
        this.helpers = helpers;
    }

    @Override
    public void handle(HttpExchange httpExchange){

        init(httpExchange);
        String method = httpExchange.getRequestMethod();
        String[] actions = getStringsFromURL(httpExchange);

        try {
            user = loggedUser(httpExchange);

        } catch (Exception e) {
            e.printStackTrace();
        }

        checkMethod(method, actions);
    }


    private String[] getStringsFromURL(HttpExchange httpExchange) {
        String url = httpExchange.getRequestURI().getRawPath();
        String[] actions = url.split("/");
        return actions;
    }

    public void init(HttpExchange httpExchange) {
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
            case "student_menu" -> serviceFactory.getStudentQuestService().updateStudentQuest(formData);


        }
        String redirectURL = "/student";
        httpExchange.getResponseHeaders().add("Location", redirectURL);
        sendResponse(HttpHelper.MOVED_PERMANENTLY);
    }

    private void getActions(String[] actions) {

        if (actions[1].equals("student") && actions.length == 2) {
            initializeStudentPage();
        }
        switch (actions[2]) {
            case "student_menu" -> initializeStudentAction(actions);
            case "rewards_student" -> initializeStudentRewards(actions);
            case "quests_student" -> initializeStudentQuests(actions);

        }
    }

    private void initializeStudentQuests(String[] actions) {
        if(actions.length == 3){
            sendStudentQuestsPage("templates/quests_student.twig");
        }
//        else if(actions[3].equals("edit") && actions[4].matches("\\d+")) {
//            Reward reward = serviceFactory.getRewardService().getReward(Integer.parseInt(actions[4]));
//            sendUpdateRewardPage("templates/edit_artifact.twig", reward);
//        }
    }

    private void sendStudentQuestsPage(String templatePath) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        List<Quest> quests = serviceFactory.getQuestService().getQuests();
        model.with("quests", quests);
        model.with("student", student);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }

    private void initializeStudentRewards(String[] actions) {
        if(actions.length == 3){
            sendStudentRewardsPage("templates/rewards_student.twig");
        }
//        else if(actions[3].equals("edit") && actions[4].matches("\\d+")) {
//            Reward reward = serviceFactory.getRewardService().getReward(Integer.parseInt(actions[4]));
//            sendUpdateRewardPage("templates/edit_artifact.twig", reward);
//        }
    }

    private void sendStudentRewardsPage(String templatePath) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        List<Reward> rewards = serviceFactory.getRewardService().getRewards();
        model.with("rewards", rewards);
        model.with("student", student);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }

    private void initializeStudentAction(String[] actions) {
        if(actions[3].equals("use") && actions[4].matches("\\d+") && actions[5].matches("\\d+")) {
            Reward reward = serviceFactory.getRewardService().getReward(Integer.parseInt(actions[4]));
            sendUpdateStatusRewardPage("templates/use_reward.twig", student, reward);
        }
        else if(actions[3].equals("submit") && actions[4].matches("\\d+") && actions[5].matches("\\d+")) {
            int studentId = Integer.parseInt(actions[4]);
            int questId = Integer.parseInt(actions[5]);
            StudentQuest studentQuest = serviceFactory.getStudentQuestService().getStudentQuest(studentId, questId);
            sendUpdateStatusQuestPage("templates/submit_quest.twig", student, studentQuest);
        }
    }

    private void sendUpdateStatusRewardPage(String templatePath, Student student, Reward reward) {
    }

    private void sendUpdateStatusQuestPage(String templatePath, Student student, StudentQuest studentQuest) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("student", student);
        model.with("studentQuest", studentQuest);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }



    private void initializeStudentPage() {
//        List<Student> studentsWithQuestsToMark = serviceFactory.getStudentService().createStudentListWithQuestsToMark(mentor, students);
        student = serviceFactory.getStudentService().getStudentByUser(user);

        sendStudentStartPage("templates/student_menu.twig", student);
    }

    private void sendStudentStartPage(String templatePath, Student student) {

        JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
        JtwigModel model = JtwigModel.newModel();
        model.with("student", student);
        response = template.render(model);
        sendResponse(HttpHelper.OK);
    }


    public User loggedUser(HttpExchange httpExchange){
        user = UserFactory.USER_NOT_FOUND;
        Optional<HttpCookie> cookie = helpers.getCookieHelper().getSessionIdCookie(httpExchange);
        if (cookie.isPresent()) {
            String sessionId = helpers.getCookieHelper().getSessionIdFromCookie(cookie.get());
            return user =  serviceFactory.getUserService().getBySessionId(sessionId);
        }
        else {
            String redirectURL = "/login";
            httpExchange.getResponseHeaders().add("Location", redirectURL);
            sendResponse(HttpHelper.MOVED_PERMANENTLY);
        }

        return user;
    }

    private void sendResponse(int statusCode) {
        try {
            helpers.getHttpHelper().sendResponse(httpExchange, response, statusCode);
        } catch (IOException e) {
            // send page 500
        }
    }


}
