package com.example.myfirebase_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    //1. 宣告變數
    DatabaseReference myRef;
    EditText input;
    Spinner genres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.inputName);
        genres = findViewById(R.id.genres);
        myRef = FirebaseDatabase.getInstance().getReference("songs");
    }
    //新增紀錄
    public void add(View view) {
        String name = input.getText().toString().trim();
        String genre = genres.getSelectedItem().toString();
        String id = myRef.push().getKey(); //取得自動新增出來的索引編號

        if(!TextUtils.isEmpty(name)){
            Song s1 = new Song(id, name, genre);
            myRef.child(id).setValue(s1);
            Snackbar.make(view, "新增成功功", Snackbar.LENGTH_SHORT).show();

            input.setText("");
        } else {
            Toast.makeText(MainActivity.this, "不可空白白", Toast.LENGTH_LONG).show();
                }
    }

    //轉跳查詢
    public void onClick(View view) {
        //跳頁
        startActivity(new Intent(MainActivity.this, Query.class));
    }

}