package com.example.cogor.navigationdrawer;

/**
 * Created by cogor on 16/09/2017.
 */

public class User {

    private String name;
    private String email;
    private String date;
    private String gender;

    public User(String name, String email, String date, String gender)
    {
        this.name = name;
        this.email = email;
        this.date = date;
        this.gender = gender;
    }


    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getGender() {
        return gender;
    }
}
