package com.codecool.queststore.dao;

import com.codecool.queststore.models.users.Mentor;
import com.codecool.queststore.models.users.User;

public interface IMentorDAO  extends IDAO<Mentor> {
    int getMentorId(User user);
}
