package com.example.bui.news.Service;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.example.bui.news.Controller.LoadMoreHandler;
import com.example.bui.news.DBConnector.DbConnector;
import com.example.bui.news.Model.News;
import com.example.bui.news.View.NewsListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class GetMoreService extends AsyncTask<String, JSONObject, Void> {
    private LoadMoreHandler handler;


    public GetMoreService(LoadMoreHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        String url = params[0];
        Log.i("NewsUrl", url);
        JSONObject jsonObject = null;
        try {
            jsonObject = MyJSONReader.readJsonFromUrl(url);
            //Log.i("NewsJSON", jsonObject.getJSONObject("result").getString("content"));
            publishProgress(jsonObject);
        } catch (IOException e) {
            Log.e("getNewsError", e.toString());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e("getNewsError JSON", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(JSONObject... values) {
        Log.i("onProgressUpdate", "processong");
        super.onProgressUpdate(values);
        JSONObject jsonObject = values[0];
        String content = "";
        String title = "";
        String recommend = "";
        News news = null;
        ArrayList<News> listNews = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            Log.i("NewsJSONArr", jsonArray.toString());

            for(int i = 0; i < jsonArray.length(); i++){
                content = jsonArray.getJSONObject(i).optString("content").toString();
                title = jsonArray.getJSONObject(i).optString("title").toString();
                JSONArray recom = jsonArray.getJSONObject(i).optJSONArray("recommend");
                for(int index = 0; index < recom.length(); index++){
                    recommend = recommend + recom.getString(index) + ",";
                }
                news = new News();
                news.setTitle(title);
                news.setContent(content);
                news.setRecommend(recommend.split(","));
                listNews.add(news);
//                long rs = dbConnector.addNews(news);
//                Log.i("GetPageService","addNews " + String.valueOf(rs));
            }
            Message msg = this.handler.obtainMessage(1, listNews);
            this.handler.sendMessage(msg);
            //this.dbConnector.deleteAllNews();
            Log.i("NewsContent",content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
    }
}
