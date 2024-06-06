package com.jk204.pojo;

import java.util.List;

public class WorkFullInfo {
    private int id;
    private String title;
    private String intro;
    private int likes;
    private int award;
    private String awardTime;
    private List<String> imgs;

    private String updateTime;

    public WorkFullInfo() {
    }

    public WorkFullInfo(int id, String title, String intro, int likes, int award, String awardTime, List<String> imgs, String updateTime) {
        this.id = id;
        this.title = title;
        this.intro = intro;
        this.likes = likes;
        this.award = award;
        this.awardTime = awardTime;
        this.imgs = imgs;
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public String getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(String awardTime) {
        this.awardTime = awardTime;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
