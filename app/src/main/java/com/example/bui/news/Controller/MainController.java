package com.example.bui.news.Controller;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.example.bui.news.DBConnector.DbConnector;
import com.example.bui.news.Model.News;
import com.example.bui.news.Service.GetMoreService;
import com.example.bui.news.Service.GetPagesService;
import com.example.bui.news.View.NewsListAdapter;

import java.util.List;

public class MainController {
    private Context context;
    private DbConnector dbConnector;
    private ListView listView;
    private NewsListAdapter newsListAdapter;
    private Context appContext;
    private LoadMoreHandler handler;

    public MainController(Context context, Context appContext, ListView listView, NewsListAdapter newsListAdapter, LoadMoreHandler handler){
        this.context = context;
        this.dbConnector = new DbConnector(context);
        this.appContext = appContext;
        this.listView = listView;
        this.newsListAdapter = newsListAdapter;
        this.handler = handler;
    }

    public void getPages(int from, int to){
        String url = "http://203.162.88.120:443/getPage/" + String.valueOf(from) + "to" + String.valueOf(to);
        new GetPagesService(this.context, this.appContext, this.listView, this.newsListAdapter).execute(url);
    }

    public List<News> getAllNews(){
//        Log.i("MainController","getAllNews - Title[0]:" + dbConnector.getNews().get(0).getTitle());
        return dbConnector.getNews();
    }

    public void dropNewsTable(){
        this.dbConnector.deleteAllNews();
    }

    public void getmore(int from, int to){
        String url = "http://203.162.88.120:443/getPage/" + String.valueOf(from) + "to" + String.valueOf(to);
        new GetMoreService(this.handler).execute(url);
    }
}
