package com.example.bui.news.Controller;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.bui.news.Model.News;
import com.example.bui.news.View.NewsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class LoadMoreHandler extends Handler {
    private View view;
    private ListView listView;
    private NewsListAdapter adapter;

    public LoadMoreHandler(View view, ListView listView, NewsListAdapter adapter){
        this.view = view;
        this.listView = listView;
        this.adapter = adapter;
    }

    @Override
    public void handleMessage(Message msg) {
        int count = 0;
        switch (msg.what) {
            case 0:
                Log.i("LoadMoreHandler","aaaaaaaaa");
                //listView.addFooterView(view);
                Log.i("LoadMoreHandler","bbbbbbbbb");
                break;
            case 1:
                Log.i("LoadMoreHandler","Count:" + String.valueOf(count++));
                //Update adapter and UI
                adapter.addListNewsAdapteṛ̣̣̣̣̣̣((ArrayList<News>)msg.obj);
                Log.i("LoadMoreHandler","Ttitle More: " + ((ArrayList<News>)msg.obj).get(0).getTitle());
//                Log.i("LoadMoreHandler", view.getContext().toString());
                //listView.removeFooterView(view);
                break;
             default:
                 break;
        }
    }
}
