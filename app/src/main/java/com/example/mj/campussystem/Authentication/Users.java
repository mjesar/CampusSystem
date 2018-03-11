package com.example.mj.campussystem.Authentication;

/**
 * Created by MJ on 3/6/2018.
 */

public class Users {
    String name;
    String email;
    String user_id;


    String nameJob;
    String experience;

    String catogety;
    public Users(String name, String email, String user_id,String catogery) {
        this.name = name;
        this.email= email;
        this.user_id= user_id;
        this.catogety=catogery;


    }

    public Users(){

    }

    public Users(String nameJob, String experience) {
    this.nameJob=nameJob;
    this.experience= experience;

    }


    public String getName() {
        return name;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getCatogety() {
        return catogety;
    }

    public void setCatogety(String catogety) {
        this.catogety = catogety;
    }


    public String getNameJob() {
        return nameJob;
    }

    public void setNameJob(String nameJob) {
        this.nameJob = nameJob;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

}
