package com.example.simpletodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.simpletodolist.databinding.ActivityAddEditBinding;

public class AddEditActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.simpletodolist.EXTRA_ID";
    public static final String EXTRA_CONTENT = "com.example.simpletodolist.EXTRA_CONTENT";

    private ActivityAddEditBinding addEditBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit);

        initView();
    }

    private void initView() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        setTitle("할 일 추가하기");

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)) { // EXTRA_ID로 값이 들어오면 수정모드임
            setTitle("할 일 수정하기");
            addEditBinding.contentEdt.setText(intent.getStringExtra(EXTRA_CONTENT));
        } else {
            setTitle("할 일 추가하기");
            addEditBinding.contentEdt.requestFocus(); // EditText에 포커스 주기
        }

        addEditBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                save();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        String content = addEditBinding.contentEdt.getText().toString();

        if(content.trim().isEmpty()) {
            Toast.makeText(this, "내용을 입력해주세요. ", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_CONTENT, content);

        // 사용한 intent에서 EXTRA_ID가 없는 경우(새 아이템 추가인 경우), -1을 id에 저장함
        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if(id != -1) { // 새 아이템 추가가 아닌 경우 == 수정인 경우
            intent.putExtra(EXTRA_ID, id); // 기존 id put
        }
        
        setResult(RESULT_OK, intent);
        finish();
    }
}