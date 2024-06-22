package com.example.mymedical;

public class Event {
    private String message;
    private String date;
    private int viewed;

    public Event(String message, String date, int viewed) {
        this.message = message;
        this.date = date;
        this.viewed = viewed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }
}
