package com.jk204.domain;

public class Team {
    private int id, captain_id, contest_id;
    private String name;

    public Team() {
    }

    public Team(int id, int captain_id, int contest_id) {
        this.id = id;
        this.captain_id = captain_id;
        this.contest_id = contest_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaptain_id() {
        return captain_id;
    }

    public void setCaptain_id(int captain_id) {
        this.captain_id = captain_id;
    }

    public int getContest_id() {
        return contest_id;
    }

    public void setContest_id(int contest_id) {
        this.contest_id = contest_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
