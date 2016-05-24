package com.pixelcan.ipidemo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pixelcan.inkpageindicator.InkPageIndicator;

public class MainActivity extends AppCompatActivity {

    TestFragmentAdapter mAdapter;
  public  ViewPager mPager;
    InkPageIndicator mIndicator;
    ControllforData controllforData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new TestFragmentAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        controllforData = new ControllforData();
        mIndicator = (InkPageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }
}
