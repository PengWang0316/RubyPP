package com.example.pengwang.rubypp.dao;

import android.graphics.Bitmap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Peng on 12/15/2016.
 */
public class Record {
    private String time="";
    private boolean isPeed=false;
    private boolean isPooped=false;
    private boolean isAte=false;
    private String date="";
    private String spouseTime="";
    public final static String SIX_OCLOCK="06:00";
    public final static String SEVEN_OCLOCK="07:00";
    public final static String ELEVEN_OCLOCK="11:00";
    public final static String FOURTEEN_OCLOCK="14:00";
    public final static String SEVENTEEN_OCLOCK="17:00";
    public final static String TWENTY_OCLOCK="20:00";
    public final static String TWENTY_THREE_OCLOCK="23:00";

    private final static HashMap<String,String> SPOUSE_TIME_KEY_MAP=new HashMap<String,String>();
    static {
        SPOUSE_TIME_KEY_MAP.put(Record.SIX_OCLOCK,Record.SEVEN_OCLOCK);
        SPOUSE_TIME_KEY_MAP.put(Record.SEVEN_OCLOCK,Record.ELEVEN_OCLOCK);
        SPOUSE_TIME_KEY_MAP.put(Record.ELEVEN_OCLOCK,Record.FOURTEEN_OCLOCK);
        SPOUSE_TIME_KEY_MAP.put(Record.FOURTEEN_OCLOCK,Record.SEVENTEEN_OCLOCK);
        SPOUSE_TIME_KEY_MAP.put(Record.SEVENTEEN_OCLOCK,Record.TWENTY_OCLOCK);
        SPOUSE_TIME_KEY_MAP.put(Record.TWENTY_OCLOCK,Record.TWENTY_THREE_OCLOCK);
        SPOUSE_TIME_KEY_MAP.put(Record.TWENTY_THREE_OCLOCK,Record.SIX_OCLOCK);
    }

    public Record(){}

    /******
     * setting up for future item record
     * @param lastRecord
     */
    public Record(Record lastRecord){
        String spouseTime=SPOUSE_TIME_KEY_MAP.get(lastRecord.getSpouseTime());
        setSpouseTime(spouseTime);
        setTime(spouseTime);
        if (spouseTime==SIX_OCLOCK){
            //set a new day
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date = format.parse(lastRecord.getDate());
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH,1);
                setDate(format.format(calendar.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else setDate(lastRecord.getDate());
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isPeed() {
        return isPeed;
    }

    public void setPeed(boolean peed) {
        isPeed = peed;
    }

    public boolean isPooped() {
        return isPooped;
    }

    public void setPooped(boolean pooped) {
        isPooped = pooped;
    }

    public boolean isAte() {
        return isAte;
    }

    public void setAte(boolean ate) {
        isAte = ate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getSpouseTime() {
        return spouseTime;
    }

    public void setSpouseTime(String spouseTime) {
        this.spouseTime = spouseTime;
    }
}
