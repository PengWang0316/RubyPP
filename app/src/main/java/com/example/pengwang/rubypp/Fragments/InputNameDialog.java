package com.example.pengwang.rubypp.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.example.pengwang.rubypp.R;

import java.util.zip.Inflater;

/**
 * Created by Peng on 12/22/2016.
 */

public class InputNameDialog extends DialogFragment {

    public static final String INPUT_NAME_DIALOG = "InputNameDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity activity=getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.input_name_dialog,null))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //if the text is empty do not go forward and show a snackbar
                        String inputText=((EditText)getDialog().findViewById(R.id.input_name_dialog_name_view)).getText().toString().trim();
                        if(inputText.equals("")) {
                            Snackbar.make(activity.findViewById(R.id.activity_main),R.string.input_name_empty_message,Snackbar.LENGTH_SHORT).show();
                            //show the dialog again
                            InputNameDialog dialog=new InputNameDialog();
                            dialog.setCancelable(false);
                            dialog.show(getFragmentManager(), INPUT_NAME_DIALOG);
                        }else {
                            //save name to shared preferences
                            SharedPreferences sharedPreferences=activity.getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString(getResources().getString(R.string.default_name_key),inputText);
                            editor.commit();
                        }
                    }
            });




        return builder.create();
    }
}
