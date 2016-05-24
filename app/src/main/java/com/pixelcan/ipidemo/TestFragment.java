package com.pixelcan.ipidemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestFragment extends Fragment {
    ImageView imageView;
    private static final String KEY_CONTENT = "TestFragment:Content";
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    public static TestFragment newInstance(int content) {
        TestFragment fragment = new TestFragment();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();

        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.testinterface5, container, false);
        imageView = (ImageView) v.findViewById(R.id.imageView);
        setDrawable();
          /* TextView text = new TextView(getActivity());
           text.setGravity(Gravity.CENTER);

           text.setText(mContent);
           text.setTextSize(20 * getResources().getDisplayMetrics().density);
           text.setPadding(20, 20, 20, 20);

           LinearLayout layout = new LinearLayout(getActivity());
           layout.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
           layout.setGravity(Gravity.CENTER);
           layout.addView(text);
*/
        return v;
    }

    private void setDrawable() {
        imageView.setImageResource(R.drawable.shape);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
