package com.codecool.queststore.models.users;

import com.codecool.queststore.models.Role;

public class UserFactory {

    public static final User USER_NOT_FOUND = create(0, "", "", Role.STUDENT, false, "", "");

    public static User create(int id, String firstName, String lastName,
                       Role role, boolean isActive, String email, String password) {

        switch (role.toString()) {
            case "ADMIN":
                User admin = new Admin(id, firstName, lastName, role, isActive, email, password);
                return admin;

            case "MENTOR":
                User mentor = new Mentor(id, firstName, lastName, role, isActive, email, password);
                return mentor;

            case "STUDENT":
                User student = new Student(id, firstName, lastName, role, isActive, email, password);
                return student;

            default:
                return null;

        }
    }
}