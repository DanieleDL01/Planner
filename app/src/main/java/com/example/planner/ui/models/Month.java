package com.example.planner.ui.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Month extends RealmObject{
    @PrimaryKey
    private int id;
    private String start;
    private RealmList<Integer> tasks;
    private RealmList<Integer> weeks;

    /*public Month(int id, String start, RealmList<Integer> tasks, RealmList<Integer> weeks){
        this.id=id;
        this.start=start;

        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);

        this.weeks = new RealmList<>();
        this.weeks.addAll(weeks);
    }*/

    public Month(String start){
        Realm realm=Realm.getDefaultInstance();
        Month existingMonth = realm.where(Month.class).equalTo("start", start).findFirst();
        if (existingMonth != null) {
            this.id=existingMonth.getId();
            this.start=existingMonth.getStart();

            this.tasks = new RealmList<>();
            this.weeks = new RealmList<>();
            this.tasks.addAll(existingMonth.getTasks());
            this.weeks.addAll(existingMonth.getWeeks());
        }else{
            Number maxId = realm.where(Day.class).max("id");
            this.id = (maxId != null) ? maxId.intValue() + 1 : 1;
            this.start=start;
            this.tasks = new RealmList<>();
            this.weeks = new RealmList<>();
            addMonth();
        }
    }

    /*--------------functions--------------*/


    private void addMonth() {
        Realm realm = Realm.getDefaultInstance();
        Month existingMonth = realm.where(Month.class).equalTo("start", start).findFirst();
        if (existingMonth != null) {
            return;
        }
        realm.executeTransaction(r -> {
            Month newMonth = realm.createObject(Month.class, id);
            newMonth.setStart(start);
            newMonth.setTasks(tasks);
            newMonth.setWeeks(weeks);
        });

        Date date=new Date(start);
        date.setDay(1);
        date.setMonth(1);
        date.setHour(0);
        String d=date.getString();

        new Year(d);
        realm.executeTransaction(r -> {
            Year year = realm.where(Year.class).equalTo("start", d).findFirst();
            if (year != null && !year.getMonths().contains( id )) {
                year.getMonths().add( id );
            }
        });
    }

    public void addTask(Task t){
        t.addTask();
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Month month = realm.where(Month.class).equalTo("id", id).findFirst();
            if (month != null && !month.getTasks().contains( t.getId() )) {
                month.getTasks().add( t.getId() );
            }
        });
    }

    public boolean checkDelete(){
        if(tasks.isEmpty() && weeks.isEmpty()){
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(r -> {
                Month month = realm.where(Month.class).equalTo("id", id).findFirst();
                if (month != null) {
                    month.deleteFromRealm();
                }
            });



            Date date=new Date(start);
            date.setDay(1);
            date.setMonth(1);
            date.setHour(0);
            String d=date.getString();

            Year year=new Year(d);
            year.checkDelete();
            return true;
        }
        return false;
    }

    public void deleteTask(int task_id){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Month month = realm.where(Month.class).equalTo("id", id).findFirst();
            if (month != null) {
                Task task = realm.where(Task.class).equalTo("id", task_id).findFirst();
                if (task != null) {
                    task.deleteFromRealm();
                }
                month.getTasks().remove(task_id);
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
        this.start=start;
    }

    public RealmList<Integer> getTasks() {
        return tasks;
    }
    public void setTasks(RealmList<Integer> tasks) {
        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);
    }

    public RealmList<Integer> getWeeks() {
        return weeks;
    }
    public void setWeeks(RealmList<Integer> weeks) {
        this.weeks = new RealmList<>();
        this.weeks.addAll(weeks);
    }
}

/*class MonthTask extends Task{
    public MonthTask(int id, int start, int end, int priority, String name, String description){
        super(id, start, end, priority, name, description);
    }
}*/
