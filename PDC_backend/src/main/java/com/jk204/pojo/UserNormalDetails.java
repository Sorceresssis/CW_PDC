package com.jk204.pojo;

public class UserNormalDetails {
    private String nickname, username, phone;
    private long uid;

    public UserNormalDetails() {
    }

    public UserNormalDetails(String nickname, String username, String phone, long uid) {
        this.nickname = nickname;
        this.username = username;
        this.phone = phone;
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
