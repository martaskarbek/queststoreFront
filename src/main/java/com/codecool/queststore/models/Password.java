package com.codecool.queststore.models;

import com.codecool.queststore.models.users.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Map;

public class Password {


    public static void hashPasswordAddSalt(Map<String, String> formData) {
        String password = formData.get("password");
        String salt = BCrypt.gensalt();
        formData.put("salt", salt);
        String hashedPassword = BCrypt.hashpw(password, salt);
        formData.replace("password", hashedPassword);
    }

    public static boolean checkPasswords(String password, User user) {
        String hashedPasswordCandidate = BCrypt.hashpw(password, user.getSalt());
        return user.getPassword().equals(hashedPasswordCandidate);
    }
}
