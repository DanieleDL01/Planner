package com.example.planner.ui.models;

import java.util.ArrayList;
import java.util.List;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Month extends RealmObject{
    @PrimaryKey
    private int id;
    private int number;
    private RealmList<Integer> tasks;
    private RealmList<Integer> weeks;

    public Month(int id, int number, RealmList<Integer> tasks, RealmList<Integer> weeks){
        this.id=id;
        this.number=number;

        this.tasks = new RealmList<>();
        this.tasks.addAll(tasks);

        this.weeks = new RealmList<>();
        this.weeks.addAll(weeks);
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
