package com.volare_automation.springwebshop.model;

public class UserBackUpModel {
    private int userid;
    private String username;
    private String password;
    private String isEnabled;




    public UserBackUpModel() {
    }

    public UserBackUpModel(String username, String password, String isEnabled) {

        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
    }

    public UserBackUpModel(int userid, String username, String password, String isEnabled) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
    }


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }
}
