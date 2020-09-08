package com.codecool.queststore.models;

public class Quest {
    private int id;
    private String name;
    private int coinsToEarn;
    private int moduleId;
    private String description;
    private int mentorId;
    private int categoryId;
    private Boolean isActive;


    public Quest(int id, String name, String description, int coinsToEarn, int moduleId, int mentorId, int categoryId, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.coinsToEarn = coinsToEarn;
        this.moduleId = moduleId;
        this.description = description;
        this.mentorId = mentorId;
        this.categoryId = categoryId;
        this.isActive = isActive;
    }

    public Quest() {
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoinsToEarn() {
        return coinsToEarn;
    }

    public void setCoinsToEarn(int coinsToEarn) {
        this.coinsToEarn = coinsToEarn;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coinsToEarn=" + coinsToEarn +
                ", moduleId=" + moduleId +
                ", description='" + description + '\'' +
                ", mentorId=" + mentorId +
                ", categoryId=" + categoryId +
                ", isActive=" + isActive +
                '}';
    }
}
