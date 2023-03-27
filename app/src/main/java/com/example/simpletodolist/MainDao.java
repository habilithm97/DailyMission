package com.example.simpletodolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {

    @Insert(onConflict = REPLACE) // PrimaryKey가 겹치면 덮어 씀
    void insert(MainModel mainModel);

    @Delete
    void delete(MainModel mainModel);

    @Update
    void update(MainModel mainModel);

    @Query("SELECT * FROM my_tb")
    LiveData<List<MainModel>> getAll();
}
