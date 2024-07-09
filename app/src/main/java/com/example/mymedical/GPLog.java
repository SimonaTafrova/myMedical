package com.example.mymedical;

public class GPLog {
    private String doctor;
    private String topic;
    private String date;

    public GPLog(String doctor, String topic, String date) {
        this.doctor = doctor;
        this.topic = topic;
        this.date = date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
