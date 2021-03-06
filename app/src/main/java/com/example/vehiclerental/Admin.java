package com.example.vehiclerental;

public class Admin {
    private String adminId;
    private String adminName;
    private String adminRating;
    private String imageAdmin;
    private String source;
    private String destination;
    private String dates;
    private String rentalTime;
    private String firebase_id;

    public Admin(){}

    public Admin(String adminName, String adminRating, String imageAdmin, String source, String destination, String rentalTime, String firebase_id) {
        this.adminName = adminName;
        this.adminRating = adminRating;
        this.imageAdmin = imageAdmin;
        this.source = source;
        this.destination = destination;
        this.rentalTime = rentalTime;
        this.firebase_id = firebase_id;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminRating() {
        return adminRating;
    }

    public void setAdminRating(String adminRating) {
        this.adminRating = adminRating;
    }

    public String getImageAdmin() {
        return imageAdmin;
    }

    public void setImageAdmin(String imageAdmin) {
        this.imageAdmin = imageAdmin;
    }

    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(String rentalTime) {
        this.rentalTime = rentalTime;
    }

    public String getFirebase_id() {
        return firebase_id;
    }

    public void setFirebase_id(String firebase_id) {
        this.firebase_id = firebase_id;
    }
}
