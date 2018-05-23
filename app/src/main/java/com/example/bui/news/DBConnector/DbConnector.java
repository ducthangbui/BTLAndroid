package com.example.bui.news.DBConnector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bui.news.Model.Bookmark;
import com.example.bui.news.Model.News;

import java.util.ArrayList;
import java.util.List;

public class DbConnector extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "News";

    public DbConnector(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String dropNews = "DROP TABLE IF EXISTS News";
        sqLiteDatabase.execSQL(dropNews);
        String dropBookmark = "DROP TABLE IF EXISTS Bookmark";
        sqLiteDatabase.execSQL(dropBookmark);

        String scriptCreateBookmark = "CREATE TABLE IF NOT EXISTS Bookmark " + "(Id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Tittle TEXT)";
        sqLiteDatabase.execSQL(scriptCreateBookmark);

        String scriptCreateNews = "CREATE TABLE IF NOT EXISTS News " + "(Id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Content TEXT, Tittle TEXT, Recommend TEXT)";
        sqLiteDatabase.execSQL(scriptCreateNews);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "DBConnector.onUpgrade ...");

        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Book" );
    }

    public List<Bookmark> getBookmark() {
        Log.i(TAG, "getAllNews ...");
        List<Bookmark> list_news = new ArrayList<Bookmark>();

        String script = "SELECT * FROM Bookmark";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        if(cursor.moveToFirst()){
            do {
                int Id = Integer.parseInt(cursor.getString(0));
                String tittle = cursor.getString(1);
                Bookmark news = new Bookmark(Id, tittle);
                list_news.add(news);
            } while (cursor.moveToNext());
        }

        return list_news;
    }

    public List<News> getNews() {
        Log.i(TAG, "getAllNews ...");
        List<News> list_news = new ArrayList<News>();

        String script = "SELECT * FROM News";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        if(cursor.moveToFirst()){
            do {
                int Id = Integer.parseInt(cursor.getString(0));
                String content = cursor.getString(1);
                String tittle = cursor.getString(2);
                String recommend = cursor.getString(3);

                News news = new News(Id, tittle, content, recommend.split(","));
                list_news.add(news);
            } while (cursor.moveToNext());
        }

        return list_news;
    }

    public List<Bookmark> searchBookmarks(String tittle) {
        Log.i(TAG, "searchBookmarks ..." + tittle);
        List<Bookmark> list_news = new ArrayList<Bookmark>();

        String script = "SELECT * FROM Bookmark WHERE Tittle LIKE '%" + tittle + "%'";
        Log.i(TAG, "script: " + script);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        if(cursor.moveToFirst()){
            do {
                int Id = Integer.parseInt(cursor.getString(0));
                String tittle1 = cursor.getString(1);
                Bookmark news = new Bookmark(Id, tittle1);
                list_news.add(news);
            } while (cursor.moveToNext());
        }

        return list_news;
    }

    public List<News> searchNews(String tittle) {
        Log.i(TAG, "searchNews ...");
        List<News> list_news = new ArrayList<News>();

        String script = "SELECT * FROM News WHERE Tittle = '" + tittle + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        if(cursor.moveToFirst()){
            do {
                int Id = Integer.parseInt(cursor.getString(0));
                String content = cursor.getString(1);
                String tittle1 = cursor.getString(2);
                String recommend = cursor.getString(3);

                News news = new News(Id, content, tittle1, recommend.split(","));
                list_news.add(news);
            } while (cursor.moveToNext());
        }

        return list_news;
    }

    public Bookmark getBookmark(int Id) {
        Log.i(TAG, "getBookmarkById ..." + Id);
        String script = "SELECT * FROM Bookmark WHERE Id = " + Id;
        Bookmark news = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        if(cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String tittle1 = cursor.getString(1);
                news = new Bookmark(id, tittle1);
            }while (cursor.moveToNext());
        }
        return news;
    }

    public News getNews(int Id) {
        Log.i(TAG, "getNewsById ..." + Id);
        String script = "SELECT * FROM News WHERE NewsId = " + Id;
        News news = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        if(cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String content = cursor.getString(1);
                String tittle1 = cursor.getString(2);
                String recommend = cursor.getString(3);

                news = new News(id, content, tittle1, recommend.split(","));
            }while (cursor.moveToNext());
        }
        return news;
    }

    public long addBookmark(Bookmark news) {
        Log.i(TAG, "addBookmark ... " + news.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put("Id", news.getId());
        values.put("Tittle", news.getTitle());

        //If rs = -1 is error else rs = id of inserted
        long rs = db.insert("Bookmark", null, values);
        db.close();

        return rs;
    }

    public long addNews(News news) {
        Log.i(TAG, "addNews ... " + news.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Content", news.getContent());
        values.put("Tittle", news.getTitle());
        String recommend = null;
        for(String str : news.getRecommend()){
            recommend = recommend + str + ",";
        }
        values.put("Recommend",recommend);
        //If rs = -1 is error else rs = id of inserted
        long rs = db.insert("News", null, values);
        db.close();

        return rs;
    }

    public int updateBookmark(Bookmark news) {
        Log.i(TAG, "updateBookmark ... " + news.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("NewsId", news.getId());
        values.put("Tittle", news.getTitle());

        //the number of rows affected
        return db.update("News", values, "Id = ?", new String[]{String.valueOf(news.getId())});
    }

    public int updateNews(News news) {
        Log.i(TAG, "updateNews ... " + news.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Content", news.getContent());
        values.put("Tittle", news.getTitle());
        String recommend = null;
        for(String str : news.getRecommend()){
            recommend = recommend + str + ",";
        }
        values.put("Recommend",recommend);

        //the number of rows affected
        return db.update("News", values, "Id = ?", new String[]{String.valueOf(news.getId())});
    }

    public int deleteBookmark(Bookmark news) {
        Log.i(TAG, "deleteBookmark ... " + news.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        //the number of rows affected
        return db.delete("Bookmark", "Tittle = ?", new String[]{news.getTitle()});
    }

    public int deleteNews(News news) {
        Log.i(TAG, "deleteNews ... " + news.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        //the number of rows affected
        return db.delete("News", "Id = ?", new String[]{String.valueOf(news.getId())});
    }

    public void deleteAllNews(){
        Log.i(TAG, "deleteAllNews ... ");

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from News");
    }
}
