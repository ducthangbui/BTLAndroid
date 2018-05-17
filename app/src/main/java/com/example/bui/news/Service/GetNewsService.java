package com.example.bui.news.Service;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetNewsService extends AsyncTask<String, JSONObject, Void> {
    private TextView textViewContent;
    private TextView textViewRecommend;
    private TextView textViewRecommend2;
    private TextView textViewRecommend3;

    public GetNewsService(TextView textView, TextView textViewRecommend, TextView textViewRecommend2, TextView textViewRecommend3){
        this.textViewContent = textView;
        this.textViewRecommend = textViewRecommend;
        this.textViewRecommend2 = textViewRecommend2;
        this.textViewRecommend3 = textViewRecommend3;
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
        try {
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            Log.i("NewsJSONArr", jsonArray.toString());

            for(int i = 0; i < jsonArray.length(); i++){
                content = jsonArray.getJSONObject(i).optString("content").toString();
                this.textViewContent.setText(content);
                JSONArray listRecommend = jsonArray.getJSONObject(i).optJSONArray("recommend");
                Log.i("NewsRecommend", listRecommend.getString(0));
                this.textViewRecommend.setText(listRecommend.getString(0));
                this.textViewRecommend2.setText(listRecommend.getString(1));
                this.textViewRecommend3.setText(listRecommend.getString(2));
            }

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
