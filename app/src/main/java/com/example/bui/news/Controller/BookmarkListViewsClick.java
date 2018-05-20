package com.example.bui.news.Controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.bui.news.Model.Bookmark;
import com.example.bui.news.Model.News;
import com.example.bui.news.View.ContentActivity;

import java.util.ArrayList;

public class BookmarkListViewsClick implements AdapterView.OnItemClickListener {

    private ArrayList<Bookmark> listNews;
    private Context contextFrom;

    public BookmarkListViewsClick(ArrayList<Bookmark> listNews, Context contextFrom){
        Log.i("NewsListViewsClick","listNews.size(): " + String.valueOf(listNews.size()));
        this.listNews = listNews;
        this.contextFrom = contextFrom;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(contextFrom, ContentActivity.class);
        String title = this.listNews.get(position).getTitle();
        intent.putExtra("title", title);
        contextFrom.startActivity(intent);
    }
}
