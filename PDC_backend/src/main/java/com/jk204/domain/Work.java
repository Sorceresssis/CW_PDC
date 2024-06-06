package com.jk204.domain;

public class Work {
    private int id, team_id, likes;
    private String title, intro, award_time;
    private int award;
    private String update_time;

    public Work() {
    }

    public Work(String title, String intro, int team_id) {
        this.title = title;
        this.intro = intro;
        this.team_id = team_id;
    }

    public Work(int id, int team_id, int likes, String title, String intro, String award_time, int award, String update_time) {
        this.id = id;
        this.team_id = team_id;
        this.likes = likes;
        this.title = title;
        this.intro = intro;
        this.award_time = award_time;
        this.award = award;
        this.update_time = update_time;
    }

    public Work(int id, String title, String intro) {
        this.id = id;
        this.title = title;
        this.intro = intro;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAward_time() {
        return award_time;
    }

    public void setAward_time(String award_time) {
        this.award_time = award_time;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
