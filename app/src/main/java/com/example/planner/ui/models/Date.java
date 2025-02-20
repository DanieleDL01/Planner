package com.example.planner.ui.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Date {
    private int y;
    private int m;
    private int d;
    private int h;


    public Date(){
        Calendar calendar = Calendar.getInstance();
        d = calendar.get(Calendar.DAY_OF_MONTH);
        m = calendar.get(Calendar.MONTH) + 1;
        y = calendar.get(Calendar.YEAR);
        h = calendar.get(Calendar.HOUR_OF_DAY);
    }

    public Date(Calendar calendar){
        d = calendar.get(Calendar.DAY_OF_MONTH);
        m = calendar.get(Calendar.MONTH) + 1;
        y = calendar.get(Calendar.YEAR);
        h = calendar.get(Calendar.HOUR_OF_DAY);
    }
    public Date(int d, int m, int y, int h){
        this.d=d;
        this.m=m;
        this.y=y;
        this.h=h;
    }

    public Date(Date date){
        this.d=date.d;
        this.m=date.m;
        this.y=date.y;
        this.h=date.h;
    }

    public Date(String dateString){
        String[] parts = dateString.split("[- ]");
        this.d= Integer.parseInt(parts[0]);
        this.m= Integer.parseInt(parts[1]);
        this.y= Integer.parseInt(parts[2]);
        this.h= Integer.parseInt(parts[3]);
    }


    /*--------------functions--------------*/

    public String getString(){
        return String.format("%02d-%02d-%04d %02d", d, m, y, h);
    }

    public int hourDiff(Date date){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(this.y, this.m, this.d, this.h, 0, 0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(date.y, date.m, date.d, date.h, 0, 0);

        long diffMillis = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
        long differenzaOre = TimeUnit.MILLISECONDS.toHours(diffMillis);
        return (int)differenzaOre;
    }

    /*--------------setter & getter--------------*/
    public int getDay(){return d;}
    public int getMonth(){return m;}
    public int getYear(){return y;}
    public int getHour(){return h;}

    public void setDay(int d){this.d=d;}
    public void setMonth(int m){this.m=m;}
    public void setYear(int y){this.y=y;}
    public void setHour(int h){this.h=h;}
}
