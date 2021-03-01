package com.example.myfirebase_crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Query extends AppCompatActivity {
    ListView lv;
    EditText input;
    Spinner genres;
    ArrayList<Song> list;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        lv = findViewById(R.id.lv);
        input = findViewById(R.id.inputName);
        genres = findViewById(R.id.genres);
        list = new ArrayList<>();
        //自訂方法：顯示全部資料
        getAllData();
    }

    //自訂方法
    private void getAllData() {
        myRef = FirebaseDatabase.getInstance().getReference("songs");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //清空之前查到的資料 不然會一直加入新來的資料
                list.clear();
                //利用迴圈取得 firebase資料 並加入 list
                for(DataSnapshot d:snapshot.getChildren()){
                    Song song = d.getValue(Song.class);
                    list.add(song);
                }
                //建立Adapter
                SongAdapter adapter = new SongAdapter(Query.this, list);
                //傳到ListView
                lv.setAdapter(adapter);
                //ListView增加監聽器
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Song ss = (Song) parent.getItemAtPosition(position); //拿到的東西必須要傳到Song.java 才能知道有什麼東西可以用
                        String ss_id = ss.getId();
                        String ss_name = ss.getName();
                        String ss_genre = ss.getGenre();

                        Intent it = new Intent(Query.this, Detail.class);
                        it.putExtra("id", ss_id);
                        it.putExtra("name", ss_name);
                        it.putExtra("genre", ss_genre);

                        startActivity(it);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Query.this, "錯誤", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //條件查詢
    public void query(View view) {
        final String name = input.getText().toString().trim();
        final String genre = genres.getSelectedItem().toString().trim();
        myRef = FirebaseDatabase.getInstance().getReference("songs");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot d:snapshot.getChildren()){
                    Song song = d.getValue(Song.class);
                    //依 name 查詢 第一個 if 先確定時是否為空白
                    // 第二個if 才是去比對輸入的資料(contains(name))跟搜尋到的資料(song.getName())是否吻合
                    if(!TextUtils.isEmpty(name)){
                        if(song.getName().contains(name)){
                            list.add(song);
                        }
                    }
                    // 依 genre 查詢 跟上面同時存在就會重複顯示在 view list
                    if(song.getGenre().equals(genre)){
                        list.add(song);
                    }
                }
                //建立Adapter
                SongAdapter adapter = new SongAdapter(Query.this, list);
                //傳到ListView
                lv.setAdapter(adapter);
                //ListView增加監聽器
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Song song = (Song) parent.getItemAtPosition(position); //拿到的東西必須要傳到Song.java 才能知道有什麼東西可以用
                        String aa_id = song.getId();
                        String aa_name = song.getName();
                        String aa_genre = song.getGenre();

                        Intent it = new Intent(Query.this, Detail.class);
                        it.putExtra("id", aa_id);
                        it.putExtra("name", aa_name);
                        it.putExtra("genre", aa_genre);

                        startActivity(it);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Query.this, "錯誤", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //顯示全部
    public void queryAll(View view) {
        //自訂方法:顯示全部資料
        getAllData();
    }
}