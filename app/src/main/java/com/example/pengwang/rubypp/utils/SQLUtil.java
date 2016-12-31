package com.example.pengwang.rubypp.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.pengwang.rubypp.R;
import com.example.pengwang.rubypp.dao.Record;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Peng on 12/16/2016.
 * Util class that will be used to get data from database.
 */
public class SQLUtil {

    private static final String TAG = "SQLUtil";
    private static final int MAX_PROGRESS = 10;


    //Getting records from the database.
    public static void getInitialRecordsFromDatabase(final RecyclerView recyclerView,final ArrayList<Record> recordArrayList) {
        new DatabaseAsyncTask(null,(Activity) recyclerView.getContext()){

            @Override
            protected Integer doInBackground(String... strings) {
                setMessage(activity.getResources().getString(R.string.get_information_finished_message));
                recordArrayList.addAll(getRecordsFromDatabase());
                recordArrayList.add(new Record(recordArrayList.get(recordArrayList.size()-1)));
                //recordArrayList.addAll(getFutureRecords(recordArrayList.get(recordArrayList.size()-1)));
                return END_PROGRESS;
            }



            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                recyclerView.scrollToPosition(recordArrayList.size()-1);
            }
        }.execute();

    }



    public static void updateRecord(Record record,Activity activity) {
        //Log.d(TAG,"---------------Updated Record---------------------");
        new DatabaseAsyncTask(record, activity){

            @Override
            protected Integer doInBackground(String... strings) {
                setMessage(activity.getResources().getString(R.string.update_record_finished_message));
                updateRecordToDatabase(record);
                return END_PROGRESS;
            }

        }.execute();
    }

    public static void insertRecord(Record record, Activity activity, final RecyclerView mainRecyclerView, final ArrayList recordArrayList, final RecyclerView.Adapter adapter) {
       //Log.d(TAG,"---------------Inserted Record---------------------");
        new DatabaseAsyncTask(record, activity){

            @Override
            protected Integer doInBackground(String... strings) {
                setMessage(activity.getResources().getString(R.string.insert_record_finished_message));
                insertRecordToDatabase(record);
                return END_PROGRESS;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                refreshView(mainRecyclerView,recordArrayList,adapter);
            }
        }.execute();
    }

    public static void refreshView(RecyclerView mainRecyclerView, ArrayList<Record> recordArrayList, RecyclerView.Adapter adapter) {
        recordArrayList.clear();
        getInitialRecordsFromDatabase(mainRecyclerView,recordArrayList);

        //**********this should be refreshed in a call back after get data from database
        adapter.notifyDataSetChanged();
    }
}
