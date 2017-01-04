package com.example.pengwang.rubypp.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.pengwang.rubypp.R;
import com.example.pengwang.rubypp.adapters.MainRecyclerViewAdapter;
import com.example.pengwang.rubypp.dao.Record;
import com.example.pengwang.rubypp.utils.SQLUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Peng on 12/17/2016.
 * Using it to create a new record
 */

public class AddRecordDialogFragment extends DialogFragment {
    private final static String TAG="AddRecordDialogFragment";
    private static final int INITIAL_CAPACITY = 5;
    private static final int PEED_INDEX = 0;
    private static final int POOPED_INDEX = 1;
    private static final int ATE_INDEX = 2;
    private static final int PEED_INSIDE_INDEX = 3;
    private static final int POOPED_INSIDE_INDEX = 4;
    private static final String COLON = ":";
    private static final int HOUR_BEGIN_INDEX = 0;
    private static final int HOUR_END_INDEX = 2;
    private static final int MINUTE_BEGIN_INDEX = 3;
    private static final int MINUTEEND_INDEX = 5;
    private static final int YEAR_BEGIN_INDEX = 0;
    private static final int YEAR_END_INDEX = 4;
    private static final int MONTH_BEGIN_INDEX = 5;
    private static final int MONTH_END_INDEX = 7;
    private static final int DAY_BEGIN_INDEX = 8;
    private static final int DAY_END_INDEX = 10;
    private static final String HYPHEN = "-";
    private static final int INT_ONE = 1;
    private SparseBooleanArray booleanArray;
    private  Record record;
    private  MainRecyclerViewAdapter.RecordHolder holder;
    private  int recordIndex;
    private ArrayList<Record> recordArrayList;
//    private RecyclerView.Adapter adapter;
    private RecyclerView mainRecyclerView;

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
        booleanArray.put(PEED_INSIDE_INDEX,false);
        booleanArray.put(POOPED_INSIDE_INDEX,false);

        //Create builder and listeners
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.update_dialog_title)).setMultiChoiceItems(R.array.update_dialog_array,null,new DialogInterface.OnMultiChoiceClickListener(){
            @Override
            //Listener for click items
            public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                if(isChecked) booleanArray.put(which,true);
                else booleanArray.put(which,false);
//                Log.d(TAG,"------------------Which is: "+which+"----------------------");
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
                 record.setPeedInside(booleanArray.get(PEED_INSIDE_INDEX));
                 record.setPoopedInside(booleanArray.get(POOPED_INSIDE_INDEX));
                 //SharedPreferences sharedPreferences= getActivity().getPreferences(Context.MODE_PRIVATE);
                 //String defaultName=sharedPreferences.getString(getString(R.string.default_name_key),null);
                 Activity activity=getActivity();
                if (!record.isRecord()){
                    //record.setRecord(true);
                    //set name for record
                    SharedPreferences sharedPreferences= activity.getPreferences(Context.MODE_PRIVATE);
                    String defaultName=sharedPreferences.getString(getString(R.string.default_name_key),null);
                    record.setName(defaultName);
                }
                 //after finish, call another dialog to pick up time
                 showTimePickerDialog(activity);
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

    private void showTimePickerDialog(final Activity activity) {
        int hourOfDay;
        int minute;
        if (record.isRecord()){
            hourOfDay=Integer.parseInt(record.getTime().substring(HOUR_BEGIN_INDEX, HOUR_END_INDEX));
            minute=Integer.parseInt(record.getTime().substring(MINUTE_BEGIN_INDEX, MINUTEEND_INDEX));
        }else{
            Calendar calendar=Calendar.getInstance();
            hourOfDay=calendar.get(Calendar.HOUR_OF_DAY);
            minute=calendar.get(Calendar.MINUTE);
        }


        TimePickerDialog dialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                Log.d(TAG,"-----------TimePickerDialogFragment changed------------");
                record.setTime(String.valueOf(hourOfDay)+ COLON +String.valueOf(minute));
//              Let user picking the date
                showDatePickerDialog(activity);
            }


        },hourOfDay,minute,true);
        dialog.setTitle(R.string.time_picker_dialog_title);
        dialog.show();
    }

    private void showDatePickerDialog(final Activity activity) {
        int year;
        int month;
        int day;
        if (record.isRecord()){
            year=Integer.parseInt(record.getDate().substring(YEAR_BEGIN_INDEX, YEAR_END_INDEX));
            month= Integer.parseInt(record.getDate().substring(MONTH_BEGIN_INDEX, MONTH_END_INDEX))- INT_ONE;
            day=Integer.parseInt(record.getDate().substring(DAY_BEGIN_INDEX, DAY_END_INDEX));
        }else{
            Calendar calendar=Calendar.getInstance();
            year=calendar.get(Calendar.YEAR);
            month=calendar.get(Calendar.MONTH);
            day=calendar.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerDialog=new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                record.setDate(year+ HYPHEN +(month+INT_ONE)+HYPHEN+day);
                //Use SQLUtil class to insert or update the records
                if(record.isRecord()){
                    SQLUtil.updateRecord(record,activity);
                    holder.callBackAddNewRecord(recordIndex);
                }
                else {
                    record.setRecord(true);
                    //Use  print mysql_insert_id(); in php file return id
                    SQLUtil.insertRecord(record,activity,mainRecyclerView,recordArrayList,mainRecyclerView.getAdapter());
                    //Notify adapter what is inserted and what should be changed
                    setupNewAdapter();
                }

            }
        },year,month,day);
        datePickerDialog.setTitle(R.string.date_picker_dialog_title);
        datePickerDialog.show();
    }

    /*******
     * remove old p
     */
    private void setupNewAdapter() {
        int lastPosition=recordArrayList.size()-1;
        MainRecyclerViewAdapter adapter=(MainRecyclerViewAdapter) mainRecyclerView.getAdapter();
//        adapter.notifyItemInserted(lastPosition-1);

        recordArrayList.remove(lastPosition);

        adapter.notifyItemRemoved(lastPosition);

//        adapter.notifyItemRangeChanged(lastPosition,adapter.getItemCount());

        recordArrayList.add(record);

        adapter.notifyItemInserted(recordArrayList.size()-1);

        recordArrayList.add(new Record(record));
        lastPosition=recordArrayList.size()-1;
//                    mainRecyclerView.getAdapter().notifyItemRangeInserted(recordArrayList.size()-1,recordArrayList.size()+1);
//        adapter.notifyItemRangeInserted(lastPosition-1,2);

        adapter.notifyItemInserted(lastPosition);

//        adapter.notifyDataSetChanged();
//        adapter.notifyDataSetChanged();
        int endOfArraylist=recordArrayList.size();
        ArrayList<Integer> newInsertedPositionList=new ArrayList();
        newInsertedPositionList.add(endOfArraylist);
        newInsertedPositionList.add(--endOfArraylist);
        newInsertedPositionList.add(--endOfArraylist);
        adapter.setNewInsertedPosition(newInsertedPositionList);
        mainRecyclerView.scrollToPosition(lastPosition);
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

    public void setRecordArrayList(ArrayList<Record> recordArrayList) {
        this.recordArrayList = recordArrayList;
    }

//    public void setAdapter(RecyclerView.Adapter adapter) {
//        this.adapter=adapter;
//    }

    public void setRecyclerView(RecyclerView mainRecyclerView) {
        this.mainRecyclerView=mainRecyclerView;
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
