package com.example.planner.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Week extends RealmObject{
    @PrimaryKey
    private int id;
    private String start;
    private RealmList<Integer> tasks;
    private RealmList<Integer> days;

    /*public Week(int id, String start, RealmList<Integer> tasks, RealmList<Integer> days){
        this.id=id;
        this.start=start;

        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);

        this.days = new RealmList<>();
        this.days.addAll(days);
    }*/

    public Week(String start){
        Realm realm=Realm.getDefaultInstance();
        Week existingWeek = realm.where(Week.class).equalTo("start", start).findFirst();
        if (existingWeek != null) {
            this.id=existingWeek.getId();
            this.start=existingWeek.getStart();

            this.tasks = new RealmList<>();
            this.days = new RealmList<>();
            this.tasks.addAll(existingWeek.getTasks());
            this.days.addAll(existingWeek.getDays());
        }else{
            Number maxId = realm.where(Day.class).max("id");
            this.id = (maxId != null) ? maxId.intValue() + 1 : 1;
            this.start=start;
            this.tasks = new RealmList<>();
            this.days = new RealmList<>();
            addWeek();
        }
    }



    /*--------------functions--------------*/

    private void addWeek() {
        Realm realm = Realm.getDefaultInstance();
        Week existingWeek = realm.where(Week.class).equalTo("start", start).findFirst();
        if (existingWeek != null) {
            return;
        }
        realm.executeTransaction(r -> {
            Week newWeek = realm.createObject(Week.class, id);
            newWeek.setStart(start);
            newWeek.setTasks(tasks);
            newWeek.setDays(days);
        });

        Date date=new Date(start);

        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth(), date.getDay(), date.getHour(), 0, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 6);
        date.setDay(1);
        Date date_e=new Date(calendar);

        realm.executeTransaction(r -> {
            new Month(date.getString());
            Month month = realm.where(Month.class).equalTo("start", date.getString()).findFirst();
            if (month != null && !month.getWeeks().contains( id )) {
                month.getWeeks().add( id );
            }

            new Month(date_e.getString());
            Month month2 = realm.where(Month.class).equalTo("start", date_e.getString()).findFirst();
            if (month2 != null && !month2.getWeeks().contains( id )) {
                month2.getWeeks().add( id );
            }
        });
    }

    public void addTask(Task t){
        t.addTask();
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Week week = realm.where(Week.class).equalTo("id", id).findFirst();
            if (week != null && !week.getTasks().contains( t.getId() )) {
                week.getTasks().add( t.getId() );
            }
        });
    }

    public boolean checkDelete(){
        if(tasks.isEmpty() && days.isEmpty()){
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(r -> {
                Week week = realm.where(Week.class).equalTo("id", id).findFirst();
                if (week != null) {
                    week.deleteFromRealm();
                }
            });

            Date date=new Date(start);
            Calendar calendar = Calendar.getInstance();
            calendar.set(date.getYear(), date.getMonth(), date.getDay(), date.getHour(), 0, 0);
            calendar.add(Calendar.DAY_OF_MONTH, 6);
            date.setDay(1);
            Date date_e=new Date(calendar);

            Month month1=new Month(date.getString());
            month1.checkDelete();
            Month month2=new Month(date_e.getString());
            month2.checkDelete();
        }
        return false;
    }

    public void deleteTask(int task_id){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Week week = realm.where(Week.class).equalTo("id", id).findFirst();
            if (week != null) {
                Task task = realm.where(Task.class).equalTo("id", task_id).findFirst();
                if (task != null) {
                    task.deleteFromRealm();
                }
                week.getTasks().remove(task_id);
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

    public RealmList<Integer> getDays() {
        return days;
    }
    public void setDays(RealmList<Integer> days) {
        this.days = new RealmList<>();
        this.days.addAll(days);
    }
}

/*class WeekTask extends Task{
    public WeekTask(int id, int start, int end, int priority, String name, String description){
        super(id, start, end, priority, name, description);
    }
}*/
