package com.example.myfirebase_crud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Detail extends AppCompatActivity {
    //宣告變數
    TextView s_id, s_name, s_genre;
    String str_id, str_name, str_genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        s_id = findViewById(R.id.show_id);
        s_name = findViewById(R.id.show_name);
        s_genre = findViewById(R.id.show_genre);

        Intent it2 = getIntent();
        str_id = it2.getStringExtra("id");
        str_name = it2.getStringExtra("name");
        str_genre = it2.getStringExtra("genre");

        s_id.setText("id: "+str_id);
        s_name.setText("name: "+str_name);
        s_genre.setText("genre: "+str_genre);
    }

    public void update(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("修改內容：");
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_update, null);
        dialog.setView(dialogView);

        final EditText inputName = dialogView.findViewById(R.id.show_name);
        final Spinner inputGenre = dialogView.findViewById(R.id.show_genre);
        inputName.setText(str_name);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ss_id = str_id;
                String ss_name = inputName.getText().toString().trim();
                String ss_genre = inputGenre.getSelectedItem().toString();

                DatabaseReference ref = FirebaseDatabase.getInstance()
                        .getReference("songs")
                        .child(ss_id);

                Song s = new Song(ss_id, ss_name, ss_genre);
                ref.setValue(s);

                dialog.dismiss();
                finish();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.create().show();
    }

    public void delete(View view) {
        DatabaseReference myRef = FirebaseDatabase.getInstance()
                .getReference("songs")
                .child(str_id);
        myRef.removeValue();
        finish();
    }
}