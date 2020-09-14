package com.codecool.queststore.handlers;

import com.codecool.queststore.dao.*;
import com.codecool.queststore.helpers.CookieHelper;
import com.codecool.queststore.helpers.HttpHelper;
import com.codecool.queststore.models.Category;
import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.Role;
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
import java.net.URLDecoder;
import java.util.*;

public class MentorHandler implements HttpHandler {

    private PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
    private UserService userService = new UserService(new UserPostgreSQLDAO(postgreSQLJDBC), new SessionPostgreSQLDAO(postgreSQLJDBC));
    private MentorService mentorService = new MentorService(new MentorDAO(postgreSQLJDBC), new RewardDAO(postgreSQLJDBC), new ModuleDAO(postgreSQLJDBC));
    private RewardService rewardService = new RewardService();
    private QuestService questService = new QuestService();
    private StudentService studentService = new StudentService();

    private HttpHelper httpHelper = new HttpHelper();
    private CookieHelper cookieHelper = new CookieHelper();
    private String response;
    private HttpExchange httpExchange;
    private User user;
    private Mentor mentor;
    private List<Reward> rewards;
    private List<Quest> quests;
    private List<Student> students;

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
            students = studentService.getStudents();
            rewards = rewardService.getRewards();
            quests = questService.getQuests();
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

        httpHelper.sendResponse(httpExchange, response, HttpHelper.NOT_FOUND);
    }

    private void postActions(HttpExchange httpExchange, String[] actions) throws IOException {
        switch (actions[2]) {
            case "add_artifact" -> postReward(httpExchange);
            case "add_quest" -> postQuest(httpExchange);
            case "add_student" -> postStudent(httpExchange);
            case "rewards_mentor" -> updateReward(httpExchange, actions);
            case "quests_mentor" -> updateQuest(httpExchange, actions);
            case "students_mentor" -> updateStudent(httpExchange, actions);
        }
        String redirectURL = "/mentor";
        httpExchange.getResponseHeaders().add("Location", redirectURL);
        httpHelper.sendResponse(httpExchange, response, HttpHelper.MOVED_PERMANENTLY);
    }

    private void getActions(HttpExchange httpExchange, String[] actions) throws Exception {

        System.out.println(Arrays.toString(actions));

//        String action = actions.length == 2 ? "" : actions[2].matches("\\d+") ? "details" : actions[2];

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
                    Reward reward = rewardService.getReward(Integer.parseInt(actions[4]));
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
                    Quest quest = questService.getQuest(Integer.parseInt(actions[4]));
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
                    Student student = studentService.getStudent(Integer.parseInt(actions[4]));
                    System.out.println(student);
                    String addRewardPath = "templates/edit_student.twig";
                    sendUpdateStudentPage(addRewardPath, student);
                }
                else if(actions[3].equals("view") && actions[4].matches("\\d+")) {
                    Student student = studentService.getStudent(Integer.parseInt(actions[4]));
                    System.out.println(student.getRewardList());
                    System.out.println(student.getQuestList());
                    String addRewardPath = "templates/view_student.twig";
                    sendUpdateStudentPage(addRewardPath, student);
                }

            }
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
            httpHelper.sendResponse(httpExchange, response, HttpHelper.MOVED_PERMANENTLY);
        }
    }

    private void postReward(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        Map<String, String> data = parseFormData(br.readLine());
        System.out.println(data);
        Reward reward = createReward(data);
        rewardService.addRewardToDB(reward);
        response = "data saved";

    }

    private void postQuest(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        Map<String, String> data = parseFormData(br.readLine());
        System.out.println(data);
        Quest quest = createQuest(data);
        questService.addQuestToDB(quest);
        response = "data saved";
    }

    private void postStudent(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        Map<String, String> data = parseFormData(br.readLine());
        System.out.println(data);
        User userStudent = createUserStudent(data);
        userService.addUserToDB(userStudent);
        Student student = createStudentAccount(data);
        studentService.addStudentToDB(student);
        response = "data saved";
    }

    private void updateReward(HttpExchange httpExchange, String[] actions) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        Map<String, String> data = parseFormData(br.readLine());
        int rewardId = Integer.parseInt(data.get("rewardId"));
        Reward reward = createReward(data);
        reward.setId(rewardId);
        rewardService.updateRewardInDB(reward);
        response = "data saved";
    }

    private void updateQuest(HttpExchange httpExchange, String[] actions) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        Map<String, String> data = parseFormData(br.readLine());
        int rewardId = Integer.parseInt(data.get("questId"));
        Quest quest = createQuest(data);
        quest.setId(rewardId);
        questService.updateQuestInDB(quest);
        response = "data saved";
    }

    private void updateStudent(HttpExchange httpExchange, String[] actions) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        Map<String, String> data = parseFormData(br.readLine());
        System.out.println(data);
        int userId = Integer.parseInt(data.get("userId"));
        User userStudent = createUserStudent(data);
        userStudent.setId(userId);
        userService.updateUserStudent(userStudent);
        studentService.updateStudentByUser(userStudent, data);
        response = "data saved";
    }

    private Student createStudentAccount(Map<String, String> data) {
        Student student = new Student();
        User studentUser = userService.getUserByCredentials(data.get("email"), data.get("password"));
        student.setId(studentUser.getId());
        student.setModuleId(Integer.parseInt(data.get("modules")));
        student.setWallet(Integer.parseInt(data.get("coins")));
        return student;
    }

    private User createUserStudent(Map<String, String> data) {
        User user = new Student();
        user.setFirstName(data.get("name"));
        user.setLastName(data.get("surname"));
        user.setEmail(data.get("email"));
        user.setPassword(data.get("password"));
        user.setActive(Boolean.parseBoolean(data.get("checkbox")));
        user.setRole(Role.STUDENT);
        return user;
    }

    private Quest createQuest(Map<String, String> data) {
        Quest quest = new Quest();
        quest.setName(data.get("name"));
        quest.setDescription(data.get("description"));
        quest.setCoinsToEarn(Integer.parseInt(data.get("price")));
        quest.setModuleId(Integer.parseInt(data.get("modules")));
        quest.setMentorId(mentor.getMentorId());
        quest.setCategory(Category.valueOf(Integer.parseInt(data.get("radio"))));
        quest.setActive(Boolean.parseBoolean(data.get("checkbox")));
        return quest;
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
        httpHelper.sendResponse(httpExchange, response, statusCode);
    }
}
