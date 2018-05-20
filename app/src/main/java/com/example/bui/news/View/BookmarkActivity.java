package com.example.bui.news.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bui.news.Controller.BookmarkListViewsClick;
import com.example.bui.news.Controller.BookmarkLongClick;
import com.example.bui.news.DBConnector.DbConnector;
import com.example.bui.news.Model.Bookmark;
import com.example.bui.news.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    private ListView listViewBookmark;
    private DbConnector dbConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Your Bookmark");

        this.dbConnector = new DbConnector(BookmarkActivity.this);
        ArrayList<Bookmark> bookmarks = (ArrayList<Bookmark>) dbConnector.getBookmark();

        Intent receiveIntent = getIntent();
        Bundle receiveBundle = receiveIntent.getBundleExtra("bookmarkPackage");
        if(receiveBundle != null) {
            ArrayList<Bookmark> receiveBookmarks = (ArrayList<Bookmark>) receiveBundle.getSerializable("bookmarks");
            bookmarks = receiveBookmarks;
        }

        listViewBookmark = (ListView) findViewById(R.id.listViewBookmark);

        ArrayList<String> bookmarkTitles = new ArrayList<>();
        for(Bookmark bookmark : bookmarks){
            bookmarkTitles.add(bookmark.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bookmarkTitles);
        listViewBookmark.setAdapter(adapter);

        listViewBookmark.setOnItemLongClickListener(new BookmarkLongClick(BookmarkActivity.this, bookmarks));
        listViewBookmark.setOnItemClickListener(new BookmarkListViewsClick(bookmarks, BookmarkActivity.this));
    }
}
