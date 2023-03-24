package com.example.simpletodolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    public static final int ADD_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        MainAdapter adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class); // ViewModel 객체 생성
        // List에 포함된 객체들을 관찰함
        mainViewModel.getAllItems().observe(this, new Observer<List<MainModel>>() {
            @Override
            public void onChanged(List<MainModel> mainModels) { // List의 DataSet이 변경될 경우 호출됨
                adapter.setItems(mainModels); // 객체들(List)의 변화를 어댑터에 적용함으로써 데이터 값이 변경될 때마다 UI에 실시간으로 나타낼 수 있음
            }
        });

        FloatingActionButton floatingBtn = findViewById(R.id.floatingBtn);
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            String content = data.getStringExtra(AddActivity.EXTRA_CONTENT);

            MainModel mainModel = new MainModel(content);
            mainViewModel.insert(mainModel);

            Toast.makeText(this, "할 일이 추가되었습니다. ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "할 일이 추가되지 않았습니다. ", Toast.LENGTH_SHORT).show();
        }
    }
}