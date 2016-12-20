package com.pixelcan.recirculator.mainscreen.untils;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 *
 */

public class ParserJSON {


    static final String forLogError = "MyError:ParserJSON";
    static final String forLogTrace = "Trace:ParserJSON";

    /*
    Нужно возвращать не массив а hashmap так как добавят один показатель и все переделывать придется
     */
    public String[] getDataFromSensor(JSONObject dataJsonObj) throws JSONException {
        String[] dataFromSensors = new String[8];
        try {

            JSONArray events = dataJsonObj.getJSONArray("info");

            Log.d("MyLog", "not error in parsJson1 ");
            for (int i = 0; i < events.length(); i++) {
                JSONObject jsonEvent = events.getJSONObject(i);
                String co = "", co2 = "", onoff = "", temperature = "", humidity = "", pressure = "", lamp = "", cooler = "", mode = "";
                Iterator<String> iter = jsonEvent.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    switch (key) {
                        case "co2":
                            co2 = jsonEvent.getString("co2");
                            break;

                        case "co":
                            co = jsonEvent.getString("co");
                            break;
                        case "on":
                            onoff = jsonEvent.getString("on");
                            break;
                        case "temperature":
                            temperature = jsonEvent.getString("temperature");
                            break;
                        case "pressure":
                            pressure = jsonEvent.getString("pressure");
                            break;
                        case "humidity":
                            humidity = jsonEvent.getString("humidity");
                            break;
                        case "lamp":
                            lamp = jsonEvent.getString("lamp");
                            break;
                        case "cooler":
                            cooler = jsonEvent.getString("cooler");
                            break;
                        case "mode":
                            mode = jsonEvent.getString("mode");
                            break;

                    }
                    //можно сократить напрямую в массив записывать без беспантовых переменных
                    dataFromSensors[0] = co2;
                    dataFromSensors[1] = co;
                    dataFromSensors[2] = onoff;
                    dataFromSensors[3] = temperature;
                    dataFromSensors[4] = pressure;
                    dataFromSensors[5] = humidity;
                    dataFromSensors[6] = lamp;
                    dataFromSensors[7] = cooler;
                    dataFromSensors[8] = mode;
                }
            }
            Log.d(forLogTrace, dataFromSensors[0]);
        } catch (Exception e) {

            //  jsonhave = 2;
            Log.d(forLogError, "ошибка не верный json ");
        }
        return dataFromSensors;
    }

    /**
     * ПРОВЕРИТЬ ЛОГИКУ НИ КАК НЕ ОБРАБАТЫВАЮТСЯ ОШИБКИ
     * @param dataJsonObj
     * @return
     * @throws JSONException
     */
    public String getDatafterRegistration(JSONObject dataJsonObj) throws JSONException {
        String errorJson = "";
        if (dataJsonObj != null) {
            try {
                errorJson = dataJsonObj.get("error").toString();
                Log.d(forLogTrace, "JSON после регистрации" + errorJson);
            } catch (JSONException e) {
                Log.d(forLogError, "ошибка не верный json ");
            }

        } else {
            Log.d(forLogError, "пустой json ");
            /////вывести сообщение пользователю
        }

        return errorJson;
    }

    public boolean getDataforAutorization(JSONObject jsonObject) {
        //проверка прошел ли процедуру регистрации
        boolean rezult = false;
        if (jsonObject != null) {
            try {
                rezult = !jsonObject.get("exception").toString().contains("UserAccount matching query does not exist");
                Log.d(forLogTrace, "JSON после регистрации" + jsonObject.get("exception").toString());
            } catch (JSONException e) {
                Log.d(forLogError, "ошибка не верный json ");
            }

        } else {
            Log.d(forLogError, "пустой json ");
            /////вывести сообщение пользователю
        }
        return rezult;
    }


    public String getParseIdDevice(JSONObject jsonObject) {
        String idDevice=null;
        //логика как парсить.Нужно что бы Макс сервер доделал
        return idDevice;
    }
}

