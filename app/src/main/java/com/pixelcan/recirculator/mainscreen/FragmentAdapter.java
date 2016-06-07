package com.pixelcan.recirculator.mainscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


class FragmentAdapter extends FragmentPagerAdapter {

    private int mCount = 2;//count page
    //private String [] infomass = {"1","1","1","1","1","1"};
    public String typeView;

    public FragmentAdapter(FragmentManager fm, String typeView) {
        super(fm);
    }

    FragmentInfo[] framentMass = new FragmentInfo[2];


    @Override
    public Fragment getItem(int position) {
        framentMass[position] = FragmentInfo.newInstance(position, typeView);
        return framentMass[position];
    }

    @Override
    public int getCount() {
        FragmentInfo.typeViewstatic = typeView;
        return mCount;
    }
}

