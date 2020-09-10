package com.codecool.queststore.models;

import com.codecool.queststore.models.users.Mentor;

public class Reward {
    private int id;
    private String name;
    private String description;
    private int price;
    private Category category;
    private int mentorId;
    private Boolean isActive;
    private String author;

    public Reward(int id, String name, String description, int price, Category category, int mentorId, boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.mentorId = mentorId;
        this.isActive = isActive;
    }

    public Reward(){};


    public Boolean getActive() {
        return isActive;
    }

    public String getAuthor() {
        return author;
    }

    public Reward setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Reward setId(int id) {
        this.id = id;
        return this;
    }

    public Reward setName(String name) {
        this.name = name;
        return this;
    }

    public Reward setDescription(String description) {
        this.description = description;
        return this;
    }


    public Reward setPrice(int price) {
        this.price = price;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public int getMentorId() {
        return mentorId;
    }



    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", mentorId=" + mentorId +
                '}';
    }
}
