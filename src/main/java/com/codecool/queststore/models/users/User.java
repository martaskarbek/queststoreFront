package com.codecool.queststore.models.users;

import com.codecool.queststore.models.Role;
import com.codecool.queststore.models.Session;

public abstract class User {
    private int id;
    private String firstName;
    private String lastName;
    private Role role;
    private boolean isActive;
    private String email;
    private String password;
    private Session session;

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

    };

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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Session getSession() {
        return session;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public User setActive(boolean active) {
        isActive = active;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setSession(Session session) {
        this.session = session;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", session=" + session +
                '}';
    }
}