package com.example.simpletodolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {

    @Insert(onConflict = REPLACE)
    void insert(MainModel mainModel);

    @Delete
    void delete(MainModel mainModel);

    @Query("SELECT * FROM my_tb")
    LiveData<List<MainModel>> getAll();
}
