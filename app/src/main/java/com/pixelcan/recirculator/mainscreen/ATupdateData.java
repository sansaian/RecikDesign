package com.pixelcan.recirculator.mainscreen;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.pixelcan.recirculator.mainscreen.untils.ParserJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

//обновление данных с устройства
public class ATupdateData extends AsyncTask<Void, Void, String> {
    JSONObject dataJsonObj;
    private String urlString;
    String[] dataFromSensors = new String[9];
    FragmentAdapter fragmentAdapter;
    AppCompatActivity activity;
    ParserJSON parser = new ParserJSON();

    public ATupdateData(AppCompatActivity activity, FragmentAdapter fragmentAdapter) {
        this.fragmentAdapter = fragmentAdapter;
        this.activity = activity;
    }

    public ATupdateData(String urlString, FragmentAdapter fragmentAdapter) {
        this.urlString = urlString;
        this.fragmentAdapter = fragmentAdapter;
    }


    protected void onPreExecute() {
        super.onPreExecute();
        //даем знать что данные не загруженны
    }

    @Override
    protected String doInBackground(Void... params) {
        //   jsonhave = 0;
        try {
            parser.getDataFromSensor(getJsonObject());
        } catch (JSONException e) {
            Log.d("MyLog", "проблема распарсить JSON ");
            //    jsonhave = 1;
        }

        return null;
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //fragmentAdapter.setInfomassAdapter(dataFromSensors);
        if (fragmentAdapter.framentMass[0] != null) {
            fragmentAdapter.framentMass[0].fullTextviewData(dataFromSensors);
            Log.d("MyLog1", "dataFromSensors[2] " + dataFromSensors[2]);
            fragmentAdapter.framentMass[0].stateModesMass(dataFromSensors[2], dataFromSensors[8]);
            fragmentAdapter.framentMass[1].fullTextviewData(dataFromSensors);
            fragmentAdapter.framentMass[1].stateModesMass(dataFromSensors[2], dataFromSensors[8]);
        }

        if(((MainActivity) activity).getIdDevice()!=null){
            Toast toast = Toast.makeText(activity,
                    "У вас нет подключенных устройств",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
        }
            }

    private URL buildURL() {
        String idDevice = ((MainActivity) this.activity).getIdDevice();
        URL url = null;
        this.urlString = "https://doctorair.tk/commands/account_info_" + idDevice;
        try {
            url = new URL(this.urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d("MyLog", "адрес " + this.urlString);
        return url;
    }

    //метод который коннектится к серваку и выдает json как JsonObject
    private JSONObject getJsonObject() {
        String resultJson = "";
        //формирование url
        //можно закинуть весь конект в отдельный метод это будет по фэншую
        do {
//формирование URl
            HttpURLConnection urlConnection = null;

            try {

                urlConnection = (HttpURLConnection) buildURL().openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                Log.d("MyLog", "connect " + urlConnection);
                InputStream inputStream = urlConnection.getInputStream();
                resultJson = readInputStream(inputStream);
                Log.d("MyLog", "ответ сервера JSON " + resultJson);
                dataJsonObj = new JSONObject(resultJson);
            } catch (IOException e) {
                Log.d("MyLog", "проблемы с сервером");
                //   jsonhave = 1;
                sleep();
            } catch (JSONException e) {
                Log.d("MyLog", "не удалось получить объект JSON");
                //    jsonhave = 1;
                sleep();
            }
            ///сообщить пользователю  том что проблемы с сервером
            // может стоит сделать определенное количество повторений потом сообщать что пиздец
            //проблема когда ушли в сон он не останавливается.
        }
        while (resultJson.isEmpty());
        //   jsonhave = 0;
        return dataJsonObj;
    }

    //кладет в сон поток
    void sleep() {
        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //считать поток вернуть
    private String readInputStream(InputStream inputStream) {
        String resultreadInputStream = "";
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultreadInputStream = buffer.toString();
        return resultreadInputStream;
    }

//    private String[] parsJson(JSONObject dataJsonObj) throws JSONException {
//
//        try {
//            JSONArray events = dataJsonObj.getJSONArray("info");
//
//            Log.d("MyLog", "not error in parsJson1 ");
//            for (int i = 0; i < events.length(); i++) {
//                JSONObject jsonEvent = events.getJSONObject(i);
//                String co = "", co2 = "", onoff = "", temperature = "", humidity = "", pressure = "", lamp = "", cooler = "", mode = "";
//                Iterator<String> iter = jsonEvent.keys();
//                while (iter.hasNext()) {
//                    String key = iter.next();
//                    switch (key) {
//                        case "co2":
//                            co2 = jsonEvent.getString("co2");
//                            break;
//
//                        case "co":
//                            co = jsonEvent.getString("co");
//                            break;
//                        case "on":
//                            onoff = jsonEvent.getString("on");
//                            break;
//                        case "temperature":
//                            temperature = jsonEvent.getString("temperature");
//                            break;
//                        case "pressure":
//                            pressure = jsonEvent.getString("pressure");
//                            break;
//                        case "humidity":
//                            humidity = jsonEvent.getString("humidity");
//                            break;
//                        case "lamp":
//                            lamp = jsonEvent.getString("lamp");
//                            break;
//                        case "cooler":
//                            cooler = jsonEvent.getString("cooler");
//                            break;
//                        case "mode":
//                            mode = jsonEvent.getString("mode");
//                            break;
//
//                    }
//                    //можно сократить напрямую в массив записывать без беспантовых переменных
//                    dataFromSensors[0] = co2;
//                    dataFromSensors[1] = co;
//                    dataFromSensors[2] = onoff;
//                    dataFromSensors[3] = temperature;
//                    dataFromSensors[4] = pressure;
//                    dataFromSensors[5] = humidity;
//                    dataFromSensors[6] = lamp;
//                    dataFromSensors[7] = cooler;
//                    dataFromSensors[8] = mode;
//                }
//            }
//        } catch (Exception e) {
//
//            //  jsonhave = 2;
//            Log.d("MyLog", "ошибка не верный json ");
//        }
//        return dataFromSensors;
//    }

}

