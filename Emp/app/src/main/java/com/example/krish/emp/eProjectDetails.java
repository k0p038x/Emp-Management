package com.example.krish.emp;

/**
 * Created by krish on 10/4/18.
 */

public class eProjectDetails {
    private String id;
    private int hours;
    private String name;
    private int rate;
    private boolean isLead;
    eProjectDetails(String id,String name,int hrs,boolean isLead,int rate) {
        this.id=id;
        this.name=name;
        hours=hrs;
        this.isLead=isLead;
        this.rate=rate;
    }

    public int getRate() {
        return rate;
    }
    public int getHours() {
        return hours;
    }

    public String getpId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public boolean getLead() {
        return isLead;
    }
}
