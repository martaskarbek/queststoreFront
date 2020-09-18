package com.codecool.queststore.models;

public class Reward {
    private int id;
    private String name;
    private String description;
    private int price;
    private Category category;
    private int mentorId;
    private Boolean isActive;
    private String author;
    private OrderStatus orderStatus;

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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

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

    public Reward setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Reward setMentorId(int mentorId) {
        this.mentorId = mentorId;
        return this;
    }

    public Reward setActive(Boolean active) {
        isActive = active;
        return this;
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
                ", orderStatus=" + orderStatus +
                '}';
    }
}
