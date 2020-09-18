package com.codecool.queststore.models;

public class Module {
    private int id;
    private String name;

    public Module(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Module() {
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

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
