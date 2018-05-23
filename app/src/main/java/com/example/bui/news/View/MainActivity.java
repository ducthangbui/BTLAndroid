package com.example.bui.news.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bui.news.Controller.MainController;
import com.example.bui.news.Controller.NewsListViewsClick;
import com.example.bui.news.Controller.NewsLongClick;
import com.example.bui.news.Model.News;
import com.example.bui.news.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listViewNews;
    private ArrayList<News> listNewsGlobal;
    private String type;
    private NewsListAdapter adapter;
    private int from;
    private int to;
    private String urlGlobal = "";
    private MainController mainController = new MainController();
    private EditText editTextSearch;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        editTextSearch = (EditText) headerView.findViewById(R.id.editTextSearch);
        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    if(keyCode == KeyEvent.KEYCODE_ENTER){
                        Log.i("MainActivity","searchEditTextEvent: " + editTextSearch.getText());
                        String url = "http://203.162.88.120:443/search/" + editTextSearch.getText();
                        mainController.clickNavButton(url, MainActivity.this);
                    }
                }
                return false;
            }
        });

        listViewNews = (ListView) findViewById(R.id.listViewNews);

        from = 1;
        to = 10;

        Intent receivedIntent = getIntent();
        urlGlobal = receivedIntent.getStringExtra("url");
//        Log.i("MainActivity","urlGlobal: " + urlGlobal.isEmpty());
        if(TextUtils.isEmpty(urlGlobal)){
            String url = "http://203.162.88.120:443/getPage/" + from + "to" + to;
            type = "init";
            getNewsService(url);
        } /*else if(urlGlobal.contains("search")){
            type = "init";
            getNewsService(urlGlobal);
        }*/else{
            type = "init";
            if(urlGlobal.contains("search")) {
                type = "init";
                getNewsService(urlGlobal);
            }else {
                Log.i("MainActivity","urlGlobal: " + urlGlobal);
                String url = urlGlobal + from + "to" + to;
                getNewsService(url);
            }

        }


        listViewNews.setOnScrollListener(new AbsListView.OnScrollListener() {
            int count = 1;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                count = 1;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(view.getLastVisiblePosition() == totalItemCount - 1 && listViewNews.getCount() >= 10 && count++ <= 1){
                    Log.i("MainActivity","scroll event view.getLastVisiblePosition(): " + String.valueOf(view.getLastVisiblePosition()));
                    Log.i("MainActivity","scroll event totalItemCount: " + String.valueOf(totalItemCount));
                    Log.i("MainActivity","scroll event listViewNews.getCount() = " + listViewNews.getCount());
                    Log.i("MainActivity","Onscroll Event");
                    from = to + 1;
                    to = to + 2;
                    if(TextUtils.isEmpty(urlGlobal)){
                        String url = "http://203.162.88.120:443/getPage/" + from + "to" + to;
                        type = "more";
                        getNewsService(url);
                    }
                    else{
                        type = "more";
                        String url = urlGlobal + from + "to" + to;
                        Log.i("MainActivity","urlGlobal loadmore: " + url);
                        getNewsService(url);
                    }

                }
            }
        });

        listNewsGlobal = new ArrayList<>();
        Log.i("MainActivity","listNewsGlobal.size(): " + String.valueOf(listNewsGlobal.size()));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_topics) {
            String url = "http://203.162.88.120:443/getPage/";
            mainController.clickNavButton(url, MainActivity.this);
        } else if (id == R.id.nav_bookmarked) {
            Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
            MainActivity.this.startActivity(intent);

        } else if (id == R.id.nav_business) {

            String url = "http://203.162.88.120:443/getByTopic/business&";
            mainController.clickNavButton(url, MainActivity.this);

        } else if (id == R.id.nav_entertainment) {

            String url = "http://203.162.88.120:443/getByTopic/entertainment&";
            mainController.clickNavButton(url, MainActivity.this);
        } else if (id == R.id.nav_politics) {
            String url = "http://203.162.88.120:443/getByTopic/politics&";
            mainController.clickNavButton(url, MainActivity.this);
        } else if (id == R.id.nav_sport) {
            String url = "http://203.162.88.120:443/getByTopic/sport&";
            mainController.clickNavButton(url, MainActivity.this);
        } else if (id == R.id.nav_tech) {
            String url = "http://203.162.88.120:443/getByTopic/tech&";
            mainController.clickNavButton(url, MainActivity.this);
        } else if (id == R.id.nav_view){
            Log.i("MainActivity","nav_view");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getNewsService(String url) {
        Log.i("GetMoreServiceVolley","start " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("GetMoreServiceVolley", "response: " + response);
                    JSONObject res = new JSONObject(response);
                    JSONArray jsonArray = res.optJSONArray("result");

                    String content = "";
                    String title = "";

                    ArrayList<News> listNews = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        content = jsonArray.getJSONObject(i).optString("content").toString();
                        title = jsonArray.getJSONObject(i).optString("title").toString();
                        News news = new News();
                        news.setTitle(title);
                        news.setContent(content);
                        listNews.add(news);

                    }
                    listNewsGlobal.addAll(listNews);
                    if(type.equals("init")){
                        Log.i("GetMoreServiceVolley","listNews[0].getTitle: " + listNews.get(0).getTitle());
                        adapter = new NewsListAdapter(getApplicationContext(), listNews);
                        listViewNews.setAdapter(adapter);
                    } else {
                        Log.i("GetMoreServiceVolley","not init listNews[0].getTitle: " + listNews.get(0).getTitle());
                        adapter.addListNewsAdapteṛ̣̣̣̣̣̣(listNews);
                        listViewNews.setAdapter(adapter);
                        Toast toast = Toast.makeText(MainActivity.this, "Update Sucess", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    listViewNews.setOnItemClickListener(new NewsListViewsClick(listNewsGlobal, MainActivity.this));
                    listViewNews.setOnItemLongClickListener(new NewsLongClick(MainActivity.this, listNewsGlobal));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("GetMoreServiceVolley", "VolleyError " + error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
