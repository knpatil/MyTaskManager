package com.training.kamlakar.model;

/**
 * Created by patilk on 2/8/2017.
 */

public class Task {

    private long _id;
    private String taskTitle;
    private int taskStatus;

    public Task() {}

    public Task(String taskTitle, int taskStatus) {
        this.taskTitle = taskTitle;
        this.taskStatus = taskStatus;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }
}
