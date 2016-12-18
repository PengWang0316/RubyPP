package com.example.pengwang.rubypp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;

import com.example.pengwang.rubypp.R;
import com.example.pengwang.rubypp.adapters.MainRecyclerViewAdapter;
import com.example.pengwang.rubypp.dao.Record;
import com.example.pengwang.rubypp.utils.SQLUtil;

import java.util.HashMap;

/**
 * Created by Peng on 12/17/2016.
 * Using it to create a new record
 */

public class AddRecordDialogFragment extends DialogFragment {
    private final static String TAG="AddRecordDialogFragment";
    private static final int INITIAL_CAPACITY = 3;
    public static final int PEED_INDEX = 0;
    public static final int POOPED_INDEX = 1;
    public static final int ATE_INDEX = 2;
    private SparseBooleanArray booleanArray;
    private static Record record;
    private static MainRecyclerViewAdapter.RecordHolder holder;
    private static int recordIndex;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Initial and give default value to boolean arry
        booleanArray=new SparseBooleanArray(INITIAL_CAPACITY);
        booleanArray.put(PEED_INDEX,false);
        booleanArray.put(POOPED_INDEX,false);
        booleanArray.put(2,false);

        //Create builder and listeners
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.update_dialog_title).setMultiChoiceItems(R.array.update_dialog_array,null,new DialogInterface.OnMultiChoiceClickListener(){
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
                 //Use SQLUtil class to insert or update the records
                 if(record.isRecord()) SQLUtil.updateRecord(record);
                 else{
                     record.setRecord(true);
                     SQLUtil.insertRecord(record);
                 }
                 //after finish, call the adapter back
                 holder.callBackAddNewRecord(recordIndex);
            }
        });



        return builder.create();
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
}
