package com.example.krish.emp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by krish on 17/4/18.
 */

public class AdminPageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    public AdminPageAdapter(FragmentManager fm, int num) {
        super(fm);
        numOfTabs=num;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0 :
                return new allEmployeeFragment();
            case 1:
                return new allJobClassFragment();
            case 2:
                return new allProjectsFragment();
            default:
                return null;
        }
    }
}
