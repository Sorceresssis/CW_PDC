package com.jk204.domain;

public class User {
    private int id;
    private short rule;
    private String nickname, username, pwd, salt, phone;

    private long uid;

    public User() {

    }

    public User(int id, short rule, long uid, String nickname, String username, String pwd, String salt, String phone) {
        this.id = id;
        this.rule = rule;
        this.uid = uid;
        this.nickname = nickname;
        this.username = username;
        this.pwd = pwd;
        this.salt = salt;
        this.phone = phone;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getRule() {
        return rule;
    }

    public void setRule(short rule) {
        this.rule = rule;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
