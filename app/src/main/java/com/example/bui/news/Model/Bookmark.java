package com.example.bui.news.Model;

import java.io.Serializable;

public class Bookmark implements Serializable {
    private int id;
    private String title;

    public Bookmark(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Bookmark(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
