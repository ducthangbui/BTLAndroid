package com.example.bui.news.Model;

public class News {
    private String Title;
    private int Id;
    private String Content;

    public News() {}

    public News(String title, int id, String content) {
        Title = title;
        Id = id;
        Content = content;
    }

    public News(int id, String title) {
        Id = id;
        Title = title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTitle() {

        return Title;
    }

    public int getId() {
        return Id;
    }

    public String getContent() {
        return Content;
    }
}
