package com.pixelcan.recirculator.mainscreen.connect;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pixelcan.recirculator.mainscreen.autorization.RegistrationActivity;
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

/**
 * Класс для подключения к серверу.ОДин экземляр этого класса вызывается для регистрации пользователя
 * Другой для авторизации.Третии для регулятрного запроса данных с датчиков от сервера в реальном времени.
 * Взависимости от того какую задачу мы хотим вызвать,мы передаем соответствующий параметр.
 * параметр String params[0]:
 * 1---Регистрируем пользователя
 * 2---Авторизуем пользователя
 * 3---Запрос данных с датчиков и текущее состояние рециркулятора
 * 4--- отправка команд на сервер
 * <p/>
 * На вход класс принимает разное число параметров String ... params
 * всегда передается параметр соответствующий команде(см. выше)
 * Далее если мы регистрируем пользователя мы передаем в этот класс данные заполненные при регисттрации
 * <p/>
 * <p/>
 * возвращает токен в класс который вызвал ConnectorServer или код null(если возникла ошибка) возможно стоит возвращать код ошибки
 */
public class ConnenctorServer extends AsyncTask<String, Void, String> {

    static final String forLogError = "MyError:ConnectorServer";
    static final String forLogTrace = "Trace:ConnectorServer";
    /**
     * progress dialog to show user that the backup is processing.
     */
    private ProgressDialog dialog;
    /**
     * application context.ProgressDialog need activity and context
     */
    private RegistrationActivity activity;
    private Context context;
    private int comand;
    private String login;
    private String firstname;
    private String secondname;
    private String password;



    public ConnenctorServer(RegistrationActivity activity, int comand) {
        this.activity = activity;
        context = activity;
        dialog = new ProgressDialog(context);
        this.comand = comand;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Соединение с сервером...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
    }


    @Override
    protected String doInBackground(String[] params) {

        this.login = params[0];
        this.firstname = params[1];
        this.secondname = params[2];
        this.password = params[3];
      //  String s = params[0];
        getUrl(comand);
        /*
        сделаем отдельный класс ParJson В пакете Untils
         */
        ParserJSON parserJson = new ParserJSON();
        try {
            parserJson.getDatafterRegistration( getJsonObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param jsonObject
     */


    /**
     * @param comand Взависимости от того какую задачу мы хотим вызвать,мы передаем соответствующий параметр.
     *               параметр :
     *               1---Регистрируем пользователя
     *               2---Авторизуем пользователя
     *               3---Запрос данных с датчиков и текущее состояние рециркулятора
     *               4---Отправить команду рециркулятору
     * @return возвращает необходимый Url как класс
     */
    private URL getUrl(int comand) {
       // Log.d(forLogError,"comand" +comand);
        String urlString = "";
        switch (comand) {
            case 1://get-запрос для регистрации
                urlString ="https://doctorair.tk/addnewuser/"+this.login+"_"+this.firstname+"_"+this.secondname
                        +"_"+secondname+"_"+this.password;
                Log.d(forLogTrace, "Запрос на регистрацию"+urlString);
                break;
            case 2:// get-запрос для авторизации
                urlString = "https://doctorair.tk/getlistdevice/"+this.login+"_"+this.password;
                //!!!!для примера!!!!!!
                urlString = null;
                break;
            case 3: //get-запрос для считывания инфы с датчиков
                urlString = "https://doctorair.tk/commands/account_info_12QfBKI5wQ";
                break;
            default:
                Log.d(forLogError, "неверная команда");
                break;
        }

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(forLogError, "Не получется создать обьект URL");
        }
        return url;
    }

    /**
     * Метод создает HttpURLConnection и пробует подключиться к серверу 6 раз с перерывом в несколько секунд.После чего сообщает что это ему не удалось
     *
     * @return - возвращает объект JSON от сервера
     */
    private JSONObject getJsonObject() {
        String resultJson = "";
        JSONObject dataJsonObj = null;
        int repetitionCount = 0; //счетчик для подсчета неудачных попыток подключения к серверу
        do {
            getUrl(this.comand);
            HttpURLConnection urlConnection = null;

            try {

                urlConnection = (HttpURLConnection) getUrl(this.comand).openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                ///////////////////////////////////////////////////
                dataJsonObj = new JSONObject(readInputStream(inputStream));
            } catch (IOException e) {
                Log.d(forLogError, "проблемы с сервером");
                //   jsonhave = 1;
                sleep();
            } catch (JSONException e) {
                Log.d("forLogError", "не удалось получить объект JSON");
                //    jsonhave = 1;
                sleep();
            }
            ///сообщить пользователю  том что проблемы с сервером
            // может стоит сделать определенное количество повторений потом сообщать что пиздец
            //проблема когда ушли в сон он не останавливается.
        }
        while (resultJson.isEmpty() && repetitionCount > 6);
        //   jsonhave = 0;
        Log.d(forLogTrace,"there"+resultJson);
        return dataJsonObj;
    }

    /**
     * @param inputStream -- входящий поток от сервера
     * @return возвращает строку полученную от сервера
     */
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

    /**
     * останавливает поток на 6 секунд
     */
    void sleep() {
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        activity.goToMainActivity();
        activity.saveText(this.login,this.password);
        String [] mass = activity.loadText();
        super.onPostExecute(s);
    }
}
