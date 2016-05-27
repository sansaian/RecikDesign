package com.pixelcan.recirculator.mainscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


class FragmentAdapter extends FragmentPagerAdapter {
    //protected static final String[] CONTENT = new String[]{"This", "Is", "A",};

    private int mCount = 2;//count page
    private String [] infomass = {"1","1","1","1","1","1"};
    public String typeView;
    public FragmentAdapter(FragmentManager fm,String typeView) {
        super( fm);
    }
    FragmentInfo [] framentMass = new FragmentInfo[2];
    //FragmentInfo fragment;


    @Override
    public Fragment getItem(int position) {
        Log.d("Mylog", "getItem " + this.typeView);
        framentMass[position] =FragmentInfo.newInstance(position,typeView);
        framentMass[position].setInfomass(infomass);
        return framentMass[position];
    }

    @Override
    public int getCount() {
        //Log.d("Mylog", "getCount " + this.typeView);
        FragmentInfo.typeViewstatic = typeView;
       // Log.d("Mylog", "getCount typeViewstatic " + this.typeView);
        return mCount;
    }

    public void setInfomassAdapter(String[] massfromAtData){

      this.infomass =  massfromAtData;

    }
 }

