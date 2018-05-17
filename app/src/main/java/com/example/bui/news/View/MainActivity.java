package com.example.bui.news.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.bui.news.Controller.LoadMoreHandler;
import com.example.bui.news.Controller.MainController;
import com.example.bui.news.Controller.ThreadGetMoreData;
import com.example.bui.news.Model.News;
import com.example.bui.news.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listViewNews;
    private List<News> listNews;
    private NewsListAdapter adapter;
    public View ftView;
    public LoadMoreHandler loadMoreHandler;
//    private boolean isLoading = false;

    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        listViewNews = (ListView) findViewById(R.id.listViewNews);

        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView = li.inflate(R.layout.footer_view, null, false);

        mainController = new MainController(MainActivity.this, getApplicationContext(), listViewNews, adapter, loadMoreHandler);
        mainController.dropNewsTable();
        //init News
        mainController.getPages(1,9);
        listNews = mainController.getAllNews();
//        Log.i("MainActivity","listNews[1] - Title: " + listNews.get(1).getTitle());


//        adapter = new NewsListAdapter(getApplicationContext(), listNews);
//        listViewNews.setAdapter(adapter);
        loadMoreHandler = new LoadMoreHandler(ftView, listViewNews, adapter);

//        Intent intent = new Intent(this, ContentActivity.class);
//        this.startActivity(intent);


        listViewNews.setOnScrollListener(new AbsListView.OnScrollListener() {
            int count = 0;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                count = 0;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("MainController,onScroll","scroll event");
                Log.i("MainController,onScroll","view.getLastVisiblePosition() = " + view.getLastVisiblePosition());
                Log.i("MainController,onScroll","visibleItemCount = " + visibleItemCount);

                //Check when scroll to last item in ListView, in this tut, init data in listview = 2
                if(view.getLastVisiblePosition() ==  totalItemCount - 1 && listViewNews.getCount() >= 9 && ++count <= 1){
                    Log.i("MainController,onScroll","scroll event thread");
                    Log.i("MainController,onScroll","listViewNews.getCount() = " + listViewNews.getCount());
                    Thread thread = new ThreadGetMoreData((LoadMoreHandler) loadMoreHandler, MainActivity.this, getApplicationContext(), listViewNews, adapter);
                    thread.start();
                }
            }
        });
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
            // Handle the camera action
        } else if (id == R.id.nav_bookmarked) {

        } else if (id == R.id.nav_business) {

        } else if (id == R.id.nav_entertainment) {

        } else if (id == R.id.nav_politics) {

        } else if (id == R.id.nav_sport) {

        } else if (id == R.id.nav_tech) {

        } else if (id == R.id.nav_view){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
