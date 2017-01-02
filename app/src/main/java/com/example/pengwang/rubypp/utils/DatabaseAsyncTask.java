package com.example.pengwang.rubypp.utils;

import android.app.Activity;
import android.os.AsyncTask;
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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created by Peng on 12/20/2016.
 *
 */

abstract class DatabaseAsyncTask extends AsyncTask<String,Integer,Integer> {
    private static final int START_PROGRESS = 0;
    private final static int NUMBER_OF_ITEM_FOR_FUTURE=5;
    static final int END_PROGRESS = 5;
    //TODO change back to the normal url
    private static final String GET_INITIAL_URL = "http://pengwang.freeoda.com/GetInitialRecordsTest.php";
    private static final int INT_TRUE = 1;
    private static final String UPDATE_PHP_URL = "http://pengwang.freeoda.com/UpdateRecord.php";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String ACCEPT = "Accept";
    private static final String PUT = "PUT";
    private static final String EQUAL_MARK = "=";
    private static final String UTF_8 = "UTF-8";
    private static final String AND_MARK = "&";
    private static final String POST = "POST";
    private static final String STRING_TRUE = "1";
    private static final String STRING_FALSE = "0";
    //TODO change back to the normal url
    private static final String INSERT_PHP_URL = "http://pengwang.freeoda.com/InsertNewRecordTest.php";
    Activity activity;
    Record record;
    //private final static String TAG="DatabaseAsyncTask";
    private String message;

    private String getMessage() {
        return message==null?activity.getResources().getString(R.string.default_database_message):message;
    }



    void setMessage(String message) {
        this.message = message;
    }

    DatabaseAsyncTask(Record record,Activity activity){
        this.activity=activity;
        this.record=record;
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

    void insertRecordToDatabase(Record record){
        conncetToPhpScript(record, INSERT_PHP_URL);
    }

    void updateRecordToDatabase(Record record){
        conncetToPhpScript(record,UPDATE_PHP_URL);
    }

    private void conncetToPhpScript(Record record,String Url) {
        //Random random = new Random();
        URL url = null;
        try {
            url = new URL(Url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            connection.setRequestMethod(POST);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connection.setDoInput(true);
        connection.setDoOutput(true);


        OutputStream os = null;
        try {
            os = connection.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, UTF_8));
            writer.write(getQuery(record));
            writer.flush();
            writer.close();
            os.close();
            connection.connect();
            connection.getResponseMessage();
//            Log.d("TAG","---------------------"+connection.getResponseMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }

        /*for test
        String line=null;
        InputStream inputStream= null;
        try {
            inputStream = new BufferedInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Start to read the content
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb=new StringBuilder();
        try {
            while ((line=bufferedReader.readLine())!=null){
                sb.append(line).append("\n");
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result=sb.toString();
        connection.disconnect();
        */
    }




    private String getQuery(Record record) throws UnsupportedEncodingException{
        StringBuffer sb=new StringBuffer();
        sb.append(URLEncoder.encode(Record.ID, UTF_8));
        sb.append(EQUAL_MARK);
        sb.append(URLEncoder.encode(record.getId(),UTF_8));
        sb.append(AND_MARK);

        sb.append(URLEncoder.encode(Record.TITLE_NAME, UTF_8));
        sb.append(EQUAL_MARK);
        sb.append(URLEncoder.encode(record.getName(),UTF_8));
        sb.append(AND_MARK);

        sb.append(URLEncoder.encode(Record.TITLE_TIME, UTF_8));
        sb.append(EQUAL_MARK);
        sb.append(URLEncoder.encode(record.getTime(),UTF_8));
        sb.append(AND_MARK);

        sb.append(URLEncoder.encode(Record.TITLE_IS_PEED, UTF_8));
        sb.append(EQUAL_MARK);
        sb.append(URLEncoder.encode(record.isPeed()? STRING_TRUE : STRING_FALSE,UTF_8));
        sb.append(AND_MARK);

        sb.append(URLEncoder.encode(Record.TITLE_IS_POOPED, UTF_8));
        sb.append(EQUAL_MARK);
        sb.append(URLEncoder.encode(record.isPooped()? STRING_TRUE : STRING_FALSE,UTF_8));
        sb.append(AND_MARK);

        sb.append(URLEncoder.encode(Record.TITLE_IS_PEED_INSIDE, UTF_8));
        sb.append(EQUAL_MARK);
        sb.append(URLEncoder.encode(record.isPeedInside()? STRING_TRUE : STRING_FALSE,UTF_8));
        sb.append(AND_MARK);

        sb.append(URLEncoder.encode(Record.TITLE_IS_POOPED_INSIDE, UTF_8));
        sb.append(EQUAL_MARK);
        sb.append(URLEncoder.encode(record.isPoopedInside()? STRING_TRUE : STRING_FALSE,UTF_8));
        sb.append(AND_MARK);

        sb.append(URLEncoder.encode(Record.TITLE_IS_ATE, UTF_8));
        sb.append(EQUAL_MARK);
        sb.append(URLEncoder.encode(record.isAte()? STRING_TRUE : STRING_FALSE,UTF_8));
        sb.append(AND_MARK);

//        sb.append(URLEncoder.encode(Record.TITLE_SPOUSE_TIME, UTF_8));
//        sb.append(EQUAL_MARK);
//        sb.append(URLEncoder.encode(record.getSpouseTime(),UTF_8));
//        sb.append(AND_MARK);

        sb.append(URLEncoder.encode(Record.TITLE_DATE, UTF_8));
        sb.append(EQUAL_MARK);
        sb.append(URLEncoder.encode(record.getDate(),UTF_8));
        //sb.append(AND_MARK);

        return sb.toString();
    }


    ArrayList<Record> getFutureRecords(Record lastRecord) {
        ArrayList<Record> recordsList= new ArrayList<>(NUMBER_OF_ITEM_FOR_FUTURE);
        for(int i=0;i<4;i++){
            Record newRecord=new Record(lastRecord);
            recordsList.add(newRecord);
            lastRecord=newRecord;
        }

        return recordsList;
    }

    Collection<? extends Record> getRecordsFromDatabase() {
        JSONObject jsonObject;
        ArrayList<Record> recordArrayList=null;
        String result=getResultFromUrl(GET_INITIAL_URL);
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

    private String getResultFromUrl(String getInitialUrl) {
        URL url=null;
        HttpURLConnection connection=null;
        String result="";
        String line;
        try {
            url=new URL(getInitialUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            if (url==null) throw new NullPointerException();
            connection=(HttpURLConnection)url.openConnection();

            InputStream inputStream=new BufferedInputStream(connection.getInputStream());
            //Start to read the content
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb=new StringBuilder();
            while ((line=bufferedReader.readLine())!=null){
                sb.append(line).append("\n");
            }
            inputStream.close();
            result=sb.toString();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally{if (connection!=null) connection.disconnect();}
        return result;
    }

    private ArrayList<Record> turnJSONToArray(JSONObject jsonObject) throws JSONException {
        ArrayList<Record> newRecordArrayList=new ArrayList<>();

        for (int i=jsonObject.length(); i>0; i--){
            Record record=new Record();
            JSONObject newJson=(JSONObject) jsonObject.get(String.valueOf(i));
            record.setId(newJson.getString(Record.ID));
            record.setTime(newJson.getString(Record.TITLE_TIME));
            record.setDate(newJson.getString(Record.TITLE_DATE));
//            record.setSpouseTime(newJson.getString(Record.TITLE_SPOUSE_TIME));
            record.setName(newJson.getString(Record.TITLE_NAME));
            record.setPeed(newJson.getInt(Record.TITLE_IS_PEED)==INT_TRUE);
            record.setPooped(newJson.getInt(Record.TITLE_IS_POOPED)==INT_TRUE);
            record.setAte(newJson.getInt(Record.TITLE_IS_ATE)==INT_TRUE);
            record.setPeedInside(newJson.getInt(Record.TITLE_IS_PEED_INSIDE)==INT_TRUE);
            record.setPoopedInside(newJson.getInt(Record.TITLE_IS_POOPED_INSIDE)==INT_TRUE);
            newRecordArrayList.add(record);
        }

        return newRecordArrayList;
    }

    private void showSnackbar() {
        Snackbar.make(activity.findViewById(R.id.activity_main),getMessage(),Snackbar.LENGTH_SHORT).show();
    }
/**
    private void testSleep(){
        SystemClock.sleep(500);
    }
 **/
}
