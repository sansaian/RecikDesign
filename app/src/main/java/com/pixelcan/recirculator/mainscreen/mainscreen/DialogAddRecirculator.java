package com.pixelcan.recirculator.mainscreen.mainscreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.pixelcan.recirculator.R;
import com.pixelcan.recirculator.mainscreen.MainActivity;

/**
 * Created by Max Shalavin on 14.12.2016.
 */


public class DialogAddRecirculator extends DialogFragment implements
        DialogInterface.OnClickListener {
    private View form = null;
    EditText editText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        form = getActivity().getLayoutInflater()
                .inflate(R.layout.frg_dialog_adddevice, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        editText = (EditText) form.findViewById(R.id.editText);

        return (builder.setTitle("Введите ID Рециркулятора").setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null).create());

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        ((MainActivity)getActivity()).setIdDevice(editText.getText().toString());
        Log.d("DialogAddRecirculator", "edittext "+editText.getText().toString());
    }
}