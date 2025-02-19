package com.example.planner.ui.models;

import java.util.ArrayList;
import java.util.List;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Year extends RealmObject{
    @PrimaryKey
    private int id;
    private RealmList<Integer> tasks;
    private RealmList<Integer> months;

    public Year(int id, RealmList<Integer> tasks, RealmList<Integer> months){
        this.id=id;

        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);

        this.months = new RealmList<>();
        this.months.addAll(months);
    }



    /*--------------functions--------------*/



    /*--------------setter & getter--------------*/

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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