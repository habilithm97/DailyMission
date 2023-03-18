package com.example.simpletodolist;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MainRepo {

    private MainDao mainDao;

    public MainRepo(MainDao mainDao) {
        this.mainDao = mainDao;
    }

    public void insertItem(MainModel mainModel){
        mainDao.insert(mainModel);
    }

    public void deleteItem(MainModel mainModel){
        mainDao.delete(mainModel);
    }

    public LiveData<List<MainModel>> getAll() {
        return mainDao.getAll();
    }
}
