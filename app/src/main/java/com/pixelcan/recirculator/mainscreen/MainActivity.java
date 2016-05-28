package com.pixelcan.recirculator.mainscreen;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pixelcan.inkpageindicator.InkPageIndicator;
import com.pixelcan.recirculator.R;
import com.pixelcan.recirculator.mainscreen.FragmentAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    FragmentAdapter mAdapter;
    public ViewPager mPager;
    InkPageIndicator mIndicator;
    Button buttonmodes;
    Button buttondatainfo;
    RadioGroup radiogroup1;
    String typeView = "modes";
    View.OnClickListener radioListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAdapter = new FragmentAdapter(getSupportFragmentManager(),typeView);
        mPager = (ViewPager) findViewById(R.id.pager);

        mIndicator = (InkPageIndicator) findViewById(R.id.indicator);
        mAdapter.typeView = "modes";
        mPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mPager);
        radiogroup1 = (RadioGroup) findViewById(R.id.radiogroup1);
        callAsynchronousTask();

        radioListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton)v;
                switch (rb.getId()) {
                    case R.id.modes:
                        buttonmodes.setTextColor(Color.parseColor("#FFFFFFFF"));
                        buttondatainfo.setTextColor(Color.parseColor("#0086ff"));
                        mAdapter.typeView = "modes";
                        mPager.setAdapter(mAdapter);
                        mIndicator.setViewPager(mPager);
                        break;
                    case R.id.infodata:
                        buttonmodes.setTextColor(Color.parseColor("#0086ff"));
                        buttondatainfo.setTextColor(Color.parseColor("#FFFFFFFF"));
                        mAdapter.typeView = "data";
                        mPager.setAdapter(mAdapter);
                        mIndicator.setViewPager(mPager);
                        break;
                    default:
                        break;
                }


            }
        };
        buttonmodes = (RadioButton) findViewById(R.id.modes);
        buttonmodes.setOnClickListener(radioListener);
        buttondatainfo = (RadioButton) findViewById(R.id.infodata);
        buttondatainfo.setOnClickListener(radioListener);
    }


    //Метод вызывающий ATupdateData по расписанию можно сделать паблик и передавать в атрибуты
    public void callAsynchronousTask() {

        // final String url = "https://doctorair.tk/commands/account_12QfBKI5wQ_1";
        final String url = "https://doctorair.tk/commands/account_info_12QfBKI5wQ";


        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            ATupdateData atUpdateData = new ATupdateData(url, mAdapter);
                            atUpdateData.execute();
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 1000); //execute in every 1 минута=60000
    }

public void mainsendcomand (int position){
    SendComand sendComand = new SendComand(position,mAdapter);
    sendComand.sendComandonServer();
}
}
