package com.example.simpletodolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// UI에 매칭시킬 데이터를 저장 및 처리하는 ViewModel 클래스
// Repository와 UI의 다리 역할을 함
// Observe를 통해 DataSet의 변화를 관찰하여 UI에 적용하는 역할을 함
public class MainViewModel extends AndroidViewModel {

    private MainRepo mainRepo;
    private LiveData<List<MainModel>> allItems;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepo = new MainRepo(application);
        allItems = mainRepo.getAllItems();
    }

    public void insert(MainModel mainModel) {
        mainRepo.insert(mainModel);
    }

    public void delete(MainModel mainModel) {
        mainRepo.delete(mainModel);
    }

    public LiveData<List<MainModel>> getAllItems() {
        return allItems;
    }
}
