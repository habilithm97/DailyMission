package com.example.simpletodolist;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {MainModel.class}, version = 1)
public abstract class MainDB extends RoomDatabase {

    private static MainDB instance;
    private static String DB_NAME = "my_db";

    public abstract MainDao mainDao();

    // DB를 처음 생성할 때에는 해당 작업이 먼저 이뤄져야하므로 동기화를 시킴
    public static synchronized MainDB getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MainDB.class, DB_NAME)
                    .fallbackToDestructiveMigration() // 버전이 변경되었을 때 이전의 테이블을 모두 삭제하고 새로 생성함
                    .addCallback(roomCallback) // Callback 메소드로 onCreate를 작성하면 최초 DB 생성 시의 작업을 선언할 수 있음
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new InitAsyncTask(instance).execute();
        }
    };

    private static class InitAsyncTask extends AsyncTask<Void, Void, Void> {
        private MainDao mainDao;

        private InitAsyncTask(MainDB mainDB) {
            mainDao = mainDB.mainDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mainDao.insert(new MainModel("공부", 1));
            mainDao.insert(new MainModel("운동", 2));
            mainDao.insert(new MainModel("게임", 3));
            return null;
        }
    }
}
