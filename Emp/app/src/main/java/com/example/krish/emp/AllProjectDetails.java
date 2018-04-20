package com.example.krish.emp;

import java.util.ArrayList;

/**
 * Created by krish on 17/4/18.
 */

public class AllProjectDetails  {
    private String id;
    private String name;
    private String lead;

    public AllProjectDetails(String id,String n,String l) {
        this.id = id;
        this.lead=l;
        this.name=n;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getLead() {
        return lead;
    }
}
