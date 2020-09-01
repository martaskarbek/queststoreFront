package com.codecool.queststore.handlers;

import com.codecool.queststore.services.RewardService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RewardHandler implements HttpHandler {


    private RewardService rewardService = new RewardService();



    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
