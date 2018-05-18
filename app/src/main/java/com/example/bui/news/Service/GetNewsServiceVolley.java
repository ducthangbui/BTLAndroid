package com.example.bui.news.Service;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetNewsServiceVolley {
    private TextView textViewContent;
    private TextView textViewRecommend;
    private TextView textViewRecommend2;
    private TextView textViewRecommend3;
    private Context context;

    public GetNewsServiceVolley(Context context, TextView textView, TextView textViewRecommend, TextView textViewRecommend2, TextView textViewRecommend3){
        this.context = context;
        this.textViewContent = textView;
        this.textViewRecommend = textViewRecommend;
        this.textViewRecommend2 = textViewRecommend2;
        this.textViewRecommend3 = textViewRecommend3;
    }

    public void getNewsService(String url) {
        Log.i("GetNewsServiceVolley","start");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("GetNewsServiceVolley", "response: " + response);
                    JSONObject res = new JSONObject(response);
                    JSONArray jsonArray = res.optJSONArray("result");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String content = "";
                        String title = "";
                        content = jsonArray.getJSONObject(i).optString("content").toString();
                        textViewContent.setText(content);
                        JSONArray listRecommend = jsonArray.getJSONObject(i).optJSONArray("recommend");
                        Log.i("GetNewsServiceVolley", "Content[0]:" + listRecommend.getString(0));
                        textViewRecommend.setText(listRecommend.getString(0));
                        textViewRecommend2.setText(listRecommend.getString(1));
                        textViewRecommend3.setText(listRecommend.getString(2));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("GetNewsServiceVolley", "VolleyError " + error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
