package com.example.bui.news.Controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import com.example.bui.news.DBConnector.DbConnector;
import com.example.bui.news.Model.Bookmark;
import com.example.bui.news.Model.News;
import com.example.bui.news.View.MainActivity;
import com.example.bui.news.View.NewsListAdapter;

import java.util.List;

public class MainController {
    private DbConnector dbConnector;

    public MainController(){

    }

    public MainController(Context context){
        this.dbConnector = new DbConnector(context);
    }
    public void clickNavButton(String url, Context context){
        Log.i("MainController","url: " + url);
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }


}
