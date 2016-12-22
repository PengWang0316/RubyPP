package com.example.pengwang.rubypp.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.pengwang.rubypp.R;
import com.example.pengwang.rubypp.dao.Record;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Peng on 12/20/2016.
 */

public abstract class DatabaseAsyncTask extends AsyncTask<String,Integer,Integer> {
    private static final int START_PROGRESS = 0;
    private final static int NUMBER_OF_ITEM_FOR_FUTURE=5;
    public static final int END_PROGRESS = 5;
    private static final String GET_INITIAL_URL = "http://pengwang.freeoda.com/GetInitialRecords.php";
    private static final int INT_TRUE = 1;
    protected Activity activity;
    private final static String TAG="DatabaseAsyncTask";
    private String message;

    private String getMessage() {
        return message==null?activity.getResources().getString(R.string.default_database_message):message;
    }



    public void setMessage(String message) {
        this.message = message;
    }

    public DatabaseAsyncTask(Activity activity){
        this.activity=activity;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressBar progressBar=(ProgressBar) activity.findViewById(R.id.main_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(START_PROGRESS);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        activity.findViewById(R.id.main_progress_bar).setVisibility(View.INVISIBLE);
        //use a snackbar to show it is finished
        showSnackbar();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        ProgressBar progressBar=(ProgressBar) activity.findViewById(R.id.main_progress_bar);
        progressBar.setMax(values[1]);
        progressBar.setProgress(values[0]);
    }



    public ArrayList<Record> getFutureRecords(Record lastRecord) {
        ArrayList<Record> recordsList= new ArrayList<>(NUMBER_OF_ITEM_FOR_FUTURE);
        for(int i=0;i<4;i++){
            Record newRecord=new Record(lastRecord);
            recordsList.add(newRecord);
            lastRecord=newRecord;
        }

        return recordsList;
    }

    public Collection<? extends Record> getRecordsFromDatabase() {
        //int progress=START_PROGRESS;
        //publishProgress(progress++, END_PROGRESS);
        //testSleep();
        /**
        ArrayList<Record> recordArrayList=new ArrayList<>();
        Record r1=new Record();
        Record r2=new Record();
        Record r3=new Record();
        Record r4=new Record();
        Record r5=new Record();
        Record r6=new Record();
        publishProgress(progress++, END_PROGRESS);
        //testSleep();
        r1.setTime("5:00");
        r1.setPeed(true);
        r1.setDate("03/12/2016");
        r1.setSpouseTime(Record.SIX_OCLOCK);
        r2.setTime("7:00");
        r2.setPooped(true);
        r2.setAte(true);
        r2.setDate("03/12/2016");
        r2.setSpouseTime(Record.EIGHT_OCLOCK);
        publishProgress(progress++, END_PROGRESS);
        //testSleep();
        r3.setTime("11:00");
        r3.setPeed(true);
        r3.setDate("03/12/2016");
        r3.setSpouseTime(Record.ELEVEN_OCLOCK);
        r4.setTime("14:00");
        r4.setPeed(true);
        r4.setDate("03/12/2016");
        r4.setSpouseTime(Record.FOURTEEN_OCLOCK);
        r5.setTime("17:00");
        r5.setPeed(true);
        r5.setDate("03/12/2016");
        r5.setSpouseTime(Record.SEVENTEEN_OCLOCK);
        publishProgress(progress++, END_PROGRESS);
        //testSleep();
        r6.setTime("20:00");
        r6.setPeed(true);
        r6.setDate("03/12/2016");
        r6.setSpouseTime(Record.TWENTY_OCLOCK);
        recordArrayList.add(r1);
        recordArrayList.add(r2);
        publishProgress(progress++, END_PROGRESS);

        recordArrayList.add(r3);
        recordArrayList.add(r4);
        recordArrayList.add(r5);
        recordArrayList.add(r6);
        publishProgress(progress, ++progress);
        **/





        //Real codes
        URL url=null;
        HttpURLConnection connection=null;
        String result="";
        String line;
        JSONObject jsonObject;
        ArrayList<Record> recordArrayList=null;

        try {
            url=new URL(GET_INITIAL_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            connection=(HttpURLConnection)url.openConnection();
            //connection.setDoOutput(true);
            //connection.setRequestMethod("GET");
            InputStream inputStream=new BufferedInputStream(connection.getInputStream());
            //Start to read the content
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb=new StringBuilder();
            while ((line=bufferedReader.readLine())!=null){
                sb.append(line+"\n");
            }
            inputStream.close();
            result=sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{if (connection!=null) connection.disconnect();}

        //Convert it to JSON

        try {
            jsonObject=new JSONObject(result);
            //Log.d(TAG,"--------------------------"+jsonObject.getString("time")+"-------------------------");
            recordArrayList=turnJSONToArray(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return recordArrayList;
    }

    private ArrayList<Record> turnJSONToArray(JSONObject jsonObject) throws JSONException {
        ArrayList<Record> newRecordArrayList=new ArrayList<>();

        for (int i=jsonObject.length(); i>0; i--){
            Record record=new Record();
            JSONObject newJson=(JSONObject) jsonObject.get(String.valueOf(i));
            record.setTime(newJson.getString(Record.TITLE_TIME));
            record.setDate(newJson.getString(Record.TITLE_DATE));
            record.setSpouseTime(newJson.getString(Record.TITLE_SPOUSE_TIME));
            record.setPeed(newJson.getInt(Record.TITLE_IS_PEED)==INT_TRUE);
            record.setPooped(newJson.getInt(Record.TITLE_IS_POOPED)==INT_TRUE);
            record.setAte(newJson.getInt(Record.TITLE_IS_ATE)==INT_TRUE);
            newRecordArrayList.add(record);
        }



        return newRecordArrayList;
    }

    private void showSnackbar() {
        Snackbar.make(activity.findViewById(R.id.activity_main),getMessage(),Snackbar.LENGTH_SHORT).show();
    }

    private void testSleep(){
        SystemClock.sleep(500);
    }
}
