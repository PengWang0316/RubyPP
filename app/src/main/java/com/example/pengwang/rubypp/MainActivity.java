package com.example.pengwang.rubypp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.pengwang.rubypp.adapters.MainRecyclerViewAdapter;
import com.example.pengwang.rubypp.dao.Record;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private RecyclerView mainRecyclerView;
    private LinearLayoutManager mainLinerLayoutManager;
    private ArrayList<Record> recordArrayList = new ArrayList<Record>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting action bar
        Toolbar myToolBar = (Toolbar)findViewById(R.id.maim_tool_bar);
        setSupportActionBar(myToolBar);

        //Setting up the recycler view
        mainRecyclerView=(RecyclerView) findViewById(R.id.main_recycler_view);
        mainLinerLayoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(mainLinerLayoutManager);
        RecyclerView.Adapter adapter = new MainRecyclerViewAdapter(recordArrayList);
        mainRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(recordArrayList.size()==0) getRecords();
    }

    /**************************
     * Use to get data from database
     */
    private void getRecords() {
        Log.d(TAG, "------------------Get Records-----------------------");

        Record r1=new Record();
        Record r2=new Record();
        Record r3=new Record();
        r1.setTime("5 AM");
        r1.setPeed(true);
        r1.setDate("March 12 2016");
        r2.setTime("7 AM");
        r2.setPooped(true);
        r2.setAte(true);
        r2.setDate("March 12 2016");
        r3.setTime("11 AM");
        r3.setPeed(true);
        r3.setDate("March 12 2016");
        recordArrayList.add(r1);
        recordArrayList.add(r2);


        for (int i=0;i<50;i++){
            Record r4=new Record();
            r4.setTime("5 AM");
            r4.setPeed(true);
            r4.setPooped(true);
            r4.setAte(true);
            r4.setDate("March 12 2016");
            recordArrayList.add(r4);
        }
        recordArrayList.add(r3);
    }
}
