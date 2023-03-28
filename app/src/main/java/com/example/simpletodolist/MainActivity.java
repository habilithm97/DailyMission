package com.example.simpletodolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_REQUEST = 1;
    public static final int EDIT_REQUEST = 2;

    private MainViewModel mainViewModel;

    private Toast toast;

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
                //adapter.setItems(mainModels); // 객체들(List)의 변화를 어댑터에 적용함으로써 데이터 값이 변경될 때마다 UI에 실시간으로 나타낼 수 있음
                adapter.submitList(mainModels); // 리스트 데이터 업데이트
            }
        });

        FloatingActionButton floatingBtn = findViewById(R.id.floatingBtn);
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                startActivityForResult(intent, ADD_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mainViewModel.delete(adapter.getPosition(viewHolder.getAdapterPosition()));
                showToast(getApplicationContext(), "삭제되었습니다. ");
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(MainModel mainModel) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra(AddEditActivity.EXTRA_ID, mainModel.getId());
                intent.putExtra(AddEditActivity.EXTRA_CONTENT, mainModel.getContent());
                startActivityForResult(intent, EDIT_REQUEST);
            }
        });
    }

    private void showToast(Context context, String msg) {
        if(toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else { // 기존에 토스트 객체가 있으면 추가 생성하지 않음
            toast.setText(msg);
        }
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 할 일 추가하기
        if(requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            String content = data.getStringExtra(AddEditActivity.EXTRA_CONTENT);

            MainModel mainModel = new MainModel(content);
            mainViewModel.insert(mainModel);

            Toast.makeText(this, "할 일이 저장되었습니다. ", Toast.LENGTH_SHORT).show();

        // 할 일 수정하기
        } else if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditActivity.EXTRA_ID, -1);

            if(id == -1) { // id가 -1이면
                Toast.makeText(this, "할 일을 수정할 수 없습니다. ", Toast.LENGTH_SHORT).show();
                return;
            }

            String content = data.getStringExtra(AddEditActivity.EXTRA_CONTENT);

            MainModel mainModel = new MainModel(content);
            mainModel.setId(id);
            mainViewModel.update(mainModel);

            Toast.makeText(this, "할 일이 수정되었습니다. ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "할 일이 저장되지 않았습니다. ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAll:
                showDeleteAllDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteAllDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("전체 삭제");
        builder.setMessage("정말로 전체 삭제하시겠습니까? ");
        builder.setIcon(R.drawable.delete);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mainViewModel.deleteAll();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}