package com.goodocom.gocsdk.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.goodocom.gocsdk.R;
import com.goodocom.gocsdk.domain.CallLogInfo;
import java.util.List;

public class CalllogAdapter extends BaseAdapter {
    private Context mContext;
    private List<CallLogInfo> mList;

    public CalllogAdapter(List<CallLogInfo> list, Context context) {
        this.mList = list;
        this.mContext = context;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mList.size();
    }

    @Override // android.widget.Adapter
    public CallLogInfo getItem(int i) {
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
            view = LayoutInflater.from(this.mContext).inflate(R.layout.call_log_in_listview_item_view, (ViewGroup) null);
            viewHolder = new ViewHolder();
            viewHolder.mName = (TextView) view.findViewById(R.id.tv_in_name);
            viewHolder.mNumber = (TextView) view.findViewById(R.id.tv_in_number);
            viewHolder.mTime = (TextView) view.findViewById(R.id.tv_in_time);
            viewHolder.mIcon = (ImageView) view.findViewById(R.id.iv_calllog_status);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CallLogInfo info = this.mList.get(i);
        viewHolder.mNumber.setText(info.number);
        viewHolder.mName.setText(info.name);
        viewHolder.mTime.setText(String.valueOf(info.time));
        Log.e("calllog", "info.type: " + info.type);
        if (info.type == 4) {
            viewHolder.mIcon.setImageResource(R.drawable.ico_1138_jilu_laidian);
        } else if (info.type == 5) {
            viewHolder.mIcon.setImageResource(R.drawable.ico_1139_jilu_qudian);
        } else if (info.type == 6) {
            viewHolder.mIcon.setImageResource(R.drawable.ico_1140_jilu_weijie);
        }
        return view;
    }

    static class ViewHolder {
        public ImageView mIcon;
        public TextView mName;
        public TextView mNumber;
        public TextView mTime;

        ViewHolder() {
        }
    }
}
