package com.jk204.pojo;

public class ContestProfile {
    private int id;
    private String name;
    private String topic;
    private String year;
    private String starting_time;
    private String registration_deadline;
    private String result_announcement_time;

    public ContestProfile() {
    }

    public ContestProfile(int id, String name, String topic, String year, String starting_time, String registration_deadline, String result_announcement_time) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.year = year;
        this.starting_time = starting_time;
        this.registration_deadline = registration_deadline;
        this.result_announcement_time = result_announcement_time;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
}
