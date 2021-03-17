package com.example.hospitalmanagment.Model;

public class User {
    String code;
    String name;
    String MobileNumber;
    String email;
    String Password;
    public User(String code, String name, String mobileNumber, String email, String password) {
        this.code = code;
        this.name = name;
        MobileNumber = mobileNumber;
        this.email = email;
        Password = password;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


}
