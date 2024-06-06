package com.jk204.pojo;

public class ContestTitle {
    private int id;
    private String name;

    public ContestTitle() {
    }

    public ContestTitle(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
