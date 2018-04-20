package com.example.krish.emp;

/**
 * Created by krish on 9/4/18.
 */

public class EmployeeData {
    private String ename,eid,eclass;

    public EmployeeData(String name,String id,String cla) {
        ename=name;
        eid=id;
        eclass=cla;
    }

    public String getEclass() {
        return eclass;
    }

    public String getEid() {
        return eid;
    }

    public String getEname() {
        return ename;
    }
}
