package com.example.planner.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Day extends RealmObject{
    @PrimaryKey
    private int id;
    private String start;
    private RealmList<Integer> tasks;
    private RealmList<Integer> hours;

    /*public Day(int id, String date, RealmList<Integer> tasks, RealmList<Integer> hours){
        this.id=id;
        this.date=date;

        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);

        this.hours = new RealmList<>();
        this.hours.addAll(hours);
    }*/

    /*public Day(String date, List<Integer> tasks, List<Integer> hours){
        Realm realm=Realm.getDefaultInstance();
        Number maxId = realm.where(Day.class).max("id");
        this.id = (maxId != null) ? maxId.intValue() + 1 : 1;
        this.date=date;

        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);

        this.hours = new RealmList<>();
        this.hours.addAll(hours);
    }*/

    public Day(String start){
        Realm realm=Realm.getDefaultInstance();
        Day existingDay = realm.where(Day.class).equalTo("start", start).findFirst();
        if (existingDay != null) {
            this.id=existingDay.getId();
            this.start=existingDay.getStart();

            this.tasks = new RealmList<>();
            this.hours = new RealmList<>();
            this.tasks.addAll(existingDay.getTasks());
            this.hours.addAll(existingDay.getHours());
        }else{
            Number maxId = realm.where(Day.class).max("id");
            this.id = (maxId != null) ? maxId.intValue() + 1 : 1;
            this.start=start;
            this.tasks = new RealmList<>();
            this.hours = new RealmList<>();
            addDay();
        }
    }



    /*--------------functions--------------*/

    private void addDay() {
        Realm realm = Realm.getDefaultInstance();
        Day existingDay = realm.where(Day.class).equalTo("start", start).findFirst();
        if (existingDay != null) {
            return;
        }
        realm.executeTransaction(r -> {
            Day newDay = realm.createObject(Day.class, id);
            newDay.setStart(start);
            newDay.setTasks(tasks);
            newDay.setHours(hours);
        });

        Date date=new Date(start);
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth(), date.getDay(), date.getHour(), 0, 0);

        int giornoSettimana = calendar.get(Calendar.DAY_OF_WEEK);
        if (giornoSettimana != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, - ((giornoSettimana + 5) % 7));
        }
        String d=new Date(calendar).getString();
        new Week(d);
        realm.executeTransaction(r -> {
            Week week = realm.where(Week.class).equalTo("start", d).findFirst();
            if (week != null && !week.getDays().contains( id )) {
                week.getDays().add( id );
            }
        });
    }

    public void addTask(Task t){
        t.addTask();
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Day day = realm.where(Day.class).equalTo("id", id).findFirst();
            if (day != null && !day.getTasks().contains( t.getId() )) {
                day.getTasks().add( t.getId() );
            }
        });
    }

    public boolean checkDelete(){
        if(tasks.isEmpty() && hours.isEmpty()){
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(r -> {
                Day day = realm.where(Day.class).equalTo("id", id).findFirst();
                if (day != null) {
                    day.deleteFromRealm();
                }
            });

            Date date=new Date(start);
            Calendar calendar = Calendar.getInstance();
            calendar.set(date.getYear(), date.getMonth(), date.getDay(), date.getHour(), 0, 0);
            int giornoSettimana = calendar.get(Calendar.DAY_OF_WEEK);
            if (giornoSettimana != Calendar.MONDAY) {
                calendar.add(Calendar.DAY_OF_MONTH, - ((giornoSettimana + 5) % 7));
            }
            String d=new Date(calendar).getString();
            Week week=new Week(d);
            week.checkDelete();
            return true;
        }
        return false;
    }

    public void deleteTask(int task_id){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Day day = realm.where(Day.class).equalTo("id", id).findFirst();
            if (day != null) {
                Task task = realm.where(Task.class).equalTo("id", task_id).findFirst();
                if (task != null) {
                    task.deleteFromRealm();
                }
                day.getTasks().remove(task_id);
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

    public String getStart() {
        return start;
    }
    public void setStart(String start) {
        this.start = start;
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