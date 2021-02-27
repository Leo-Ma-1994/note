package com.yadea.launcher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yadea.launcher.R;

import java.util.List;

public class FaultGridAdapter extends BaseAdapter {
    private List<String> titles;
    private Context context;
    private LayoutInflater inflater;
    public FaultGridAdapter(List<String> titles, Context context) {
        super();
        this.titles = titles;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflater.inflate(R.layout.item_fault_info, null);
        TextView tv=  (TextView) v.findViewById(R.id.fault_tv);
        tv.setText(titles.get(position));
        return v;
    }

}
