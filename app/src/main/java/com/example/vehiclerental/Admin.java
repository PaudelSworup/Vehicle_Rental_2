package com.example.vehiclerental;

public class Admin {
    private String adminName;
    private String adminRating;
    private String imageAdmin;
    private String destination;
    private String rentalTime;
    private String firebase_id;

    public Admin(){}

    public Admin(String adminName, String adminRating, String imageAdmin, String destination, String rentalTime, String firebase_id) {
        this.adminName = adminName;
        this.adminRating = adminRating;
        this.imageAdmin = imageAdmin;
        this.destination = destination;
        this.rentalTime = rentalTime;
        this.firebase_id = firebase_id;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
