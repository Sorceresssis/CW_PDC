package com.jk204.domain;

public class WorkImage {
    private int id, work_id;
    private String imgUrl;

    public WorkImage() {
    }

    public WorkImage(int id, int work_id, String imgUrl) {
        this.id = id;
        this.work_id = work_id;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWork_id() {
        return work_id;
    }

    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
