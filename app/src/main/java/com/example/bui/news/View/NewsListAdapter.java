package com.example.bui.news.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bui.news.Model.News;
import com.example.bui.news.R;

import org.w3c.dom.Text;

import java.util.List;

public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private List<News> listNews;

    public NewsListAdapter(){

    }

    public NewsListAdapter(Context context, List<News> listNews){
        this.context = context;
        this.listNews = listNews;
    }
    @Override
    public int getCount() {
        return listNews.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(this.context, R.layout.list_news, null);
        TextView textViewTittle = (TextView) view.findViewById(R.id.textViewTitleList);
        TextView textViewDes = (TextView) view.findViewById(R.id.textViewDes);

        textViewTittle.setText(this.listNews.get(position).getTitle());
        String content = this.listNews.get(position).getContent();
        String des = content.substring(0, 100);
        textViewDes.setText(des + "...");
        return view;
    }

    public void addListNewsAdapteṛ̣̣̣̣̣̣(List<News> list){
        //Add list to current array list of data
        this.listNews.addAll(0, list);
        //Notify UI
        this.notifyDataSetChanged();
    }
}
