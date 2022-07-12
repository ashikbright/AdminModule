package com.ashik.adminmodule;

public class User {
    String name;
    String workertype;
    String email;
    String phone;
public  User(){

}
    public User(String name, String workertype, String email, String phone) {
        this.name = name;
        this.workertype = workertype;
        this.email = email;
        this.phone = phone;
    }

    public User(String name, String workertype, String email) {
        this.name = name;
        this.workertype = workertype;
        this.email = email;
    }

    public User(String name, String workertype) {
        this.name = name;
        this.workertype = workertype;
    }
}
