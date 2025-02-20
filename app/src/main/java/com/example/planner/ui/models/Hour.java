package com.example.planner.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Hour extends RealmObject{
    @PrimaryKey
    private int id;
    private String start;
    private RealmList<Integer> tasks;

    /*public Hour(int id, int number, RealmList<Integer> tasks){
        this.id=id;
        this.number=number;

        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);
    }*/

    public Hour(String start, int number){
        Realm realm=Realm.getDefaultInstance();
        Hour existingDay = realm.where(Hour.class).equalTo("start", start).findFirst();
        if (existingDay != null) {
            this.id=existingDay.getId();
            this.start=existingDay.getStart();
            this.tasks = new RealmList<>();
            this.tasks.addAll(existingDay.getTasks());
        }else{
            Number maxId = realm.where(Day.class).max("id");
            this.id = (maxId != null) ? maxId.intValue() + 1 : 1;
            this.start=start;
            this.tasks = new RealmList<>();
            addHour();
        }
    }



    /*--------------functions--------------*/

    private void addHour() {
        Realm realm = Realm.getDefaultInstance();
        Hour existingDay = realm.where(Hour.class).equalTo("start", start).findFirst();
        if (existingDay != null) {
            return;
        }
        Date date=new Date(start);
        date.setHour(0);
        String d=date.getString();
        realm.executeTransaction(r -> {
            Hour newHour = realm.createObject(Hour.class, id);
            newHour.setStart(start);
            newHour.setTasks(tasks);
        });
        new Day(d);
        realm.executeTransaction(r -> {
            Day day = realm.where(Day.class).equalTo("start", d).findFirst();
            if (day != null && !day.getHours().contains( id )) {
                day.getHours().add( id );
            }
        });
    }

    public void addTask(Task t){
        t.addTask();
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Hour hour = realm.where(Hour.class).equalTo("id", id).findFirst();
            if (hour != null && !hour.getTasks().contains( t.getId() )) {
                hour.getTasks().add( t.getId() );
            }
        });
    }

    public boolean checkDelete(){
        if(tasks.isEmpty()){
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(r -> {
                Hour hour = realm.where(Hour.class).equalTo("id", id).findFirst();
                if (hour != null) {
                    hour.deleteFromRealm();
                }
            });
            Date date=new Date(start);
            date.setHour(0);
            Day day=new Day(date.getString());
            day.checkDelete();
            return true;
        }
        return false;
    }

    public void deleteTask(int task_id){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Hour hour = realm.where(Hour.class).equalTo("id", id).findFirst();
            if (hour != null) {
                Task task = realm.where(Task.class).equalTo("id", task_id).findFirst();
                if (task != null) {
                    task.deleteFromRealm();
                }
                hour.getTasks().remove(task_id);
                checkDelete();
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

    public String getStart(){
        return start;
    }
    public void setStart(String start){
        this.start=start;
    }

    public RealmList<Integer> getTasks() {
        return tasks;
    }
    public void setTasks(RealmList<Integer> tasks) {
        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);
    }

}
