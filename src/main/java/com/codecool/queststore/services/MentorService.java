package com.codecool.queststore.services;

import com.codecool.queststore.dao.MentorDAO;
import com.codecool.queststore.dao.RewardDAO;
import com.codecool.queststore.models.Quest;
import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.users.Mentor;
import com.codecool.queststore.models.users.User;


public class MentorService {

    private MentorDAO mentorDAO;
    private RewardDAO rewardDAO;

    public MentorService(MentorDAO mentorDAO, RewardDAO rewardDAO){
        this.rewardDAO = rewardDAO;
        this.mentorDAO = mentorDAO;
    };

    public Mentor getMentorByUser(User user) throws Exception {
        Mentor mentor = new Mentor();
        mentor.setId(user.getId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setRole(user.getRole())
                .setActive(user.isActive())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword());
        mentor.setMentorId(mentorDAO.getMentorId(user));
//        mentor.setRewards(rewardDAO.getAllMentorRewards(user));

        return mentor;
    }





}
