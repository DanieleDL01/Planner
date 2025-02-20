package com.example.planner.ui.models;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Year extends RealmObject{
    @PrimaryKey
    private int id;
    private String start;
    private RealmList<Integer> tasks;
    private RealmList<Integer> months;

    /*public Year(int id, RealmList<Integer> tasks, RealmList<Integer> months){
        this.id=id;

        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);

        this.months = new RealmList<>();
        this.months.addAll(months);
    }*/

    public Year(String start){
        Realm realm=Realm.getDefaultInstance();
        Year existingYear = realm.where(Year.class).equalTo("start", start).findFirst();
        if (existingYear != null) {
            this.id=existingYear.getId();
            this.start=existingYear.getStart();

            this.tasks = new RealmList<>();
            this.months = new RealmList<>();
            this.tasks.addAll(existingYear.getTasks());
            this.months.addAll(existingYear.getMonths());
        }else{
            Number maxId = realm.where(Day.class).max("id");
            this.id = (maxId != null) ? maxId.intValue() + 1 : 1;
            this.start=start;
            this.tasks = new RealmList<>();
            this.months = new RealmList<>();
            addYear();
        }
    }

    /*--------------functions--------------*/

    private void addYear() {
        Realm realm = Realm.getDefaultInstance();
        Year existingYear = realm.where(Year.class).equalTo("start", start).findFirst();
        if (existingYear != null) {
            return;
        }
        realm.executeTransaction(r -> {
            Year newYear = realm.createObject(Year.class, id);
            newYear.setStart(start);
            newYear.setTasks(tasks);
            newYear.setMonths(months);
        });
    }

    public void addTask(Task t){
        t.addTask();
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Year year = realm.where(Year.class).equalTo("id", id).findFirst();
            if (year != null && !year.getTasks().contains( t.getId() )) {
                year.getTasks().add( t.getId() );
            }
        });
    }

    public boolean checkDelete(){
        if(tasks.isEmpty() && months.isEmpty()){
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(r -> {
                Month month = realm.where(Month.class).equalTo("id", id).findFirst();
                if (month != null) {
                    month.deleteFromRealm();
                }
            });
            return true;
        }
        return false;
    }

    public void deleteTask(int task_id){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Year year= realm.where(Year.class).equalTo("id", id).findFirst();
            if (year != null) {
                Task task = realm.where(Task.class).equalTo("id", task_id).findFirst();
                if (task != null) {
                    task.deleteFromRealm();
                }
                year.getTasks().remove(task_id);
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

    public List<Integer> getTasks() {
        return tasks;
    }
    public void setTasks(RealmList<Integer> tasks) {
        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);
    }

    public RealmList<Integer> getMonths() {
        return months;
    }
    public void setMonths(RealmList<Integer> months) {
        this.months = new RealmList<>();
        this.months.addAll(months);
    }
}

/*class YearTask extends Task{
    public YearTask(int id, int start, int end, int priority, String name, String description){
        super(id, start, end, priority, name, description);
    }
}*/