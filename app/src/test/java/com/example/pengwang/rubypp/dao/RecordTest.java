package com.example.pengwang.rubypp.dao;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Peng on 12/27/2016.
 */
public class RecordTest {
    private Record lastRecord;

    @Before
    public void setUp() throws Exception {
        lastRecord=new Record();
        lastRecord.setSpouseTime("06:00");
        lastRecord.setDate("2016-03-16");
    }

    @Test
    public void getANewRecord(){
        Record record=new Record(lastRecord);
        Assert.assertEquals(record.getSpouseTime(),"08:00");
        Assert.assertEquals(record.getDate(),"2016-03-16");
        lastRecord.setSpouseTime("23:00");
        record=new Record(lastRecord);
        Assert.assertEquals(record.getSpouseTime(),"06:00");
        Assert.assertEquals(record.getDate(),"2016-03-17");
    }

    @Test
    public  void add_peedInside_poopedInside() throws Exception{
        Record record=new Record();
        assertFalse(record.isPeedInside());
        assertFalse(record.isPoopedInside());
        record.setPeedInside(true);
        record.setPoopedInside(true);
        assertTrue(record.isPeedInside());
        assertTrue(record.isPoopedInside());

    }

    @Test
    public void newRecordTest(){
        Record lastRecord=new Record();
        lastRecord.setTime("06:01");
        Record newRecord=new Record(lastRecord);

        assertFalse(newRecord.isRecord());
        assertEquals("08:00",newRecord.getTime());

        lastRecord.setTime("06:00");
        newRecord=new Record(lastRecord);
        assertEquals("08:00",newRecord.getTime());

        lastRecord.setTime("08:00");
        newRecord=new Record(lastRecord);
        assertEquals("11:00",newRecord.getTime());

        lastRecord.setTime("08:05");
        newRecord=new Record(lastRecord);
        assertEquals("11:00",newRecord.getTime());

        lastRecord.setTime("10:59");
        newRecord=new Record(lastRecord);
        assertEquals("11:00",newRecord.getTime());

        lastRecord.setTime("11:59");
        newRecord=new Record(lastRecord);
        assertEquals("14:00",newRecord.getTime());

        lastRecord.setTime("15:59");
        newRecord=new Record(lastRecord);
        assertEquals("17:00",newRecord.getTime());

        lastRecord.setTime("18:59");
        newRecord=new Record(lastRecord);
        assertEquals("20:00",newRecord.getTime());

        lastRecord.setTime("20:59");
        newRecord=new Record(lastRecord);
        assertEquals("23:00",newRecord.getTime());

        lastRecord.setTime("23:59");
        newRecord=new Record(lastRecord);
        assertEquals("06:00",newRecord.getTime());

        lastRecord.setTime("00:59");
        newRecord=new Record(lastRecord);
        assertEquals("06:00",newRecord.getTime());

        lastRecord.setTime("01:59");
        newRecord=new Record(lastRecord);
        assertEquals("06:00",newRecord.getTime());

        lastRecord.setTime("23:00");
        newRecord=new Record(lastRecord);
        assertEquals("06:00",newRecord.getTime());

        lastRecord.setTime("00:00");
        newRecord=new Record(lastRecord);
        assertEquals("06:00",newRecord.getTime());

        lastRecord.setTime("05:59");
        newRecord=new Record(lastRecord);
        assertEquals("06:00",newRecord.getTime());

        lastRecord.setTime("06:00");
        newRecord=new Record(lastRecord);
        assertEquals("08:00",newRecord.getTime());
    }

    @After
    public void tearDown() throws Exception {

    }

}