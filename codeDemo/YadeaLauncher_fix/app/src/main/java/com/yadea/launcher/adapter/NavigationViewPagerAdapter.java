package com.yadea.launcher.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NavigationViewPagerAdapter extends PagerAdapter {
    private List<View> gridList;
    public NavigationViewPagerAdapter(){
        gridList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void add(List<View> datas){
        if (gridList.size() > 0) {
            gridList.clear();
        }
        gridList.addAll(datas);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if(gridList != null){
            return gridList.size();
        }
        return 0;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if(gridList.contains((View) object)){
            return gridList.indexOf((View) object);
        }
        return POSITION_NONE;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(gridList.get(position));
        return gridList.get(position);
    }
}
