package com.example.bui.news.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.bui.news.R;
import com.example.bui.news.Service.GetNewsServiceVolley;

public class ContentActivity extends AppCompatActivity {
    private TextView textViewContent;
    private TextView textViewRecommend;
    private TextView textViewRecommend2;
    private TextView textViewRecommend3;
    private TextView textViewTitleRecommend;
    private GetNewsServiceVolley getNewsServiceVolley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewContent = (TextView) findViewById(R.id.textViewContent);
        textViewRecommend = (TextView) findViewById(R.id.textViewRecommend);
        textViewRecommend2 = (TextView) findViewById(R.id.textViewRecommend1);
        textViewRecommend3 = (TextView) findViewById(R.id.textViewRecommend2);
        textViewTitleRecommend = (TextView) findViewById(R.id.textViewTitleRecommend);

        textViewTitleRecommend.setText("Ralated News");

        String url = "http://203.162.88.120:443/getByTitle/Israel%20looks%20to%20US%20for%20bank%20chief";
        getNewsServiceVolley = new GetNewsServiceVolley(this, textViewContent, textViewRecommend, textViewRecommend2, textViewRecommend3);
        getNewsServiceVolley.getNewsService(url);

        textViewRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("NewsRecommend","Recommend1 click event");
            }
        });

        textViewRecommend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("NewsRecommend","Recommend2 click event");
            }
        });

        textViewRecommend3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("NewsRecommend","Recommend3 click event");
            }
        });
    }
}
