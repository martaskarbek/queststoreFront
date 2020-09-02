package com.codecool.queststore.dao;

import com.codecool.queststore.models.Credentials;
import com.codecool.queststore.models.users.User;

public interface IUserDAO extends IDAO<User>{
    User getByCredentials(Credentials credentials);
}
