package com.pixelcan.recirculator.mainscreen.autorization;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pixelcan.recirculator.R;
import com.pixelcan.recirculator.mainscreen.connect.ConnenctorServer;


public class FragmentRegistration extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText edTexLogin, edTexName, edTexSurName, edTexPhone, edTexPasw, edTexPaswRep;
    private Button btnAccept;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Construstor must be empty
     */
    public FragmentRegistration() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegistration.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegistration newInstance(String param1, String param2) {
        FragmentRegistration fragment = new FragmentRegistration();
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
        View v = inflater.inflate(R.layout.fragment_registration, container, false);
        initializeElemetsScreen(v);


        //Active for touch on Autorization Button
        btnAccept = (Button) v.findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    String[] massUserdata = sendValueEdTextToConnector();
                if (checkRepeatPassword(massUserdata)) {
                    new ConnenctorServer((RegistrationActivity) getActivity(), 1).execute(massUserdata);
                }
                //    showProgressAutentification();
            }});
        return v;
    }

    private String[] sendValueEdTextToConnector(){
     return     new String[]{edTexLogin.getText().toString(),edTexName.getText().toString(),edTexSurName.getText().toString(),
                edTexPasw.getText().toString(),edTexPaswRep.getText().toString()};

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }




    private void initializeElemetsScreen(View v) {

        edTexLogin = (EditText) v.findViewById(R.id.edTexLogin);
        edTexName = (EditText) v.findViewById(R.id.edTexName);
        edTexSurName = (EditText) v.findViewById(R.id.edTexSurName);
        edTexPhone  = (EditText) v.findViewById(R.id.edTexPhone);
        edTexPasw = (EditText) v.findViewById(R.id.edTexPasw);
        edTexPaswRep = (EditText) v.findViewById(R.id.edTexPaswRep);
    }

    /**
     * Проверяет введеные пароли при регистрации совпадают или нет
     */
    private boolean checkRepeatPassword(String [] s) {

        boolean check = false;
        if(s!=null&&s.length>0)
            if(s[3].equals(s[4]))check=true;
            else {    Toast toast = Toast.makeText(getActivity(),
                    "Пароли не совпадают",
                    Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();}

        return check;
    }
}
