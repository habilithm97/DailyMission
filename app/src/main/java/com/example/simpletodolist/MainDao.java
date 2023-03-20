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

    @Insert(onConflict = REPLACE) // PrimaryKey가 겹치면 덮어 씀
    void insert(MainModel mainModel);

    @Delete
    void delete(MainModel mainModel);

    @Query("SELECT * FROM my_tb ORDER BY priority DESC")
    LiveData<List<MainModel>> getAll();
}
