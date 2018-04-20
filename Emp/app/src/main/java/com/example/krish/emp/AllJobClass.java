package com.example.krish.emp;

/**
 * Created by krish on 17/4/18.
 */

public class AllJobClass  {
    private String name;
    private int hrly_rate;

    public  AllJobClass(String n,int r) {
        name = n;
        hrly_rate = r;
    }

    public String getName() {
        return name;
    }

    public int getHrly_rate() {
        return hrly_rate;
    }
}
