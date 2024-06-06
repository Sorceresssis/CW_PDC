package com.jk204.pojo;

public class ContestDetails {
    private int id, category_id;
    private String name, topic, intro, starting_time, registration_deadline, result_announcement_time, year;
    private String status;

    public ContestDetails() {
    }

    public ContestDetails(int id, int category_id, String name, String topic, String intro, String starting_time, String registration_deadline, String result_announcement_time, String year, String status) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.topic = topic;
        this.intro = intro;
        this.starting_time = starting_time;
        this.registration_deadline = registration_deadline;
        this.result_announcement_time = result_announcement_time;
        this.year = year;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getStarting_time() {
        return starting_time;
    }

    public void setStarting_time(String starting_time) {
        this.starting_time = starting_time;
    }

    public String getRegistration_deadline() {
        return registration_deadline;
    }

    public void setRegistration_deadline(String registration_deadline) {
        this.registration_deadline = registration_deadline;
    }

    public String getResult_announcement_time() {
        return result_announcement_time;
    }

    public void setResult_announcement_time(String result_announcement_time) {
        this.result_announcement_time = result_announcement_time;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
