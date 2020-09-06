package com.codecool.queststore.models.users;

import com.codecool.queststore.models.Reward;
import com.codecool.queststore.models.Role;

import java.util.List;

public class Mentor extends User{
    int mentorId;
    List<Module> modules;
    List<Reward> rewards;

    public Mentor(int id, String firstName, String lastName, Role role, boolean isActive, String email, String password) {
        super(id, firstName, lastName, role, isActive, email, password);
    }

    public Mentor(){};

    public int getMentorId() {
        return mentorId;
    }

    public Mentor setMentorId(int mentorId) {
        this.mentorId = mentorId;
        return this;
    }

    public List<Module> getModules() {
        return modules;
    }

    public Mentor setModules(List<Module> modules) {
        this.modules = modules;
        return this;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public Mentor setRewards(List<Reward> rewards) {
        this.rewards = rewards;
        return this;
    }
}