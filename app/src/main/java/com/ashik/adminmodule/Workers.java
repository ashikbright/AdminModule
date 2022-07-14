package com.ashik.adminmodule;

public class Workers {
    public String name;
   // public String workertype;
    public String email;
    public String phone;
    public String isWorker;

    public Workers(){

    }

    public Workers(String name, String email, String phone,String isWorker) {
        this.name = name;
       // this.workertype = workertype;
        this.email = email;
        this.phone = phone;
        this.isWorker=isWorker;
    }
    public Workers(String name,String email, String phone) {
        this.name = name;
        //this.workertype = workertype;
        this.email = email;
        this.phone = phone;
    }

}
