package com.jk204.pojo;

import java.util.List;

public class TeamFullInfo {
    private int id;
    private String name;
    private UserProfile captain;
    private List<UserProfile> member;

    public TeamFullInfo() {
    }

    public TeamFullInfo(int id, String name, UserProfile captain, List<UserProfile> member) {
        this.id = id;
        this.name = name;
        this.captain = captain;
        this.member = member;
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

    public UserProfile getCaptain() {
        return captain;
    }

    public void setCaptain(UserProfile captain) {
        this.captain = captain;
    }

    public List<UserProfile> getMember() {
        return member;
    }

    public void setMember(List<UserProfile> member) {
        this.member = member;
    }
}
