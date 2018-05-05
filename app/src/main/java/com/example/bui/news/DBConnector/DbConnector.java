package com.example.bui.news.DBConnector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bui.news.Model.News;

import java.util.ArrayList;
import java.util.List;

public class DbConnector extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Book";

    public DbConnector(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String scriptCreateBookmark = "CREATE TABLE News " + "(Id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NewsId INTEGER, Tittle TEXT)";
        sqLiteDatabase.execSQL(scriptCreateBookmark);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "DBConnector.onUpgrade ...");

        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Book" );
    }

    public List<News> getNews() {
        Log.i(TAG, "getAllUsers ...");
        List<News> list_news = new ArrayList<News>();

        String script = "SELECT * FROM News";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        if(cursor.moveToFirst()){
            do {
                int Id = Integer.parseInt(cursor.getString(0));
                int newsId = Integer.parseInt(cursor.getString(1));
                String tittle = cursor.getString(2);
                News news = new News(newsId, tittle);
                list_news.add(news);
            } while (cursor.moveToNext());
        }

        return list_news;
    }

    public List<News> searchNews(String tittle) {
        Log.i(TAG, "searchBooks ...");
        List<News> list_news = new ArrayList<News>();

        String script = "SELECT * FROM News WHERE Tittle = '" + tittle + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        if(cursor.moveToFirst()){
            do {
                int Id = Integer.parseInt(cursor.getString(0));
                int newsId = Integer.parseInt(cursor.getString(1));
                String tittle1 = cursor.getString(2);
                News news = new News(newsId, tittle1);
                list_news.add(news);
            } while (cursor.moveToNext());
        }

        return list_news;
    }

    public News getNews(int Id) {
        Log.i(TAG, "getBookById ..." + Id);
        String script = "SELECT * FROM Book WHERE NewsId = " + Id;
        News news = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(script, null);
        if(cursor.moveToFirst()) {
            do {
                int newsId = Integer.parseInt(cursor.getString(1));
                String tittle1 = cursor.getString(2);
                news = new News(newsId, tittle1);
            }while (cursor.moveToNext());
        }
        return news;
    }

    public long addNews(News news) {
        Log.i(TAG, "addNews ... " + news.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NewsId", news.getId());
        values.put("Tittle", news.getTitle());

        //If rs = -1 is error else rs = id of inserted
        long rs = db.insert("News", null, values);
        db.close();

        return rs;
    }

    public int updateNews(News news) {
        Log.i(TAG, "updateNews ... " + news.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NewsId", news.getId());
        values.put("Tittle", news.getTitle());

        //the number of rows affected
        return db.update("News", values, "NewsId = ?", new String[]{String.valueOf(news.getId())});
    }

    public int deleteNews(News news) {
        Log.i(TAG, "deleteNews ... " + news.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        //the number of rows affected
        return db.delete("News", "NewsId = ?", new String[]{String.valueOf(news.getId())});
    }
}
