package com.example.pengwang.rubypp.utils;

import android.support.v7.widget.RecyclerView;

import com.example.pengwang.rubypp.dao.Record;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Peng on 12/16/2016.
 * Util class that will be used to get data from database.
 */
public class SQLUtil {
    private final static int NUMBER_OF_ITEM_FOR_FUTURE=5;


    //Getting records from the database.
    public static void getInitialRecordsFromDatabase(RecyclerView recyclerView,ArrayList<Record> recordArrayList) {
        recordArrayList.addAll(getRecordsFromDatabase());
        recordArrayList.addAll(getFutureRecords(recordArrayList.get(recordArrayList.size()-1)));
        recyclerView.scrollToPosition(recordArrayList.size()-1);
    }

    private static ArrayList<Record> getFutureRecords(Record lastRecord) {
        ArrayList<Record> recordsList= new ArrayList<>(NUMBER_OF_ITEM_FOR_FUTURE);
        for(int i=0;i<4;i++){
            Record newRecord=new Record(lastRecord);
            recordsList.add(newRecord);
            lastRecord=newRecord;
        }

        return recordsList;
    }

    private static Collection<? extends Record> getRecordsFromDatabase() {
        ArrayList<Record> recordArrayList=new ArrayList<>();
        Record r1=new Record();
        Record r2=new Record();
        Record r3=new Record();
        Record r4=new Record();
        Record r5=new Record();
        Record r6=new Record();

        r1.setTime("5:00");
        r1.setPeed(true);
        r1.setDate("03/12/2016");
        r1.setSpouseTime(Record.SIX_OCLOCK);
        r2.setTime("7:00");
        r2.setPooped(true);
        r2.setAte(true);
        r2.setDate("03/12/2016");
        r2.setSpouseTime(Record.SEVEN_OCLOCK);
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
        r6.setTime("20:00");
        r6.setPeed(true);
        r6.setDate("03/12/2016");
        r6.setSpouseTime(Record.TWENTY_OCLOCK);
        recordArrayList.add(r1);
        recordArrayList.add(r2);

        /*
        for (int i=0;i<50;i++){
            Record r4=new Record();
            r4.setTime("5 AM");
            r4.setPeed(true);
            r4.setPooped(true);
            r4.setAte(true);
            r4.setDate("March 12 2016");
            recordArrayList.add(r4);
        }
        */
        recordArrayList.add(r3);
        recordArrayList.add(r4);
        recordArrayList.add(r5);
        recordArrayList.add(r6);
        return recordArrayList;
    }
}
