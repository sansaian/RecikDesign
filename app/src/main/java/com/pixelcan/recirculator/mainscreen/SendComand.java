package com.pixelcan.recirculator.mainscreen;


import android.util.Log;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by Maxim on 05.03.2016.
 */
public class SendComand {

    int idposition;
    FragmentAdapter madapter;

    public SendComand(int idposition, FragmentAdapter madapter) {
        this.idposition = idposition;
        this.madapter = madapter;
    }
    //формирует URL
    private String getURLString() {

        String switchonof;
        if (idposition != 0) {
            switchonof = "1";
        } else {
            switchonof = "0";
            idposition++;
        }

        String url = "https://doctorair.tk/commands/account_request_12QfBKI5wQ_{\"on\":" + switchonof + ",\"mode\":" + idposition + ",\"mode_param\":\"\"}";
        Log.d("MyLog", "switchonof " + switchonof);
        return url;
    }
//вызывет ATdateData и передает свой url
    public void sendComandonServer() {

        ATupdateData atUpdateData = new ATupdateData(getURLString(), madapter);
        atUpdateData.execute();
    }

}

