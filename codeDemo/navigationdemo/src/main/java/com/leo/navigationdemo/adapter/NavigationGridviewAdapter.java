package com.leo.navigationdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.navigationdemo.AppInfoBean;
import com.leo.navigationdemo.MainActivity;
import com.leo.navigationdemo.R;

import java.util.ArrayList;
import java.util.List;

public class NavigationGridviewAdapter extends BaseAdapter {
    private List<AppInfoBean> dataList;
    private int mPageIndex;//页数下标，表示第几页，从0开始
    private int mPageSize;//每页显示的最大数量

    public NavigationGridviewAdapter(List<AppInfoBean> datas, int PageIndex, int pageSize){
        dataList = datas;
        this.mPageIndex = PageIndex;
        this.mPageSize = pageSize;
    }

    @Override
    public int getCount() {
        //判断当前GridView显示几个
        if(dataList != null){
            return dataList.size() > (mPageIndex + 1)* mPageSize ? mPageSize :
                    (dataList.size() - mPageIndex*mPageSize);
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position + mPageIndex * mPageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + mPageIndex * mPageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_icon, parent, false);
            mHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            mHolder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        final int pos = position + mPageIndex * mPageSize;
        AppInfoBean bean = dataList.get(pos);
        if (bean != null) {
            mHolder.iv_img.setImageDrawable(bean.getIcon());
            mHolder.tv_text.setText(bean.getAppName());
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_img;
        private TextView tv_text;
    }
}
