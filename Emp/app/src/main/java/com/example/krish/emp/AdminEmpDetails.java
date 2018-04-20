package com.example.krish.emp;

/**
 * Created by krish on 17/4/18.
 */

public class AdminEmpDetails {

    private String name;
    private String id;
    private String jobClass;


    public AdminEmpDetails(String id,String name,String jobClass) {
        this.name=name;
        this.id=id;
        this.jobClass=jobClass;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public void setName(String name) {
        this.name = name;
    }
}
