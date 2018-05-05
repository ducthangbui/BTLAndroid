package com.example.bui.news.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bui.news.R;

public class CustomListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(){

    }

    public CustomListAdapter(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        convertView = layoutInflater.inflate(R.layout.topics_layout, null);
        holder = new ViewHolder();
        holder.textViewBusiness = (TextView) convertView.findViewById(R.id.textViewBusiness);
        holder.textViewEntertainment = (TextView) convertView.findViewById(R.id.textViewEntertainment);
        holder.textViewPolitics = (TextView) convertView.findViewById(R.id.textViewPolitics);
        holder.textViewSport = (TextView) convertView.findViewById(R.id.textViewSport);
        holder.textViewTech = (TextView) convertView.findViewById(R.id.textViewTech);

        return convertView;
    }

    static class ViewHolder {
        TextView textViewPolitics;
        TextView textViewEntertainment;
        TextView textViewTech;
        TextView textViewSport;
        TextView textViewBusiness;
    }
}
