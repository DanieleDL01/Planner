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
    private int number;
    private RealmList<Integer> tasks;

    public Hour(int id, int number, RealmList<Integer> tasks){
        this.id=id;
        this.number=number;

        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);
    }



    /*--------------functions--------------*/



    /*--------------setter & getter--------------*/

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public RealmList<Integer> getTasks() {
        return tasks;
    }
    public void setTasks(RealmList<Integer> tasks) {
        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);
    }

}
