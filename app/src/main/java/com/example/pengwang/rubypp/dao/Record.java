package com.example.pengwang.rubypp.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Peng on 12/15/2016.
 * Rcord data
 */
public class Record {

    private String time="";
    private boolean isPeed=false;
    private boolean isPooped=false;
    private boolean isAte=false;
    private String date="";
    private String spouseTime="";
    private boolean isRecord=true;
    private String name="--";

    private static final String DATA_FORMAT = "yyyy-MM-dd";
    public final static String SIX_OCLOCK="06:00";
    public final static String EIGHT_OCLOCK ="08:00";
    public final static String ELEVEN_OCLOCK="11:00";
    public final static String FOURTEEN_OCLOCK="14:00";
    public final static String SEVENTEEN_OCLOCK="17:00";
    public final static String TWENTY_OCLOCK="20:00";
    public final static String TWENTY_THREE_OCLOCK="23:00";

    public final static String TITLE_TIME="time";
    public final static String TITLE_DATE="date";
    public final static String TITLE_SPOUSE_TIME="spouseTime";
    public final static String TITLE_IS_PEED="isPeed";
    public final static String TITLE_IS_POOPED="isPooped";
    public final static String TITLE_IS_ATE="isAte";
    public final static String TITLE_NAME="name";

    //private static final String TIME_FORMAT = "HH:mm";
    //private final static SimpleDateFormat timeFormat=new SimpleDateFormat(TIME_FORMAT);



    private final static HashMap<String,String> SPOUSE_TIME_KEY_MAP=new HashMap<String,String>();
    static {
        SPOUSE_TIME_KEY_MAP.put(Record.SIX_OCLOCK,Record.EIGHT_OCLOCK);
        SPOUSE_TIME_KEY_MAP.put(Record.EIGHT_OCLOCK,Record.ELEVEN_OCLOCK);
        SPOUSE_TIME_KEY_MAP.put(Record.ELEVEN_OCLOCK,Record.FOURTEEN_OCLOCK);
        SPOUSE_TIME_KEY_MAP.put(Record.FOURTEEN_OCLOCK,Record.SEVENTEEN_OCLOCK);
        SPOUSE_TIME_KEY_MAP.put(Record.SEVENTEEN_OCLOCK,Record.TWENTY_OCLOCK);
        SPOUSE_TIME_KEY_MAP.put(Record.TWENTY_OCLOCK,Record.TWENTY_THREE_OCLOCK);
        SPOUSE_TIME_KEY_MAP.put(Record.TWENTY_THREE_OCLOCK,Record.SIX_OCLOCK);
    }

    public Record(){}

    /******
     * setting up for future item record
     * @param lastRecord the last record
     */
    public Record(Record lastRecord){
        String spouseTime=SPOUSE_TIME_KEY_MAP.get(lastRecord.getSpouseTime());
        setSpouseTime(spouseTime);
        setTime(spouseTime);
        setRecord(false);
        if (spouseTime.equals(SIX_OCLOCK)){
            //set a new day
            SimpleDateFormat format = new SimpleDateFormat(DATA_FORMAT);
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
        //timeFormat.applyPattern(time);
        //Subtract second from time
        this.time = time.substring(0,5);
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

    public boolean isRecord() {
        return isRecord;
    }

    public void setRecord(boolean record) {
        isRecord = record;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
