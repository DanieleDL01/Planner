package com.example.planner.ui.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Calendar;

public class Date {
    private int y;
    private int m;
    private int d;

    public Date(){
        Calendar calendario = Calendar.getInstance();
        d = calendario.get(Calendar.DAY_OF_MONTH);
        m = calendario.get(Calendar.MONTH) + 1;
        y = calendario.get(Calendar.YEAR);
    }

    public Date(int d, int m, int y){
        this.d=d;
        this.m=m;
        this.y=y;
    }

    public Date(Date date){
        this.d=date.d;
        this.m=date.m;
        this.y=date.y;
    }

    public int getDay(){return d;}
    public int getMonth(){return m;}
    public int getYear(){return y;}

    public void setDay(int d){this.d=d;}
    public void setMonth(int m){this.m=m;}
    public void setYear(int y){this.y=y;}
}
