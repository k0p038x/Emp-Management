package com.example.krish.emp;

import android.renderscript.ScriptGroup;

/**
 * Created by krish on 14/4/18.
 */

public class InProjectDetails {
    private String id;

    private int hrs;
    private float rate;

    public InProjectDetails(String id,int h,float r) {
        this.id=id;
        hrs=h;
        rate=r;

    }




    public float getRate() {
        return rate;
    }

    public int getHrs() {
        return hrs;
    }

    public String getId() {
        return id;
    }

    public void setHrs(int hrs) {
        this.hrs = hrs;
    }


}
