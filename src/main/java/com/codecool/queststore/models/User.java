package com.codecool.queststore.models;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private Role role;
    private boolean isActive;
    private String email;
    private String password;

    public User() {
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }


    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public User setActive(boolean active) {
        this.isActive = active;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setUser(int id, String firstName, String lastName, Role role, boolean isActive, String email, String password) {
        User user = new User();
        user.setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setRole(role)
                .setActive(isActive)
                .setEmail(email)
                .setPassword(password);
        return user;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
