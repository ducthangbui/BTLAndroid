package com.example.bui.news.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.bui.news.DBConnector.DbConnector;
import com.example.bui.news.Model.Bookmark;
import com.example.bui.news.Model.News;
import com.example.bui.news.View.BookmarkActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class NewsLongClick implements AdapterView.OnItemLongClickListener {
    private ArrayList<News> listBookmark;
    private Context context;

    public NewsLongClick(Context context, ArrayList<News> listBookmark){
        this.context = context;
        this.listBookmark = listBookmark;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do you want to add this into Bookmark ?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbConnector db = new DbConnector(context);
                News news = listBookmark.get(position);
                Bookmark bookmark = new Bookmark(news.getTitle());

                ArrayList<Bookmark> bookmarks = (ArrayList<Bookmark>) db.getBookmark();
                for(Bookmark bookmark1 : bookmarks){
                    if(bookmark1.getTitle().equals(news.getTitle())){
                        Toast toast = Toast.makeText(context, "Bookmark is exist", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                }

                long rs = db.addBookmark(bookmark);

                if (rs != -1){
                    Toast toast = Toast.makeText(context, "Add Bookmark Sucess", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(context, "Add Bookmark Fail", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.show();
        return false;
    }
}
