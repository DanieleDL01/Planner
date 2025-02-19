package com.example.planner.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Day extends RealmObject{
    @PrimaryKey
    private int id;
    private Date date;
    private RealmList<Integer> tasks;
    private RealmList<Integer> hours;

    public Day(int id, Date date, RealmList<Integer> tasks, RealmList<Integer> hours){
        this.id=id;
        this.date=date;

        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);

        this.hours = new RealmList<>();
        this.hours.addAll(hours);
    }

    public Day(Date date, List<Integer> tasks, List<Integer> hours){
        Realm realm=Realm.getDefaultInstance();
        Number maxId = realm.where(Day.class).max("id");
        this.id = (maxId != null) ? maxId.intValue() + 1 : 1;
        this.date=date;

        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);

        this.hours = new RealmList<>();
        this.hours.addAll(hours);
    }



    /*--------------functions--------------*/

    public void addDay() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Day newDay = realm.createObject(Day.class, id);
            newDay.setDate(date);
            newDay.setTasks(tasks);
            newDay.setHours(hours);
        });
    }

    public void addTask(Task t){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Day day = realm.where(Day.class).equalTo("id", id).findFirst();
            if (day != null) {
                //day.setDescription("Updated description.");
            }
        });
    }



    /*--------------setter & getter--------------*/

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public RealmList<Integer> getTasks() {
        return tasks;
    }
    public void setTasks(RealmList<Integer> tasks) {
        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);
    }

    public RealmList<Integer> getHours() {
        return hours;
    }
    public void setHours(RealmList<Integer> hours) {
        this.hours = new RealmList<>();
        this.hours.addAll(hours);
    }
}

/*class DayTask extends Task{
    public DayTask(int id, int start, int end, int priority, String name, String description){
        super(id, start, end, priority, name, description);
    }
}*/