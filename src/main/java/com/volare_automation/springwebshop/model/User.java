package com.volare_automation.springwebshop.model;

public class User {

    private int userid;
    private String username;
    private String password;
    private String isEnabled;
    private String nameName;
    private String surname;
    private String email;
    private String address;
    private String zip;
    private String city;
    private String country;
    boolean stayLogged;
    boolean adminAuth;


//    public User(int userid, String username, String password,
//                String isEnabled, String nameName, String surname,
//                String email, String address, String zip, String city, String country) {
//
//        this.userid = userid;
//        this.username = username;
//        this.password = password;
//        this.isEnabled = isEnabled;
//        this.nameName = nameName;
//        this.surname = surname;
//        this.email = email;
//        this.address = address;
//        this.zip = zip;
//        this.city = city;
//        this.country = country;
//    }

    public User() {
    }


    public String getNameName() {
        return nameName;
    }

    public void setNameName(String nameName) {
        this.nameName = nameName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    /*public User(String username, String password, String isEnabled) {

        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
    }

    public User(int userid, String username, String password, String isEnabled) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
    }*/


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

    public boolean isStayLogged() {
        return stayLogged;
    }

    public void setStayLogged(boolean stayLogged) {
        this.stayLogged = stayLogged;
    }

    public boolean isAdminAuth() {
        return adminAuth;
    }

    public void setAdminAuth(boolean adminAuth) {
        this.adminAuth = adminAuth;
    }

    //    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

//    @Override
//    public String toString(){
//        return "User [First Name " + firstname + "Last Name" + surname + "]";
//    }
}
