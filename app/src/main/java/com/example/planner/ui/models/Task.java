package com.example.planner.ui.models;

import java.time.Period;
import java.util.List;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import io.realm.Realm;
import io.realm.RealmResults;

public class Task extends RealmObject{
    @PrimaryKey
    private int id;
    private int priority;
    private String start;
    private String end;
    private String name;
    private String description;

    public Task(int id, int priority, String start, String end, String name, String description){
        this.id=id;
        this.priority=priority;
        this.start=start;
        this.end=end;
        this.name=name;
        this.description=description;
    }

    public Task(int priority, String start, String end, String name, String description){
        Realm realm=Realm.getDefaultInstance();
        Number maxId = realm.where(Task.class).max("id");
        this.id = (maxId != null) ? maxId.intValue() + 1 : 1;

        this.priority=priority;
        this.start=start;
        this.end=end;
        this.name=name;
        this.description=description;
    }



/*--------------functions--------------*/

    public void addTask(){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Task newTask = realm.createObject(Task.class, id);
            newTask.setPriority(priority);
            newTask.setStart(start);
            newTask.setEnd(end);
            newTask.setName(name);
            newTask.setDescription(description);
        });
    }

    public void deleteTask(){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Task newTask = realm.createObject(Task.class, id);
            newTask.setPriority(priority);
            newTask.setStart(start);
            newTask.setEnd(end);
            newTask.setName(name);
            newTask.setDescription(description);
        });
    }




/*--------------setter & getter--------------*/

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStart(){
        return start;
    }
    public void setStart(String start){
        this.start=start;
    }

    public String getEnd(){
        return end;
    }
    public void setEnd(String end){
        this.end=end;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }





}
