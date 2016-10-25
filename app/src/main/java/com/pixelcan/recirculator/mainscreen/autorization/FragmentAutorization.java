package com.pixelcan.recirculator.mainscreen.autorization;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pixelcan.recirculator.R;

/**
 * Класс отвечает за фрагмент регистрации принимает даннные от пользователя проверяет на коректность написания.
 * Вызывает другой класс(имя) который коннектится и возвращает тру или фолс если тру то данный класс переправляет в другое активити
 * обрабатывает нажатие на кнопку регистрации и переправляет в фрагмент регистрации
 */
public class FragmentAutorization extends Fragment {


    EditText edTxLogin, edTxPasw;
    // views of screen
    Button btnEnter;
    Button btnRegistration;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ProgressDialog dialog;

    private String mParam1;
    private String mParam2;


    public FragmentAutorization() {
        // Required empty public constructor
    }


    public static FragmentAutorization newInstance(String param1, String param2) {
        FragmentAutorization fragment = new FragmentAutorization();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_autorization, container, false);
        edTxLogin = (EditText) v.findViewById(R.id.edTxLogin);
        edTxPasw = (EditText) v.findViewById(R.id.edTxPasw);

        //Active for touch on Registration Button
        btnRegistration = (Button) v.findViewById(R.id.btnRegistration);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((RegistrationActivity) getActivity()).replaceFragment();
            }
        });
        //Active for touch on Autorization Button
        btnEnter = (Button) v.findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //проверяем вернувшийся пароль с сервера
                //включаем диалогПрогресс
                //переходим в активити если ок
                if (chekedUser()) {

                    ((RegistrationActivity) getActivity()).goToMainActivity();
                }  //   new ConnenctorServer((AppCompatActivity)getActivity(),1).execute("1");
                else
                    Toast.makeText(getActivity(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                //    showProgressAutentification();
            }
        });
        return v;

    }


    /**
     * Метод для проверки введеных данных
     */
    private boolean chekedUser() {
        boolean checkUser = false;
        String[] identifmass = ((RegistrationActivity) getActivity()).loadText();
        String login = edTxLogin.getText().toString();
        String password = edTxPasw.getText().toString();
        if (login.equals(identifmass[0]) && password.equals(identifmass[1])){
            checkUser = true;}
        Toast.makeText(getActivity(), "Text loaded"+identifmass[0]+"//asdasdasdad//"+identifmass[1], Toast.LENGTH_SHORT).show();
        return checkUser;
    }

    /**
     * Метод выводит Прогресс диалог загрузки данных с сервера
     */
 /*   private void showProgressAutentification() {

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Проверка...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
    }
*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }
}
