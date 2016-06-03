package com.pixelcan.recirculator.mainscreen;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pixelcan.recirculator.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FragmentInfo extends Fragment {

    private TextView textViewShowHumidity, textViewShowTemper, textViewShowCO,textView34;
    public TextView textViewShowCO2, textViewPressure, textPointerOnRoom;
    private TextView textheAverage, textShowtemperondoor, textShowResLamp, textShowFun;
    private String typeView;
    static String typeViewstatic;
    ImageView imageView,imageViewRingCO,imageViewRingCO2,imageViewResurceLamp;
    private static final String KEY_CONTENT = "FragmentInfo:Content";
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    public String[] infomass;
    ListView lvMain;

    public void setInfomass(String[] infomass) {
        this.infomass = infomass;
    }

    public static FragmentInfo newInstance(int content, String typeView) {
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
        return drowView(v, inflater, container);
    }

    private View drowView(View v, LayoutInflater inflater, ViewGroup container) {

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
                textPointerOnRoom = (TextView) v.findViewById(R.id.textPointerOnRoom);
                setDrawableforScreenCommand();

                lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // SendComand sendComand = new SendComand(position);
                        // sendComand.sendComandonServer();
                        ((MainActivity) getActivity()).mainsendcomand(position);
                        // Log.d("MyList", "itemClick: position = " + position + ", id = "
                        //         + id);
                    }
                });
                break;
            case "data":
                v = inflater.inflate(R.layout.testinterface5, container, false);
                imageView = (ImageView) v.findViewById(R.id.imageView);
                textViewShowTemper = (TextView) v.findViewById(R.id.textShowTemper);
                textViewShowHumidity = (TextView) v.findViewById(R.id.textShowHumidity);
                textViewShowCO = (TextView) v.findViewById(R.id.textShowCO);
                textViewShowCO2 = (TextView) v.findViewById(R.id.textShowCO2);
                textView34 = (TextView) v.findViewById(R.id.textView34);
                textView34.setText("Уровень CO"+(char)0x00B2 );
                textPointerOnRoom = (TextView) v.findViewById(R.id.textPointerOnRoom);
                textViewPressure = (TextView) v.findViewById(R.id.textShowPressure);
                textShowtemperondoor = (TextView) v.findViewById(R.id.textShowtemperondoor);
                textheAverage = (TextView) v.findViewById(R.id.textheAverage);
                textShowResLamp = (TextView) v.findViewById(R.id.textShowResLamp);
                textShowFun = (TextView) v.findViewById(R.id.textShowFun);
                imageViewRingCO = (ImageView) v.findViewById(R.id.ringco);
                imageViewRingCO2 = (ImageView) v.findViewById(R.id.ringco2);
                imageViewResurceLamp = (ImageView) v.findViewById(R.id.ringressurs);
                //////////////////////////////////////////////////

                ////////////////////////////////////////////////
                setDrawableforScreenData();
                break;
        }

        return v;
    }

    //отрисовка экрана с информацией с датчиками
    private void setDrawableforScreenData() {
        int ringID=0,ringIDbottom=0;
        switch (numberpage) {
            case "0":
                imageView.setImageResource(R.drawable.shape1);
                textPointerOnRoom.setText("Весь дом");
                textheAverage.setVisibility(View.VISIBLE);
                ringID = R.drawable.ring;
                ringIDbottom = R.drawable.ringbottom;
                break;
            case "1":
                imageView.setImageResource(R.drawable.shapegreen);
                textPointerOnRoom.setText("Гостиная");
                textheAverage.setVisibility(View.INVISIBLE);
                ringID = R.drawable.ringgreen;
                ringIDbottom = R.drawable.ringgreenbottom;

                break;
        }
        imageViewRingCO.setImageResource(ringID);
        imageViewRingCO2.setImageResource(ringID);
        imageViewResurceLamp.setImageResource(ringIDbottom);
       // imageViewRingCO.setBackgroundColor (Color.parseColor("#FF48CF12"));

       // imageViewRingCO2.setBackgroundColor (Color.parseColor("#FF48CF12"));
       // imageViewResurceLamp.setBackgroundColor(Color.parseColor("#FFE91A1A"));
        // imageView.setImageResource(R.drawable.shape1);
    }

    private void drowRing (String cO2,String resLamp,double doubleCO){

        if(doubleCO<40.0) imageViewRingCO.setBackgroundColor (Color.parseColor("#FF48CF12"));
        else if(doubleCO>=40.0&&doubleCO<=75) imageViewRingCO.setBackgroundColor (Color.parseColor("#FFFFF30E"));
        else imageViewRingCO.setBackgroundColor (Color.parseColor("#FFEA0A0A"));

        double doubleCO2 = new BigDecimal((Double.parseDouble(cO2))).setScale(1, RoundingMode.UP).doubleValue();
        if(doubleCO2<40.0) imageViewRingCO2.setBackgroundColor (Color.parseColor("#FF48CF12"));
        else if(doubleCO2>=40.0&&doubleCO2<=75) imageViewRingCO2.setBackgroundColor (Color.parseColor("#FFFFF30E"));
        else imageViewRingCO2.setBackgroundColor (Color.parseColor("#FFEA0A0A"));
        textViewShowCO2.setText(doubleCO2 + " %");

        double doubleresLamp = new BigDecimal((Double.parseDouble(resLamp))).setScale(1, RoundingMode.UP).doubleValue();
        if(doubleresLamp>6000) imageViewResurceLamp.setBackgroundColor (Color.parseColor("#FF48CF12"));
        else if(doubleresLamp>=2000&&doubleresLamp<=6000) imageViewResurceLamp.setBackgroundColor (Color.parseColor("#FFFFF30E"));
        else imageViewResurceLamp.setBackgroundColor (Color.parseColor("#FFEA0A0A"));
        textShowResLamp.setText(doubleresLamp + " hr");
    }

    private void setDrawableforScreenCommand() {
        switch (numberpage) {
            case "0":
                imageView.setImageResource(R.drawable.shape1);
                textPointerOnRoom.setText("Весь дом");
                //textheAverage.setVisibility(View.VISIBLE);
                break;
            case "1":
                imageView.setImageResource(R.drawable.shapegreen);
                textPointerOnRoom.setText("Гостиная");
                //textheAverage.setVisibility(View.INVISIBLE);
                break;
        }
        // imageView.setImageResource(R.drawable.shape1);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, numberpage);
    }
//проблема с обрезаемыми значениями
    public void fullTextviewData(String[] data) {
        double doubleCO=0;
        if (textViewShowCO2 != null) {
           // textViewShowCO2.setText(data[0] + " %");

            doubleCO = translateCO(data[1]);

            textViewShowCO.setText(doubleCO + " %");//сразу обрезано
            textShowtemperondoor.setText("18" + " " + (char) 0x00B0);
            textViewShowTemper.setText(data[3] + " " + (char) 0x00B0);
            textViewPressure.setText(translatePaskalTommhg(data[4]) + " mmHg");
            textViewShowHumidity.setText(data[5] + " %");
           // textShowResLamp.setText(data[6].substring(0,5) + " hr");
            textShowFun.setText(data[7] + " rpm");

            drowRing(data[0],data[6],doubleCO);
        }
    }
    private int translatePaskalTommhg (String paskal)
    {
        int mmHg = (int) (Double.parseDouble(paskal) / 133.3224);

        return mmHg;
    }
    private double translateCO (String co)
    {

        double coDouble = new BigDecimal(((1-(Double.parseDouble(co)))*100)).setScale(2, RoundingMode.UP).doubleValue();
        return coDouble;
    }
}
