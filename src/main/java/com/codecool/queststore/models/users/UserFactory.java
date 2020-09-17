package com.codecool.queststore.models.users;

import com.codecool.queststore.models.Role;

public class UserFactory {

    public static final User USER_NOT_FOUND = create(0, "", "", Role.STUDENT, false, "", "", "");

    public static User create(int id, String firstName, String lastName,
                       Role role, boolean isActive, String email, String password, String salt) {

        switch (role.toString()) {
            case "ADMIN":
                return new Admin(id, firstName, lastName, role, isActive, email, password, salt);

            case "MENTOR":
                return new Mentor(id, firstName, lastName, role, isActive, email, password, salt);

            case "STUDENT":
                return new Student(id, firstName, lastName, role, isActive, email, password, salt);

            default:
                return null;

        }
    }
}

