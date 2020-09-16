package com.codecool.queststore.dao;


import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.users.User;

import java.util.List;

public interface IRewardDao extends IDAO<Reward>{

    List<Reward> getAllMentorRewards(User user) throws Exception;
}
