package com.goodocom.gocsdk.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.domain.PhoneBookInfo;
import java.util.List;

public class PhoneBookAdapter extends BaseAdapter {
    private Context mContext;
    private List<PhoneBookInfo> mList;

    public PhoneBookAdapter(List<PhoneBookInfo> list, Context context) {
        this.mList = list;
        this.mContext = context;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mList.size();
    }

    @Override // android.widget.Adapter
    public PhoneBookInfo getItem(int i) {
        return this.mList.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return (long) i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = View.inflate(this.mContext, R.layout.contacts_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mName = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.mNumber = (TextView) view.findViewById(R.id.tv_number);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        PhoneBookInfo info = this.mList.get(i);
        viewHolder.mNumber.setText(info.num);
        viewHolder.mName.setText(info.name);
        return view;
    }

    static class ViewHolder {
        public TextView mName;
        public TextView mNumber;

        ViewHolder() {
        }
    }
}
