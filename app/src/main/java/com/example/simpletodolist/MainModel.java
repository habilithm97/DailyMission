package com.example.simpletodolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_tb")
public class MainModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private int priority;

    public MainModel(String content, int priority) {
        this.content = content;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
