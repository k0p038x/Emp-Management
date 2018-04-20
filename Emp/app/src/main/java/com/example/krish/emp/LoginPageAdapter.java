package com.example.krish.emp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by krish on 9/4/18.
 */

public class LoginPageAdapter extends FragmentPagerAdapter {

    int numOfTabs;
    public LoginPageAdapter(FragmentManager fm,int count) {
        super(fm);
        numOfTabs=count;
    }

    public int getNumOfTabs() {
        return numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new loginFragment();
            case 1:
                return new eRegisterFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "LOGIN";
            case 1:
                return "REGISTER";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
