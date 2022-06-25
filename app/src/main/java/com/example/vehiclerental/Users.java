package com.example.vehiclerental;

import com.google.firebase.firestore.auth.User;

public class Users {
    private String userName;
    private String userImage;
    private String userDestination;
    private String userRental;
    private String userDate;
    private String userFid;
    private String userAmount;


    public Users(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserDestination() {
        return userDestination;
    }

    public void setUserDestination(String userDestination) {
        this.userDestination = userDestination;
    }

    public String getUserRental() {
        return userRental;
    }

    public void setUserRental(String userRental) {
        this.userRental = userRental;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    public String getUserFid() {
        return userFid;
    }

    public void setUserFid(String userFid) {
        this.userFid = userFid;
    }

    public String getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(String userAmount) {
        this.userAmount = userAmount;
    }
}
