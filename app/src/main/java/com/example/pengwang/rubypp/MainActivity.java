package com.example.pengwang.rubypp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.pengwang.rubypp.Fragments.AddRecordDialogFragment;
import com.example.pengwang.rubypp.Fragments.InputNameDialog;
import com.example.pengwang.rubypp.Fragments.ShowStatisticDialog;
import com.example.pengwang.rubypp.adapters.MainRecyclerViewAdapter;
import com.example.pengwang.rubypp.dao.Record;
import com.example.pengwang.rubypp.decorations.VerticalSpaceItemDecoration;
import com.example.pengwang.rubypp.utils.SQLUtil;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
//    private static final String INSERT_DIALOG_TAG = "Insert Dialog Tag";
    private RecyclerView mainRecyclerView;
    private LinearLayoutManager mainLinerLayoutManager;
    private ArrayList<Record> recordArrayList = new ArrayList<Record>();
    private RecyclerView.Adapter adapter;

    //private final static String[] TEXTS_OF_TIME_VIEW={"05:00","07:00","11:00","14:00","17:00","20:00","23:00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting action bar
        Toolbar myToolBar = (Toolbar)findViewById(R.id.main_tool_bar);
        setSupportActionBar(myToolBar);

        //Setting up the recycler view
        mainRecyclerView=(RecyclerView) findViewById(R.id.main_recycler_view);
        mainRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mainLinerLayoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(mainLinerLayoutManager);
        mainRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration());
        adapter = new MainRecyclerViewAdapter(recordArrayList);

//        use recyclerview-animators library to do the animation job
//        AlphaInAnimationAdapter animationAdapter=new AlphaInAnimationAdapter(adapter);
//        animationAdapter.setDuration(1000);
        mainRecyclerView.setAdapter(adapter);
        FadeInLeftAnimator animator=new FadeInLeftAnimator();
//        animator.setInterpolator(new OvershootInterpolator());
        mainRecyclerView.setItemAnimator(animator);

        //set listener for float action button (use the same listener with recycle view)
        setupListenrForFloatActionButton();
        findDefaultName();
    }

    private void setupListenrForFloatActionButton() {
        findViewById(R.id.main_floating_action_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG,"----------------------------------");
                AddRecordDialogFragment dialogFragment=new AddRecordDialogFragment();
//                dialogFragment.setHolder(null);
                Record record=new Record();
                record.setRecord(false);
                dialogFragment.setRecord(record);
                dialogFragment.setRecordArrayList(recordArrayList);
                dialogFragment.setRecyclerView(mainRecyclerView);
//                dialogFragment.setIndex(index);
                dialogFragment.show(getSupportFragmentManager(), AddRecordDialogFragment.INSERT_DIALOG_TAG);
            }
        });
    }

    /*******
     * Test whether has a default name
     */
    private void findDefaultName() {
        SharedPreferences sharedPreferences= getPreferences(Context.MODE_PRIVATE);
        String defaultName=sharedPreferences.getString(getString(R.string.default_name_key),null);
        if (defaultName==null){
            InputNameDialog inputNameDialog=new InputNameDialog();
            inputNameDialog.setCancelable(false);
            inputNameDialog.show(getSupportFragmentManager(), InputNameDialog.INPUT_NAME_DIALOG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if(recordArrayList.size()==0) SQLUtil.getInitialRecordsFromDatabase(mainRecyclerView,recordArrayList);
        if (recordArrayList.size()!=0) recordArrayList.clear();
        SQLUtil.getInitialRecordsFromDatabase(mainRecyclerView,recordArrayList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //if(recordArrayList.size()==0) SQLUtil.getInitialRecordsFromDatabase(mainRecyclerView,recordArrayList);
    }

    /******
     * Inflate menu for main activity (call back)
     * @param menu the main activity's menu
     * @return always true after inflate
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_action_menu,menu);
        return true;
    }

    /**********
     * action when item was clicked
     * @param item was selected
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_action_menu_refresh:
                SQLUtil.refreshView(mainRecyclerView,recordArrayList,adapter);
                break;
                //SQLUtil.getInitialRecordsFromDatabase();
            case R.id.main_action_menu_medal:
                showStatistic();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showStatistic() {
        SQLUtil.getStatisticDate(this,getSupportFragmentManager());

    }

    /**************************
     * Use to get data from database

    private void getInitialRecords() {
        Log.d(TAG, "------------------Get Records-----------------------");
        recordArrayList.addAll(SQLUtil.getInitialRecordsFromDatabase());

        //Set the position of view to the end
        Log.d(TAG, "----------------------size:"+recordArrayList.size()+"-----------------------------");
        mainRecyclerView.scrollToPosition(recordArrayList.size()-1);
    }

    ***************
     * Get some record for the future records
     * Use the current time to calculate the value for time view
     *

    private ArrayList<Record> getFutureRecords() {
        ArrayList<Record> recordsList= new ArrayList<Record>(NUMBER_OF_ITEM_FOR_FUTURE);


        return recordsList;
    }*/
/*
    public void onClick(View view) {
        Log.d(TAG,"--------onClick-----------");}
        */
}
