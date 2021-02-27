package com.yadea.launcher.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.yadea.launcher.R;
import com.yadea.launcher.bean.Status;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StatusBarAdapter extends BaseAdapter {

    private List<Status> mData;

    private List<Status> mDataBackup;

    private Context mContext;

    private GridView mView;

    public StatusBarAdapter(Context context, List<Status> mData, GridView view) {
        this.mData = mData;
        this.mContext = context;
        mView = view;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View container = LayoutInflater.from(mContext).inflate(R.layout.layout_statusbar_item, parent, false);
        ImageView imageView = container.findViewById(R.id.status_icon);
        imageView.setImageResource(getDrawable(mData.get(position)));
        return container;
    }

    private int getDrawable(Status status) {
        int result = R.drawable.ic_phone;
        switch (status) {
            case HEAD_LAMP:
                result = R.drawable.ic_status_head_lamp;
                break;
            case SIDE_BRACE:
                result = R.drawable.ic_status_sidebrace;
                break;
            case PHONE:
                result = R.drawable.ic_phone;
                break;
            case CRUISE:
                result = R.drawable.ic_status_cruise;
                break;
            case HOT:
                result = R.drawable.ic_status_hot;
                break;
            case TIRE:
                result = R.drawable.ic_status_tire_pressure;
                break;
            case BREAKDOWN:
                result = R.drawable.ic_status_fault;
                break;
            case FIX:
                result = R.drawable.ic_status_fix;
                break;
            case GRIP:
                result = R.drawable.ic_status_grip;
                break;
            case MOTOR_FAULT:
                result = R.drawable.ic_status_motor_fault;
                break;
            case CHARGING:
                result = R.drawable.ic_status_battery_state;
                break;
            case CHARGED:
                result = R.drawable.ic_status_battery_state_charged;
                break;
            case BATTERY_LOW:
                result = R.drawable.ic_status_battery_low;
                break;
            default: break;
        }

        return result;
    }

    public void addStatus(Status status) {
        doAddStatus(status);
    }

    public void removeStatus(Status status) {
        doRemoveStatus(status);
    }

    public void removeAll() {
        doRemoveAll();
    }

    public void restoreAll() {
        if (mDataBackup != null) {
            for (int i = 0; i < mDataBackup.size(); i++) {
                doAddStatus(mDataBackup.get(i));
            }
            mDataBackup.clear();
        }
    }

    private void doRemoveAll() {
        if (mData == null) return;
        mDataBackup = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            mDataBackup.add(mData.get(i));
            mDataBackup.remove(Status.BREAKDOWN);
        }
        // 清空除了总线故障之外的所有状态
        if (mData.contains(Status.BREAKDOWN)) {
            mData.clear();
            mData.add(Status.BREAKDOWN);
        } else {
            mData.clear();
        }

        notifyDataSetChanged();
        calGridViewSumWH(9, mView);
    }

    private void doAddStatus(Status status) {
        if (mData.contains(status)) return;
        mData.add(status);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mData.sort((o1, o2) -> o1.ordinal() - o2.ordinal());
        }
        notifyDataSetChanged();
        calGridViewSumWH(9, mView);
    }

    private void doRemoveStatus(Status status) {
        if (!mData.contains(status)) return;
        mData.remove(status);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mData.sort((o1, o2) -> o1.ordinal() - o2.ordinal());
        }
        notifyDataSetChanged();
        calGridViewSumWH(9, mView);
    }

    public static void calGridViewSumWH(int numColumns , GridView gridView) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int totalWidth = 0;
        int itemSpacing = 32;


        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalWidth = listItem.getMeasuredWidth();
            totalHeight = listItem.getMeasuredHeight();
        }
        if (listAdapter.getCount() <= numColumns) {
            gridView.setNumColumns(listAdapter.getCount());
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();

        if (listAdapter.getCount() % 2 == 0) {
            params.width = (totalWidth + itemSpacing) * listAdapter.getCount();
        } else {
            params.width = (totalWidth + itemSpacing) * listAdapter.getCount() - itemSpacing;
        }
        params.height = totalHeight;

        gridView.setLayoutParams(params);
    }
}
