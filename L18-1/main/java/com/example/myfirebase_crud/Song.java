package com.example.myfirebase_crud;

public class Song {
    String id, name, genre;

    //無參數的建構方法是為了要能夠有容器放置自動產生的物件
    public Song() {
    }

    public Song(String id, String name, String genre) {
        this.id = id;
        this.name = name;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
