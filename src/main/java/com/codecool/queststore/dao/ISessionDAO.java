package com.codecool.queststore.dao;

import com.codecool.queststore.models.Session;

public interface ISessionDAO extends IDAO<Session> {
    Session getBySessionId(String sessionId) throws Exception;
}
