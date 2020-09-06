package com.codecool.queststore.services;

import com.codecool.queststore.dao.MentorDAO;
import com.codecool.queststore.models.users.Mentor;


public class MentorService {

    private MentorDAO mentorDAO;

    public MentorService(MentorDAO mentorDAO){
        this.mentorDAO = mentorDAO;
    };

    public Mentor getMentorByUserId(int id) {
        return mentorDAO.get(id);

    }


}
