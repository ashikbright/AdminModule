package com.ashik.adminmodule;

public class Workers {
    public String name;
    public String workertype;
    public String email;
    public String phone;
    public String isWorker;
    public String address;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getIsWorker() {
        return isWorker;
    }

    public String getAddress() {
        return address;
    }

    public String getWorkertype() {
        return workertype;
    }

    public Workers(){

    }

    public Workers(String name, String workertype,String email, String phone,String isWorker,String address) {
        this.name = name;
        this.workertype = workertype;
        this.email = email;
        this.phone = phone;
        this.isWorker=isWorker;
        this.address=address;
    }

    public Workers(String name, String email, String phone,String isWorker,String address) {
        this.name = name;
        // this.workertype = workertype;
        this.email = email;
        this.phone = phone;
        this.isWorker=isWorker;
        this.address=address;
    }

    public Workers(String name,String email, String phone) {
        this.name = name;
        //this.workertype = workertype;
        this.email = email;
        this.phone = phone;
    }

}
