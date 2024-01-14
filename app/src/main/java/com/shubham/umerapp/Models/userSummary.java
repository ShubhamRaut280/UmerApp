package com.shubham.umerapp.Models;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class userSummary {
    String name, email, phone;
    String documentId;
    ArrayList<SessionInfo> morning;
    ArrayList<SessionInfo> evening;

    public userSummary() {
    }

    public userSummary(Drawable photo, String name, String email, String phone, String documentId, ArrayList<SessionInfo> morning, ArrayList<SessionInfo> evening) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.documentId = documentId;
        this.morning = morning;
        this.evening = evening;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public ArrayList<SessionInfo> getMorning() {
        return morning;
    }

    public void setMorning(ArrayList<SessionInfo> morning) {
        this.morning = morning;
    }

    public ArrayList<SessionInfo> getEvening() {
        return evening;
    }

    public void setEvening(ArrayList<SessionInfo> evening) {
        this.evening = evening;
    }
}
