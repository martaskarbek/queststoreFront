package com.codecool.queststore.handlers;

import com.codecool.queststore.models.Reward;
import com.codecool.queststore.services.RewardService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RewardHandler implements HttpHandler {


    private RewardService rewardService = new RewardService();



    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().getRawPath();
        String[] actions = url.split("/");
        System.out.println(Arrays.toString(actions));
        System.out.println(actions.length);
        String action = actions.length == 2 ? "" : actions[2].matches("\\d+") ? "reward" : actions[2] ;
//        ObjectMapper mapper = new ObjectMapper();
        String response = "";

        try {
            switch (action) {
                case "add":
                    //todo  add new user -> POST
                    break;
                case "reward":
                    //np. http://localhost:8001/rewards/reward/1
                    Reward reward = rewardService.getReward(Integer.parseInt(actions[3]));
//                    Student student = this.studentsDao.getStudent(Integer.parseInt(actions[3]));
//                    response = mapper.writeValueAsString(reward);
                    break;
                default:
                    //np. http://localhost:8001/rewards
                    List<Reward> rewards = rewardService.getRewards();
//                    response = mapper.writeValueAsString(rewards);
            }
            sendResponse(response, exchange, 200);

        } catch (Exception error) {
            sendResponse(response, exchange, 404);
        }
    }

    private void sendResponse(String response, HttpExchange exchange, int status) throws IOException {
        if (status == 200) {
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));
            exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
        }

        exchange.sendResponseHeaders(status, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
