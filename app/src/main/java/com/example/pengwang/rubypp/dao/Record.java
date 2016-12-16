package com.example.pengwang.rubypp.dao;

import android.graphics.Bitmap;

/**
 * Created by Peng on 12/15/2016.
 */
public class Record {
    private String time="";
    private boolean isPeed=false;
    private boolean isPooped=false;
    private boolean isAte=false;
    private String date="";

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


}
