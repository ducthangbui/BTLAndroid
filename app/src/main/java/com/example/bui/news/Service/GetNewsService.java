package com.example.bui.news.Service;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetNewsService extends AsyncTask<Void, Void, String> {
    private TextView txtTittle;
    private TextView txtContent;
    private String url;
    private String response;

    public GetNewsService(String url, TextView txtTittle, TextView txtContent){
        this.url = url;
        this.txtTittle = txtTittle;
        this.txtContent = txtContent;
    }

    @Override
    protected String doInBackground(Void... urls) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ( (line = bufferedReader.readLine()) != null ){
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            this.response = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.optJSONArray("News");

            int lengthJsonArr = jsonArray.length();
            for(int i=0; i < lengthJsonArr; i++) {
                JSONObject jsonChildNode = jsonArray.getJSONObject(i);

                String tittle = jsonChildNode.optString("Title").toString();
                String content = jsonChildNode.optString("Content").toString();

                this.txtTittle.setText(tittle);
                this.txtContent.setText(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
