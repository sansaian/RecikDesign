package com.pixelcan.recirculator.mainscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


class FragmentAdapter extends FragmentPagerAdapter {
    //protected static final String[] CONTENT = new String[]{"This", "Is", "A",};

    private int mCount = 2;//count page
    private String [] infomass = {"1","1","1","1","1","1"};
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    FragmentInfo [] framentMass = new FragmentInfo[2];
    //FragmentInfo fragment;


    @Override
    public Fragment getItem(int position) {
        Log.d("1111", "asdasdad " + position);
        framentMass[position] =FragmentInfo.newInstance(position);
        framentMass[position].setInfomass(infomass);
        return framentMass[position];
    }

    @Override
    public int getCount() {
        return mCount;
    }

    public void setInfomassAdapter(String[] massfromAtData){

      this.infomass =  massfromAtData;

    }
 }

