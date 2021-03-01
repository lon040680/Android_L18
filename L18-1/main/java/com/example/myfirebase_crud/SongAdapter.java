package com.example.myfirebase_crud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter {
    Context context;
    ArrayList<Song> list;

    public SongAdapter(@NonNull Context context, ArrayList<Song> list) {
        super(context, R.layout.item, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, null, true);

        TextView name = view.findViewById(R.id.name);
        TextView genre = view.findViewById(R.id.genre);

        Song song = list.get(position);
        name.setText(song.getName());
        genre.setText(song.getGenre());

        return view;
    }
}
