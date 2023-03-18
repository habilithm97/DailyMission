package com.example.simpletodolist;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_tb")
public class MainModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private String createInfo;

    public MainModel(int id, String content, String createInfo) {
        this.id = id;
        this.content = content;
        this.createInfo = createInfo;
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

    public String getCreateInfo() {
        return createInfo;
    }

    public void setCreateInfo(String createInfo) {
        this.createInfo = createInfo;
    }
}
