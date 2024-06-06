package com.jk204.domain;

public class TeamMember {
    private int id, team_id, user_id;

    public TeamMember() {
    }

    public TeamMember(int id, int team_id, int user_id) {
        this.id = id;
        this.team_id = team_id;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
