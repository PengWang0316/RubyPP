package com.example.pengwang.rubypp.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.TimePicker;

import com.example.pengwang.rubypp.R;
import com.example.pengwang.rubypp.adapters.MainRecyclerViewAdapter;
import com.example.pengwang.rubypp.dao.Record;
import com.example.pengwang.rubypp.utils.SQLUtil;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Peng on 12/17/2016.
 * Using it to create a new record
 */

public class AddRecordDialogFragment extends DialogFragment {
    private final static String TAG="AddRecordDialogFragment";
    private static final int INITIAL_CAPACITY = 3;
    private static final int PEED_INDEX = 0;
    private static final int POOPED_INDEX = 1;
    private static final int ATE_INDEX = 2;
    private SparseBooleanArray booleanArray;
    private  Record record;
    private  MainRecyclerViewAdapter.RecordHolder holder;
    private  int recordIndex;

    //private static CheckedTextView peed;
    //private static CheckedTextView pooped;
    //private static CheckedTextView ate;
    //private static TimePicker timePicker;
    //private static Activity activity;

    /**** old version**/
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Initial and give default value to boolean arry
        booleanArray=new SparseBooleanArray(INITIAL_CAPACITY);
        booleanArray.put(PEED_INDEX,false);
        booleanArray.put(POOPED_INDEX,false);
        booleanArray.put(ATE_INDEX,false);

        //Create builder and listeners
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.update_dialog_title)+" "+record.getSpouseTime()).setMultiChoiceItems(R.array.update_dialog_array,null,new DialogInterface.OnMultiChoiceClickListener(){
            @Override
            //Listener for click items
            public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                if(isChecked) booleanArray.put(which,true);
                else booleanArray.put(which,false);
                Log.d(TAG,"------------------Which is: "+which+"----------------------");
            }
        }).setPositiveButton(R.string.ok,new DialogInterface.OnClickListener(){
             @Override
            /***********
             * The main ui thread does not wait for this dialog. So, we have to update database when uses finish
             * the choice.
             */
            public void onClick(DialogInterface dialogInterface, int i) {

                 record.setPeed(booleanArray.get(PEED_INDEX));
                 record.setPooped(booleanArray.get(POOPED_INDEX));
                 record.setAte(booleanArray.get(ATE_INDEX));
                 //SharedPreferences sharedPreferences= getActivity().getPreferences(Context.MODE_PRIVATE);
                 //String defaultName=sharedPreferences.getString(getString(R.string.default_name_key),null);
                 //Use SQLUtil class to insert or update the records
                 if(record.isRecord()) SQLUtil.updateRecord(record);
                 else{
                     record.setRecord(true);
                     //set name for record
                     SharedPreferences sharedPreferences= getActivity().getPreferences(Context.MODE_PRIVATE);
                     String defaultName=sharedPreferences.getString(getString(R.string.default_name_key),null);
                     record.setName(defaultName);
                     SQLUtil.insertRecord(record);
                 }
                 //after finish, call another dialog to pick up time
                 showTimePickerDialog();
                 //TimePickerDialogFragment timePickerDialogFragment=TimePickerDialogFragment.getInstance(getContext());
                 //timePickerDialogFragment.setHolder(holder);
                 //timePickerDialogFragment.setRecord(record);
                 //timePickerDialogFragment.setRecordIndex(recordIndex);
                 //timePickerDialogFragment.show();
                 //timePickerDialogFragment.show(((AppCompatActivity)getContext()).getSupportFragmentManager(),"UpdateDialogTag");
                 //holder.callBackAddNewRecord(recordIndex);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            //Do nothing but show a cancel button
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }

    private void showTimePickerDialog() {
        Calendar calendar=Calendar.getInstance();
        TimePickerDialog dialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                Log.d(TAG,"-----------TimePickerDialogFragment changed------------");
                record.setTime(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
                holder.callBackAddNewRecord(recordIndex);
            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
        dialog.setTitle(R.string.time_picker_dialog_title);
        dialog.show();
    }



    public void setRecord(Record record) {
        this.record = record;
    }

    public void setHolder(MainRecyclerViewAdapter.RecordHolder holder) {
        this.holder = holder;
    }

    public void setIndex(int index) {
        this.recordIndex=index;
    }

    /*For CheckedTextView

        public static void onClick(View view) {
            if (peed.isChecked()){
                booleanArray.put(PEED_INDEX,false);
                peed.setChecked(false);
            }else{
                booleanArray.put(PEED_INDEX,true);
                peed.setChecked(true);
            }
            if (pooped.isChecked()){
                booleanArray.put(POOPED_INDEX,false);
                pooped.setChecked(false);
            }else{
                booleanArray.put(POOPED_INDEX,true);
                pooped.setChecked(true);
            }
            if (ate.isChecked()){
                booleanArray.put(ATE_INDEX,false);
                ate.setChecked(false);
            }else{
                booleanArray.put(ATE_INDEX,true);
                ate.setChecked(true);
            }
        }
    */
}
