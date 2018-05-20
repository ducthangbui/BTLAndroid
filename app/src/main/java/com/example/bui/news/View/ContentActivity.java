package com.example.bui.news.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bui.news.Controller.ContentController;
import com.example.bui.news.Controller.MainController;
import com.example.bui.news.R;
import com.example.bui.news.Service.GetNewsServiceVolley;

public class ContentActivity extends AppCompatActivity {
    private TextView textViewContent;
    private TextView textViewRecommend;
    private TextView textViewRecommend2;
    private TextView textViewRecommend3;
    private TextView textViewTitleRecommend;
    private GetNewsServiceVolley getNewsServiceVolley;
    private Button btnAddToBookmark;
    private ContentController contentController;
    private String titleGlobal;

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
        btnAddToBookmark = (Button) findViewById(R.id.btnBookmark);
        textViewTitleRecommend.setText("Ralated News");

        Intent receivedIntent = getIntent();
        String receivedTitle = receivedIntent.getStringExtra("title");
        setTitle(receivedTitle);
        titleGlobal = receivedTitle;
        Log.i("ContentActivity","receivedTitle: " + receivedTitle);
        receivedTitle = receivedTitle.replace(" ", "%20");
        String url = "http://203.162.88.120:443/getByTitle/" + receivedTitle;
        Log.i("ContentActivity","url: " + url);
        getNewsServiceVolley = new GetNewsServiceVolley(this, textViewContent, textViewRecommend, textViewRecommend2, textViewRecommend3);
        getNewsServiceVolley.getNewsService(url);

        textViewRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("NewsRecommend","Recommend1 click event");
                String title = textViewRecommend.getText().toString();
                Intent intent = new Intent(ContentActivity.this, ContentActivity.class);
                intent.putExtra("title", title);
                ContentActivity.this.startActivity(intent);
            }
        });

        textViewRecommend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("NewsRecommend","Recommend2 click event");
                String title = textViewRecommend2.getText().toString();
                Intent intent = new Intent(ContentActivity.this, ContentActivity.class);
                intent.putExtra("title", title);
                ContentActivity.this.startActivity(intent);
            }
        });

        textViewRecommend3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("NewsRecommend","Recommend3 click event");
                String title = textViewRecommend3.getText().toString();
                Intent intent = new Intent(ContentActivity.this, ContentActivity.class);
                intent.putExtra("title", title);
                ContentActivity.this.startActivity(intent);
            }
        });

        btnAddToBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentController = new ContentController(ContentActivity.this);

                contentController.clickAddToBookmark(titleGlobal);
                Log.i("ContentActivity","titleGlobal: " + titleGlobal);

            }
        });
    }
}
