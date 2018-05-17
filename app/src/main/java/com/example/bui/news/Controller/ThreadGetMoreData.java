package com.example.bui.news.Controller;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.example.bui.news.DBConnector.DbConnector;
import com.example.bui.news.Model.News;
import com.example.bui.news.View.NewsListAdapter;

import java.util.ArrayList;
import java.util.logging.Handler;

public class ThreadGetMoreData extends Thread {
    private LoadMoreHandler handler;
    private int count = 0;
    private DbConnector dbConnector;
    private MainController mainController;

    public ThreadGetMoreData(LoadMoreHandler handler, Context context, Context appContext, ListView listView, NewsListAdapter newsListAdapter){
        this.handler = handler;
        this.dbConnector = new DbConnector(context);
        this.mainController = new MainController(context, appContext, listView, newsListAdapter, handler);
    }
//    private ArrayList<News> getMoreNews(){
//        ArrayList<News> listNews = new ArrayList<>();
//        listNews.add(new News("Title 3", 3, "content 3"));
//        listNews.add(new News("Title 4", 4, "content 4"));
//        return listNews;
//    }

//    public ThreadGetMoreData(LoadMoreHandler handler){
//        this.handler = handler;
//    }
    @Override
    public void run() {
        Log.i("ThreadGetMoreData","Count: " + String.valueOf(count++));
        this.handler.sendEmptyMessage(0);
        ArrayList<News> listNews = (ArrayList<News>) this.mainController.getAllNews();
        this.mainController.getPages(listNews.size(), listNews.size() + 2);
//        listNews = (ArrayList<News>) this.mainController.getAllNews();
//        Log.i("ThreadGetMoreData","listNews.size() " + String.valueOf(listNews.size()));
//        ArrayList<News> listNewsMore = new ArrayList<>();
//
//        String title = "";
//        String content = "";
//        String[] recommend = null;
//        for(int index = listNews.size() - 1; index < listNews.size() + 2; index++){
//            title = listNews.get(index).getTitle();
//            content = listNews.get(index).getContent();
//            recommend = listNews.get(index).getRecommend();
//            listNewsMore.add(new News(0, title, content, recommend));
//        }
//        Log.i("ThreadGetMoreData","Title More: " +listNewsMore.get(0).getTitle());
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        //Send the result to handler
//        Message msg = this.handler.obtainMessage(1, listNewsMore);
//        this.handler.sendMessage(msg);
//        this.dbConnector.deleteAllNews();
    }
}
