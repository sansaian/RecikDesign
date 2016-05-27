package com.pixelcan.recirculator.mainscreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pixelcan.recirculator.R;

public class FragmentInfo extends Fragment {
   //  public  int numbepage;
    private TextView textViewShowHumidity;
    private TextView textViewShowTemper;
    private TextView textViewShowCO;
    public TextView textViewShowCO2;
    private TextView textViewPressure;
    private TextView textPointerOnRoom;
    private TextView textheAverage;
    private String typeView;
    //private ToggleButton onOffButton;
    static String typeViewstatic;
    ImageView imageView;
    private static final String KEY_CONTENT = "FragmentInfo:Content";
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    public String[] infomass;
    ListView lvMain;

    public void setInfomass(String[] infomass) {
        this.infomass = infomass;
    }

    public static FragmentInfo newInstance(int content,String typeView) {
        FragmentInfo fragment = new FragmentInfo();
        //numbepage = content;
       StringBuilder builder = new StringBuilder();
        StringBuilder builder1 = new StringBuilder();
      /*  for (int i = 0; i < 20; i++) {
            builder.append(content).append(" ");
        }*/
        builder.append(content);
        builder1.append(typeView);
    //    builder.deleteCharAt(builder.length() - 1);
        fragment.numberpage = builder.toString();
        fragment.typeView = builder1.toString();
        return fragment;
    }

    private String numberpage = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            numberpage = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = null;
        Log.d("Mylog", "прошло создание typeView " + typeView);
        Log.d("Mylog", "прошло создание typeViewstatic " + typeViewstatic);
        return drowView(v,inflater,container);
    }

    private View drowView(View v,LayoutInflater inflater, ViewGroup container) {

        // imageLine = (ImageView) v.findViewById(R.id.gineImageView);

        switch (typeViewstatic) {
            case "modes":
                v = inflater.inflate(R.layout.modes, container, false);
                lvMain = (ListView) v.findViewById(R.id.lvMain);
                // устанавливаем режим выбора пунктов списка
                lvMain.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                // Создаем адаптер, используя массив из файла ресурсов
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                        getActivity(), R.array.names,
                        android.R.layout.simple_list_item_single_choice);
                lvMain.setAdapter(adapter);
                imageView = (ImageView) v.findViewById(R.id.imageView);
                break;
            case "data":
                v = inflater.inflate(R.layout.testinterface5, container, false);
                imageView = (ImageView) v.findViewById(R.id.imageView);
                textViewShowTemper = (TextView) v.findViewById(R.id.textShowTemper);
                textViewShowHumidity = (TextView) v.findViewById(R.id.textShowHumidity);
                textViewShowCO = (TextView) v.findViewById(R.id.textShowCO);
                textViewShowCO2 = (TextView) v.findViewById(R.id.textShowCO2);
                textViewPressure = (TextView) v.findViewById(R.id.textShowPressure);
                textPointerOnRoom = (TextView) v.findViewById(R.id.textPointerOnRoom);
                textheAverage = (TextView) v.findViewById(R.id.textheAverage);
                setDrawableforScreenData();
                break;
        }

        return v;
    }

    //отрисовка экрана с информацией с датчиками
    private void setDrawableforScreenData() {
        switch (numberpage) {
            case "0":
                imageView.setImageResource(R.drawable.shape1);
                textPointerOnRoom.setText("Весь дом");
                textheAverage.setVisibility(View.VISIBLE);
                break;
            case "1":
                imageView.setImageResource(R.drawable.shapegreen);
                textPointerOnRoom.setText("Гостиная");
                textheAverage.setVisibility(View.INVISIBLE);
                break;
        }
       // imageView.setImageResource(R.drawable.shape1);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, numberpage);
    }
    public void fullTextviewData(String[] data){


        textViewShowCO2.setText(data[0]);
        textViewShowCO.setText(data[1]);

        textViewShowTemper.setText(data[3]);
        textViewPressure.setText(data[4]);
        textViewShowHumidity.setText(data[5]);
    }
}
