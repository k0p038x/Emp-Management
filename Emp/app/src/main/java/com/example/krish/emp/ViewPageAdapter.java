package com.example.krish.emp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by krish on 8/4/18.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

    public int numberOfTabs;
    public ViewPageAdapter(FragmentManager fm,int num) {
       super(fm);
       numberOfTabs=num;
   }



   public int getCount() {
       return numberOfTabs;
   }

   /*public String getPageTitle(int pos) {
       switch (pos) {
           case 0:
               return "Details";
           case 1:
               return "Projects";
           default:
               return null;
       }
   } */
   public Fragment getItem(int pos) {
        switch (pos) {
            case 0 :
                return new eDetailsFragment();
            case 1:
                return new eProjectsFragment();
            default:
                return null;
        }
   }


}
