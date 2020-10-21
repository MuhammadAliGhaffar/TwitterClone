package com.example.twitterclone;

public class ModelRI {
    //Model Class for Row items like image usrname user tweet

    int userProfile;
    String username,userStatus;

    public ModelRI(int userProfile, String username, String userStatus) {
        this.userProfile = userProfile;
        this.username = username;
        this.userStatus = userStatus;
    }


    public int getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(int userProfile) {
        this.userProfile = userProfile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
