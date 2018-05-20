package com.example.bui.news.Controller;

import android.content.Context;
import android.widget.Toast;

import com.example.bui.news.DBConnector.DbConnector;
import com.example.bui.news.Model.Bookmark;
import com.example.bui.news.View.ContentActivity;

import java.util.ArrayList;

public class ContentController {
    private DbConnector dbConnector;
    private Context context;

    public ContentController(Context context){
        this.dbConnector = new DbConnector(context);
        this.context = context;
    }

    public void clickAddToBookmark(String title){
        ArrayList<Bookmark> bookmarks = (ArrayList<Bookmark>) this.dbConnector.getBookmark();
        for(Bookmark bookmark : bookmarks){
            if(bookmark.getTitle().equals(title)){
                Toast toast = Toast.makeText(context, "Bookmark is exist", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
        }
        Bookmark bookmark = new Bookmark(title);
        long rs = this.dbConnector.addBookmark(bookmark);

        if (rs != -1){
            Toast toast = Toast.makeText(context, "Add Bookmark Sucess", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(context, "Add Bookmark Fail", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
