package com.codecool.queststore.dao;

import com.codecool.queststore.models.Session;

public interface ISessionDAO extends IDAO<Session> {
    Session getSessionBySessionId(String sessionId) throws Exception;
}
