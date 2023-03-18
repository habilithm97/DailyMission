package com.example.simpletodolist;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MainViewModel extends ViewModel {

    private MainRepo repo;

    public MainViewModel(MainRepo repo) {
        this.repo = repo;
    }

    public void insertItem(MainModel mainModel) {
        new InsertItemTask().execute(mainModel);
    }

    public void deleteItem(MainModel mainModel) {
        new DeleteItemTask().execute(mainModel);
    }

    public LiveData<List<MainModel>> getItems() {
        return repo.getItems();
    }

    private class InsertItemTask extends AsyncTask<MainModel, Void, Void> {

        @Override
        protected Void doInBackground(MainModel... mainModels) {
            repo.insertItem(mainModels[0]);
            return null;
        }
    }

    private class DeleteItemTask extends AsyncTask<MainModel, Void, Void> {

        @Override
        protected Void doInBackground(MainModel... mainModels) {
            repo.deleteItem(mainModels[0]);
            return null;
        }
    }
}
