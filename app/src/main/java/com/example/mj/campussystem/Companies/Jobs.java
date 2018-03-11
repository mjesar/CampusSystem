package com.example.mj.campussystem.Companies;

/**
 * Created by MJ on 3/9/2018.
 */

public class Jobs {



    String nameJob;
    String experience;
    String user_id;
    String job;

    public Jobs(String nameJob, String experince, String user_id, String job) {

        this.nameJob=nameJob;
        this.experience=experince;
        this.user_id=user_id;
        this.job= job;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

}

