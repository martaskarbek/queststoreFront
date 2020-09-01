package com.codecool.queststore.services;


import com.codecool.queststore.dao.PostgreSQLJDBC;
import com.codecool.queststore.dao.RewardDAO;
import com.codecool.queststore.dao.UserPostgreSQLDAO;

public class RewardService {

    PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
    RewardDAO rewardDAO = new RewardDAO(postgreSQLJDBC);




}
