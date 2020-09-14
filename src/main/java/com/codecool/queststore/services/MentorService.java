package com.codecool.queststore.services;

import com.codecool.queststore.dao.MentorDAO;
import com.codecool.queststore.dao.ModuleDAO;
import com.codecool.queststore.dao.RewardDAO;
import com.codecool.queststore.models.Module;
import com.codecool.queststore.models.users.Mentor;
import com.codecool.queststore.models.users.User;

import java.util.List;


public class MentorService {

    private MentorDAO mentorDAO;
    private RewardDAO rewardDAO;
    private ModuleDAO moduleDAO;

    public MentorService(MentorDAO mentorDAO, RewardDAO rewardDAO, ModuleDAO moduleDAO){
        this.rewardDAO = rewardDAO;
        this.mentorDAO = mentorDAO;
        this.moduleDAO = moduleDAO;
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
        int mentorId = mentorDAO.getMentorId(user);
        List<Module> mentorModules = moduleDAO.getMentorModules(mentorId);
        mentor.setMentorId(mentorId);
        mentor.setModules(mentorModules);

//        mentor.setRewards(rewardDAO.getAllMentorRewards(user));

        return mentor;
    }





}
