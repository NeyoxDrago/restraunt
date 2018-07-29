package com.example.nirnaymittal.restraunt;

/**
 * Created by Nirnay Mittal on 28-07-2018.
 */

public class Data {


    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return Address;
    }

    public String getRestau_name() {
        return restau_name;
    }

    public int getRequestby() {
        return requestby;
    }

    private String first_name;
    private String last_name;
    private String phone;
    private String Address;
    private String restau_name;
    private int requestby;


    ///////////////////////////////////////



    private String username;
    private String password;

    public Data(String username, String password) {
        this.username = username;
        this.password = password;
    }



    ///////////////////////////////////////

    public Data(String first_name, String last_name, String phone, String address, String restau_name, int requestby) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        Address = address;
        this.restau_name = restau_name;
        this.requestby = requestby;
    }


}
