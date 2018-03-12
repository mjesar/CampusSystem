package com.example.mj.campussystem;

/**
 * Created by MJ on 3/11/2018.
 */

public class Jobs {

    String nameJob;
    String company;
    String student_name;



    public Jobs(String nameJob, String company, String student_name) {
        this.nameJob=nameJob;
        this.company= company;
        this.student_name= student_name;

    }


    public Jobs(){

    }
    public String getNameJob() {
        return nameJob;
    }

    public void setNameJob(String nameJob) {
        this.nameJob = nameJob;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;

    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }
}
