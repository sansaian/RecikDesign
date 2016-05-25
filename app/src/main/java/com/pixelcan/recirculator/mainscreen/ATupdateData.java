package com.pixelcan.recirculator.mainscreen;


import android.os.AsyncTask;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.TextView;
import android.widget.ToggleButton;

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
    /*  private TextView textViewShowHumidity;
      private TextView serverErrorText;
      private TextView textViewShowTemper;
      private TextView textViewShowCO;
      private TextView textViewShowCO2;
      private TextView textViewPressure;
      private ToggleButton onOffButton;*/
    // private int jsonhave = 0;
    private String urlString;
    String[] dataFromSensors = new String[6];
    FragmentAdapter fragmentAdapter;

    public ATupdateData(String urlString, FragmentAdapter fragmentAdapter) {

     /* this.textViewShowCO = textViewShowCO;
        this.textViewShowCO2 = textViewShowCO2;
        this.textViewPressure = textViewPressure;
        this.textViewShowTemper = textViewShowTemper;
        this.textViewShowHumidity = textViewShowHumidity;
        this.onOffButton = onOffButton;*/
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
            parsJson(getJsonObject());
        } catch (JSONException e) {
            Log.d("MyLog", "проблема распарсить JSON ");
            //    jsonhave = 1;
        }

        return null;
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        fragmentAdapter.framentMass[0].fullTextviewData(dataFromSensors);
        fragmentAdapter.framentMass[1].fullTextviewData(dataFromSensors);
        // fragmentAdapter.setInfomassAdapter(dataFromSensors);

        //      Log.d("MyLog", "jsonhave1 " + jsonhave);
      /*  switch (jsonhave) {
            case 0: {
                textViewShowCO2.setText(dataFromSensors[0]);
                textViewShowCO.setText(dataFromSensors[1]);
                textViewShowTemper.setText(dataFromSensors[3]);
                textViewShowHumidity.setText(dataFromSensors[5]);
                textViewPressure.setText(dataFromSensors[4]);
                onOffButton.setChecked(Boolean.valueOf(dataFromSensors[2]));
                break;
            }
            case 1: {
                textViewShowCO2.setText("0");
                textViewShowCO.setText("0");
                textViewShowTemper.setText("0");
                textViewShowHumidity.setText("0");
                textViewPressure.setText("0");
                //вывод сообщения
                //serverErrorText.setText("Ошибка1 подключения");
                //serverErrorText.setVisibility(View.VISIBLE);
                break;
            }

        }
*/

    }


    //метод который коннектится к серваку и выдает json как JsonObject
    private JSONObject getJsonObject() {
        String resultJson = "";
        //можно закинуть весь конект в отдельный метод это будет по фэншую
        do {
            URL url = null;
            try {
                url = new URL(this.urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.d("MyLog", "адрес " + this.urlString);
            HttpURLConnection urlConnection = null;

            try {

                urlConnection = (HttpURLConnection) url.openConnection();
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

    private String[] parsJson(JSONObject dataJsonObj) throws JSONException {

        try {
            JSONArray events = dataJsonObj.getJSONArray("info");

            Log.d("MyLog", "not error in parsJson1 ");
            for (int i = 0; i < events.length(); i++) {
                JSONObject jsonEvent = events.getJSONObject(i);
                String co = "", co2 = "", onoff = "", temperature = "", humidity = "", pressure = "";
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

                    }
                    //можно сократить напрямую в массив записывать без беспантовых переменных
                    dataFromSensors[0] = co2;
                    dataFromSensors[1] = co;
                    dataFromSensors[2] = onoff;
                    dataFromSensors[3] = temperature;
                    dataFromSensors[4] = pressure;
                    dataFromSensors[5] = humidity;
                    //    jsonhave = 0;
                }
            }
        } catch (Exception e) {

            //  jsonhave = 2;
            Log.d("MyLog", "ошибка не верный json ");
        }
        return dataFromSensors;
    }

}

