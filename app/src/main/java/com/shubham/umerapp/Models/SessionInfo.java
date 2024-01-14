package com.shubham.umerapp.Models;

public class SessionInfo {
    String Date;
    boolean response;

    public SessionInfo(String date, boolean response) {
        Date = date;
        this.response = response;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
