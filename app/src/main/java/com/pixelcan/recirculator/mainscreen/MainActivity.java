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

import static com.pixelcan.recirculator.R.color.white;

public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_UNSELECTED_COLOUR = "#0086ff";
    private static final String DEFAULT_SELECTED_COLOUR = "#FFFFFFFF";
    FragmentAdapter mAdapter;
    public ViewPager mPager;
    InkPageIndicator mIndicator;
    Button buttonmodes;
    Button buttondatainfo;
    RadioGroup radiogroup1;
    String typeView;
    View.OnClickListener radioListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new FragmentAdapter(getSupportFragmentManager(),typeView);
        mPager = (ViewPager) findViewById(R.id.pager);

        mIndicator = (InkPageIndicator) findViewById(R.id.indicator);
        changeFrame(R.string.modes);
        radiogroup1 = (RadioGroup) findViewById(R.id.radiogroup1);
        callAsynchronousTask();

        radioListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton)v;
                switch (rb.getId()) {
                    case R.id.modes:
                        buttonmodes.setTextColor(Color.parseColor(DEFAULT_SELECTED_COLOUR));
                        buttondatainfo.setTextColor(Color.parseColor(DEFAULT_UNSELECTED_COLOUR));
                        changeFrame(R.string.modes);
                        break;
                    case R.id.infodata:
                        buttonmodes.setTextColor(Color.parseColor(DEFAULT_UNSELECTED_COLOUR));
                        buttondatainfo.setTextColor(Color.parseColor(DEFAULT_SELECTED_COLOUR));
                        changeFrame(R.string.data);
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

    private void changeFrame(int id)
    {
        mAdapter.typeView = getString(id);
        mPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mPager);
    }
    //Метод вызывающий ATupdateData по расписанию можно сделать паблик и передавать в атрибуты
    public void callAsynchronousTask() {

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
