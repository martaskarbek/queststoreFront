package com.codecool.queststore.services;


import com.codecool.queststore.dao.PostgreSQLJDBC;
import com.codecool.queststore.dao.RewardDAO;
import com.codecool.queststore.models.Reward;

import java.util.List;

public class RewardService {

    PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
    RewardDAO rewardDAO = new RewardDAO(postgreSQLJDBC);

    public Reward getReward(int id){

        return null;
        
    }


    public List<Reward> getRewards() throws Exception {
        return  rewardDAO.getAll();
    }


    public void addRewardToDB(Reward reward) {
        rewardDAO.add(reward);
    }
}
