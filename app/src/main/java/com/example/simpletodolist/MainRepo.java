package com.example.simpletodolist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

// 로컬 DB인 Room과 ViewModel 간의 다리 역할을 하는 Repository 클래스
// DB의 상태 변화를 담당하는 Dao의 메소드 호출은 AsyncTask로 진행함
public class MainRepo {

    private MainDao mainDao;
    private LiveData<List<MainModel>> allItems;

    public MainRepo(Application application) {
        MainDB mainDB = MainDB.getInstance(application);
        mainDao = mainDB.mainDao();
        allItems = mainDao.getAll();
    }

    public void insert(MainModel mainModel) {
        new InsertItemTask(mainDao).execute(mainModel);
    }

    public void delete(MainModel mainModel) {
        new DeleteItemTask(mainDao).execute(mainModel);
    }

    public void update(MainModel mainModel) {
        new UpdateItemTask(mainDao).execute(mainModel);
    }

    public void deleteAll() {
        new DeleteAllItemsTask(mainDao).execute();
    }

    public LiveData<List<MainModel>> getAllItems() {
        return allItems;
    }

    private static class InsertItemTask extends AsyncTask<MainModel, Void, Void> {

        private MainDao mainDao;

        public InsertItemTask(MainDao mainDao) {
            this.mainDao = mainDao;
        }

        @Override
        protected Void doInBackground(MainModel... mainModels) {
            mainDao.insert(mainModels[0]);
            return null;
        }
    }

    private static class DeleteItemTask extends AsyncTask<MainModel, Void, Void> {

        private MainDao mainDao;

        public DeleteItemTask(MainDao mainDao) {
            this.mainDao = mainDao;
        }

        @Override
        protected Void doInBackground(MainModel... mainModels) {
            mainDao.delete(mainModels[0]);
            return null;
        }
    }

    private static class UpdateItemTask extends AsyncTask<MainModel, Void, Void> {

        private MainDao mainDao;

        public UpdateItemTask(MainDao mainDao) {
            this.mainDao = mainDao;
        }

        @Override
        protected Void doInBackground(MainModel... mainModels) {
            mainDao.update(mainModels[0]);
            return null;
        }
    }

    private static class DeleteAllItemsTask extends AsyncTask<MainModel, Void, Void> {
        private MainDao mainDao;

        private DeleteAllItemsTask(MainDao mainDao) {
            this.mainDao = mainDao;
        }

        @Override
        protected Void doInBackground(MainModel... mainModels) {
            mainDao.deleteAll();
            return null;
        }
    }
}
