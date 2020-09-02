package com.codecool.queststore.models.users;

import com.codecool.queststore.models.Role;

public class Mentor extends User{
    public Mentor(int id, String firstName, String lastName, Role role, boolean isActive, String email, String password) {
        super(id, firstName, lastName, role, isActive, email, password);
    }
}