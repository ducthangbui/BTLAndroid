package com.example.bui.news.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;

import com.example.bui.news.DBConnector.DbConnector;
import com.example.bui.news.Model.Bookmark;
import com.example.bui.news.View.BookmarkActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class BookmarkLongClick implements AdapterView.OnItemLongClickListener {
    private ArrayList<Bookmark> listBookmark;
    private Context context;

    public BookmarkLongClick(Context context, ArrayList<Bookmark> listBookmark){
        this.context = context;
        this.listBookmark = listBookmark;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do you want to delete this Bookmark ?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbConnector db = new DbConnector(context);
                Bookmark bookmark = listBookmark.get(position);
                db.deleteBookmark(bookmark);

                ArrayList<Bookmark> bookmarks = (ArrayList<Bookmark>) db.getBookmark();

                Intent sendIntent = new Intent(context, BookmarkActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bookmarks", (Serializable) bookmarks);
                sendIntent.putExtra("bookmarksPackage", bundle);
                context.startActivity(sendIntent);

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
