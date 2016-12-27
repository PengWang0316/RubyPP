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

    @After
    public void tearDown() throws Exception {

    }

}