package com.pixelcan.recirculator.mainscreen.autorization;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.pixelcan.recirculator.R;
import com.pixelcan.recirculator.mainscreen.MainActivity;

public class RegistrationActivity extends AppCompatActivity {


    SharedPreferences sPref;
    FragmentAutorization frgmAutorization;//фрагмент для авторизации
    FragmentRegistration frgmRegistration;//фрагмент для регистрации
    FragmentTransaction fTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //устанавливаем сначала фрагмент авторизации
        addFragment();

    }

    /**
     * Добавляет фрагмент в активити и в backStack
     * Доступен для других классов этого пакета
     */
    protected void addFragment() {
        frgmAutorization = new FragmentAutorization();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmContentRegistration, frgmAutorization, "autorization");
        fTrans.addToBackStack("AutorizationFrame");
        fTrans.commit();
    }

    protected void replaceFragment() {
        frgmRegistration = new FragmentRegistration();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.frgmContentRegistration, frgmRegistration, "registration");
        fTrans.addToBackStack("RegistrationFrame");
        fTrans.commit();
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveText(String login, String password) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("login", login);
        ed.putString("password", password);
        ed.commit();
    }
    public String [] loadText() {
        String[] massidentif = new String [2];
        sPref = getPreferences(MODE_PRIVATE);
         massidentif[0]  = sPref.getString("login", "");
        massidentif[1]  = sPref.getString("password", "");
        Toast.makeText(this, "Text loaded"+massidentif[0]+"////"+massidentif[1], Toast.LENGTH_SHORT).show();
        return massidentif;
    }
}
