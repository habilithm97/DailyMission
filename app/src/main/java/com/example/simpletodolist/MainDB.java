package com.example.simpletodolist;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MainModel.class}, version = 1)
public abstract class MainDB extends RoomDatabase {
    public abstract MainDao mainDao;
}
