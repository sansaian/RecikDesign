//package com.pixelcan.recirculator.mainscreen.connect;
//
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * Класс для обработки JSON обьектов в массив строковых переменных(то что мы выводим на экран)
// */
//public class ParserJson {
//
//    static final String forLogError = "MyError:ParserJson";
//    List<String> dataFromSensors = new ArrayList();
//
//
//    public List<String> getInfo(JSONObject dataJsonObj,int comand) throws JSONException {
//
//        try {
//            JSONArray events = dataJsonObj.getJSONArray("info");
//
//            for (int i = 0; i < events.length(); i++) {
//                JSONObject jsonEvent = events.getJSONObject(i);
//                Iterator<String> iter = jsonEvent.keys();
//                while (iter.hasNext()) {
//
//                }
//            }
//        } catch (Exception e) {
//            Log.d(forLogError, "Ошибка не верный JSON ");
//        }
//        return dataFromSensors;
//    }
//
//
//    public void getDataFromSensors(JSONObject jsonEvent,Iterator<String> iter) throws JSONException {
//
//
//                    String key = iter.next();
//                    switch (key) {
//                        case "co2":
//                            dataFromSensors.add(jsonEvent.getString("co2"));
//                            break;
//                        case "co":
//                            dataFromSensors.add(jsonEvent.getString("co"));
//                            break;
//                        case "on":
//                            dataFromSensors.add(jsonEvent.getString("on"));
//                            break;
//                        case "temperature":
//                            dataFromSensors.add(jsonEvent.getString("temperature"));
//                            break;
//                        case "pressure":
//                            dataFromSensors.add(jsonEvent.getString("pressure"));
//                            break;
//                        case "humidity":
//                            dataFromSensors.add(jsonEvent.getString("humidity"));
//                            break;
//                        case "lamp":
//                            dataFromSensors.add(jsonEvent.getString("lamp"));
//                            break;
//                        case "cooler":
//                            dataFromSensors.add(jsonEvent.getString("cooler"));
//                            break;
//                        case "mode":
//                            dataFromSensors.add(jsonEvent.getString("mode"));
//                            break;
//
//                    }
//
//    }
//
//
//}
