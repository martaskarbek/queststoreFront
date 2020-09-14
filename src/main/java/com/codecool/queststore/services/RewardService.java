package com.codecool.queststore.services;


import com.codecool.queststore.dao.Connector;
import com.codecool.queststore.dao.RewardDAO;
import com.codecool.queststore.models.Category;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.users.Mentor;

import java.util.List;
import java.util.Map;

public class RewardService {

    Connector connector = new Connector();
    RewardDAO rewardDAO = new RewardDAO(connector);

    public Reward getReward(int id){
        return rewardDAO.get(id);
    }

    public List<Reward> getRewards() throws Exception {
        return  rewardDAO.getAll();
    }

    private void addReward(Reward reward) {
        rewardDAO.add(reward);
    }

    private void editReward(Reward reward) {
        rewardDAO.edit(reward);
    }

    public void createReward(Map<String, String> formData, Mentor mentor) {
        Reward reward = createRewardModel(formData, mentor);
        addReward(reward);
    }

    private Reward createRewardModel(Map<String, String> data, Mentor mentor) {
        return new Reward()
            .setName(data.get("name"))
            .setPrice(Integer.parseInt(data.get("price")))
            .setDescription(data.get("description"))
            .setCategory(Category.valueOf(Integer.parseInt(data.get("radio"))))
            .setMentorId(mentor.getMentorId())
            .setActive(Boolean.parseBoolean(data.get("checkbox")));
    }

    public void updateReward(Map<String, String> formData, Mentor mentor) {
        int rewardId = Integer.parseInt(formData.get("rewardId"));
        Reward reward = createRewardModel(formData, mentor);
        reward.setId(rewardId);
        editReward(reward);
    }
}
