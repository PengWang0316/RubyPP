package com.example.pengwang.rubypp.dao;

import android.util.Log;

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


//    private static final String NEXT_TIME_SHOULD_BE = "Next time should be ";

    private String id="";
    private String time="";
    private boolean isPeed=false;
    private boolean isPooped=false;
    private boolean isAte=false;
    private String date="";
//    private String spouseTime="";
    private boolean isRecord=true;
    private String name="--";
    private boolean peedInside=false;
    private boolean poopedInside=false;

    private static final String TIME_FORMAT = "HH:mm";

//    private static final String DATA_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat simpleDateFormat=new SimpleDateFormat(TIME_FORMAT);
    private final static String SIX_OCLOCK="06:00";
    private final static String EIGHT_OCLOCK ="08:00";
    private final static String ELEVEN_OCLOCK="11:00";
    private final static String FOURTEEN_OCLOCK="14:00";
    private final static String SEVENTEEN_OCLOCK="17:00";
    private final static String TWENTY_OCLOCK="20:00";
    private final static String TWENTY_THREE_OCLOCK="23:00";

    public static final String ID ="id" ;
    public final static String TITLE_TIME="time";
    public final static String TITLE_DATE="date";
//    public final static String TITLE_SPOUSE_TIME="spouseTime";
    public final static String TITLE_IS_PEED="isPeed";
    public final static String TITLE_IS_POOPED="isPooped";
    public final static String TITLE_IS_ATE="isAte";
    public final static String TITLE_NAME="name";
    public final static String TITLE_IS_PEED_INSIDE="isPeedInside";
    public final static String TITLE_IS_POOPED_INSIDE="isPoopedInside";

    //private static final String TIME_FORMAT = "HH:mm";
    //private final static SimpleDateFormat timeFormat=new SimpleDateFormat(TIME_FORMAT);



//    private final static HashMap<String,String> SPOUSE_TIME_KEY_MAP=new HashMap<String,String>();
//    static {
//        SPOUSE_TIME_KEY_MAP.put(Record.SIX_OCLOCK,Record.EIGHT_OCLOCK);
//        SPOUSE_TIME_KEY_MAP.put(Record.EIGHT_OCLOCK,Record.ELEVEN_OCLOCK);
//        SPOUSE_TIME_KEY_MAP.put(Record.ELEVEN_OCLOCK,Record.FOURTEEN_OCLOCK);
//        SPOUSE_TIME_KEY_MAP.put(Record.FOURTEEN_OCLOCK,Record.SEVENTEEN_OCLOCK);
//        SPOUSE_TIME_KEY_MAP.put(Record.SEVENTEEN_OCLOCK,Record.TWENTY_OCLOCK);
//        SPOUSE_TIME_KEY_MAP.put(Record.TWENTY_OCLOCK,Record.TWENTY_THREE_OCLOCK);
//        SPOUSE_TIME_KEY_MAP.put(Record.TWENTY_THREE_OCLOCK,Record.SIX_OCLOCK);
//    }

    private final static HashMap<Calendar,Calendar> SPOUSE_TIME_KEY_MAP=new HashMap<Calendar,Calendar>();

    private static final int ADD_HOURS = 7;

    private static final int SUB_HOURS = -7;

    static {

        try {
            //Special calendar for last day
            Calendar calendarLastDayTwentyThree=Calendar.getInstance();
            calendarLastDayTwentyThree.setTime(simpleDateFormat.parse(SIX_OCLOCK));
            calendarLastDayTwentyThree.add(Calendar.HOUR_OF_DAY, SUB_HOURS);

            Calendar calendarSix=Calendar.getInstance();
            calendarSix.setTime(simpleDateFormat.parse(SIX_OCLOCK));
            Calendar calendarEight=Calendar.getInstance();
            calendarEight.setTime(simpleDateFormat.parse(EIGHT_OCLOCK));
            Calendar calendarEleven=Calendar.getInstance();
            calendarEleven.setTime(simpleDateFormat.parse(ELEVEN_OCLOCK));
            Calendar calendarFourteen=Calendar.getInstance();
            calendarFourteen.setTime(simpleDateFormat.parse(FOURTEEN_OCLOCK));
            Calendar calendarSeventeen=Calendar.getInstance();
            calendarSeventeen.setTime(simpleDateFormat.parse(SEVENTEEN_OCLOCK));
            Calendar calendarTwenty=Calendar.getInstance();
            calendarTwenty.setTime(simpleDateFormat.parse(TWENTY_OCLOCK));
            Calendar calendarTwentyThree=Calendar.getInstance();
            calendarTwentyThree.setTime(simpleDateFormat.parse(TWENTY_THREE_OCLOCK));

            //Special calendar for next day
            Calendar calendarNextDaySix=Calendar.getInstance();
            calendarNextDaySix.setTime(simpleDateFormat.parse(TWENTY_THREE_OCLOCK));
            calendarNextDaySix.add(Calendar.HOUR_OF_DAY, ADD_HOURS);

            SPOUSE_TIME_KEY_MAP.put(calendarLastDayTwentyThree,calendarSix);
            SPOUSE_TIME_KEY_MAP.put(calendarSix,calendarEight);
            SPOUSE_TIME_KEY_MAP.put(calendarEight,calendarEleven);
            SPOUSE_TIME_KEY_MAP.put(calendarEleven,calendarFourteen);
            SPOUSE_TIME_KEY_MAP.put(calendarFourteen,calendarSeventeen);
            SPOUSE_TIME_KEY_MAP.put(calendarSeventeen,calendarTwenty);
            SPOUSE_TIME_KEY_MAP.put(calendarTwenty,calendarTwentyThree);
            SPOUSE_TIME_KEY_MAP.put(calendarTwentyThree,calendarNextDaySix);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Record(){}

    public Record(Record lastRecord){
        setRecord(false);
        Calendar lastCalendar=null;
        try {
            lastCalendar= Calendar.getInstance();
            lastCalendar.setTime(simpleDateFormat.parse(lastRecord.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
            for (Calendar calendar:SPOUSE_TIME_KEY_MAP.keySet()){
                Calendar nextCalendar=SPOUSE_TIME_KEY_MAP.get(calendar);
                if (calendar.compareTo(lastCalendar)<=0 && nextCalendar.compareTo(lastCalendar)>0){
                    //TODO Find out format
                    setTime(String.format("%02d:%02d",nextCalendar.get(Calendar.HOUR_OF_DAY),nextCalendar.get(Calendar.MINUTE)));
                    break;
                }

                }

    }

    /******
     * setting up for future item record
     * //@param lastRecord the last record

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
    }*/

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        //timeFormat.applyPattern(time);
        //Subtract second from time
        if(time.length()>5) this.time = time.substring(0,5);
        else this.time=time;
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


//    public String getSpouseTime() {
//        return spouseTime;
//    }
//
//    public void setSpouseTime(String spouseTime) {
//        this.spouseTime = spouseTime;
//    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setPeedInside(boolean peedInside) {
        this.peedInside = peedInside;
    }

    public void setPoopedInside(boolean poopedInside) {
        this.poopedInside = poopedInside;
    }

    public boolean isPeedInside() {
        return peedInside;
    }

    public boolean isPoopedInside() {
        return poopedInside;
    }
}
