package com.example.pengwang.rubypp.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
        new DatabaseAsyncTask((Activity) recyclerView.getContext()){

            @Override
            protected Integer doInBackground(String... strings) {

                recordArrayList.addAll(getRecordsFromDatabase());
                recordArrayList.addAll(getFutureRecords(recordArrayList.get(recordArrayList.size()-1)));
                return END_PROGRESS;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                recyclerView.scrollToPosition(recordArrayList.size()-1);
            }
        }.execute();

    }





    public static void updateRecord(Record record) {
        Log.d(TAG,"---------------Updated Record---------------------");
    }

    public static void insertRecord(Record record) {
        Log.d(TAG,"---------------Inserted Record---------------------");
    }

    public static void refreshView(RecyclerView mainRecyclerView, ArrayList<Record> recordArrayList, RecyclerView.Adapter adapter) {
        recordArrayList.clear();
        getInitialRecordsFromDatabase(mainRecyclerView,recordArrayList);

        //**********this should be refreshed in a call back after get data from database
        adapter.notifyDataSetChanged();
    }
}
