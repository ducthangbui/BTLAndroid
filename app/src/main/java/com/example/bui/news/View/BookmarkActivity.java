package com.example.bui.news.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bui.news.Controller.BookmarkListViewsClick;
import com.example.bui.news.Controller.BookmarkLongClick;
import com.example.bui.news.DBConnector.DbConnector;
import com.example.bui.news.Model.Bookmark;
import com.example.bui.news.R;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    private ListView listViewBookmark;
    private DbConnector dbConnector;
    private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Your Bookmark");

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);

        this.dbConnector = new DbConnector(BookmarkActivity.this);
        ArrayList<Bookmark> bookmarks = (ArrayList<Bookmark>) dbConnector.getBookmark();
        Log.i("BookmarkActivity","start&&&&&&&&&&&&");
        Intent receiveIntent = getIntent();
        Bundle receiveBundle = receiveIntent.getBundleExtra("bookmarksPackage");
        if(receiveBundle != null) {
            ArrayList<Bookmark> receiveBookmarks = (ArrayList<Bookmark>) receiveBundle.getSerializable("bookmarks");
            bookmarks = receiveBookmarks;
            Log.i("BookmarkActivity","receiveBundle - bookmarks[0]:" + bookmarks.get(0).getTitle());
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

        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    if(keyCode == KeyEvent.KEYCODE_ENTER){
                        Log.i("BookmarkActivity","searchBookmark " + editTextSearch.getText());
                        ArrayList<Bookmark> bookmarks = (ArrayList<Bookmark>) dbConnector.searchBookmarks(editTextSearch.getText().toString());
                        Log.i("BookmarkActivity","bookmarks: " + bookmarks.size());
                        Intent sendIntent = new Intent(BookmarkActivity.this, BookmarkActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bookmarks", (Serializable) bookmarks);
                        sendIntent.putExtra("bookmarksPackage", bundle);
                        BookmarkActivity.this.startActivity(sendIntent);

                    }
                }
                return false;
            }
        });
    }
}
