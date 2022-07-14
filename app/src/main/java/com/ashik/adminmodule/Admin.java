package com.ashik.adminmodule;

public class Admin {
    public String name, email, phone, isAdmin;

    public Admin() {
    }

    public Admin(String name,String email, String phone, String isAdmin) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isAdmin = isAdmin;
    }


    public Admin(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


}
