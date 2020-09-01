package com.codecool.queststore.models.users;

import com.codecool.queststore.models.Role;

public abstract class User {
    private int id;
    private String firstName;
    private String lastName;
    private Role role;
    private boolean isActive;
    private String email;
    private String password;

    public User(int id, String firstName, String lastName,
                Role role, boolean isActive, String email, String password
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.isActive = isActive;
        this.email = email;
        this.password = password;
    }

    public User() {

    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}