package com.ashik.adminmodule;

public class User {
    public String name, email, phone, isAdmin;

    public User() {
    }

    public User(String name, String email, String phone, String isAdmin) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isAdmin = isAdmin;
    }


    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


}