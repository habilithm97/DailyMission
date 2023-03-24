package com.example.simpletodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    public static final String EXTRA_CONTENT = "com.example.simpletodolist.EXTRA_CONTENT";

    private EditText contentEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initView();
    }

    private void initView() {
        contentEdt = (EditText) findViewById(R.id.contentEdt);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        setTitle("할 일 추가하기");
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
        String content = contentEdt.getText().toString();

        if(content.trim().isEmpty()) {
            Toast.makeText(this, "내용을 입력해주세요. ", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_CONTENT, content);
        setResult(RESULT_OK, intent);
        finish();
    }
}