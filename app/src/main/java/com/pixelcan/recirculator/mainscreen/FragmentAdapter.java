package com.pixelcan.recirculator.mainscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


class FragmentAdapter extends FragmentPagerAdapter {

    private int mCount = 2;//count page
    //private String [] infomass = {"1","1","1","1","1","1"};
    private String typeView;

    public FragmentAdapter(FragmentManager fm, String typeView) {
        super(fm);
    }

    FragmentInfo[] framentMass = new FragmentInfo[2];

    public String getTypeView() {
        return typeView;
    }
    public void setTypeView(String idScreenFrame )
    {
        this.typeView=idScreenFrame;
    }

    @Override
    public Fragment getItem(int position) {
        framentMass[position] = FragmentInfo.newInstance(position, typeView);
        return framentMass[position];
    }

    @Override
    public int getCount() {
        FragmentInfo.typeViewstatic = typeView;
        //if(framentMass[2]!=null)framentMass[1].setfullstatemode();
        return mCount;
    }
}

