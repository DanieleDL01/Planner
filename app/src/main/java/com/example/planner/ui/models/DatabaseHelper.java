package com.example.planner.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper{
    private List<Year> years;
    private List<Week> weeks;
    private List<Task> tasks;
    public DatabaseHelper(){
        this.years=new ArrayList<>();
        this.weeks=new ArrayList<>();
        this.tasks=new ArrayList<>();
    }

    public void addDayTask(Day d){

    }
}


