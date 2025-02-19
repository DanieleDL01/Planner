package com.example.planner.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Week extends RealmObject{
    @PrimaryKey
    private int id;
    private Date start;
    private RealmList<Integer> tasks;
    private RealmList<Integer> days;

    public Week(int id, Date start, RealmList<Integer> tasks, RealmList<Integer> days){
        this.id=id;
        this.start=start;

        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);

        this.days = new RealmList<>();
        this.days.addAll(days);
    }



    /*--------------functions--------------*/



    /*--------------setter & getter--------------*/

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }
    public void setStart(Date start){
        this.start=new Date(start);
    }

    public RealmList<Integer> getTasks() {
        return tasks;
    }
    public void setTasks(RealmList<Integer> tasks) {
        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);
    }

    public RealmList<Integer> getDays() {
        return days;
    }
    public void setMonths(RealmList<Integer> days) {
        this.days = new RealmList<>();
        this.days.addAll(days);
    }
}

/*class WeekTask extends Task{
    public WeekTask(int id, int start, int end, int priority, String name, String description){
        super(id, start, end, priority, name, description);
    }
}*/
