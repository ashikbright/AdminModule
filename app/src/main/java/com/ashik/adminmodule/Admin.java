package com.ashik.adminmodule;

public class Admin {
    public String name,workertype, email, phone, isUser;

    public Admin() {
    }

    public Admin(String name, String workertype,String email, String phone, String isUser) {
        this.name = name;
        this.workertype=workertype;
        this.email = email;
        this.phone = phone;
        this.isUser = isUser;
    }


    public Admin(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getWorkertype() {
        return workertype;
    }

    public void setWorkertype(String workertype) {
        this.workertype = workertype;
    }

    public String getIsUser() {
        return isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String[] GenerateData(String name,String workertype, String email, String phone){
        String[] data = {
                name, workertype,email, phone, "", ""
        };


        return data;
    }

}
