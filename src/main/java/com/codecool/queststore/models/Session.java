package com.codecool.queststore.models;

public class Session {
    private String uuid;
    private int userId;

    public Session(String uuid, int userId) {
        this.uuid = uuid;
        this.userId = userId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
