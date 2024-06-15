package com.example.mymedical;

import java.time.LocalDate;

public class GPLog {
    private String doctor;
    private String topic;
    private LocalDate localDate;



    public GPLog(String doctor, String topic, LocalDate localDate) {
        this.doctor = doctor;
        this.topic = topic;
        this.localDate = localDate;
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

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
