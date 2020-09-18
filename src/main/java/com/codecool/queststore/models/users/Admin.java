package com.codecool.queststore.models.users;

import com.codecool.queststore.models.Role;

public class Admin extends User{
    public Admin(int id, String firstName, String lastName, Role role, boolean isActive, String email, String password, String salt) {
        super(id, firstName, lastName, role, isActive, email, password, salt);
    }
}