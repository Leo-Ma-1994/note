package com.yadea.launcher.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.yadea.launcher.R;
import com.yadea.launcher.receiver.InformationReceiver;

import java.util.ArrayList;
import java.util.List;


public class InformationBar extends ConstraintLayout {

    public enum Info {
        SIGNAL, LOCATION, WIFI, BLUETOOTH, LAMP
    }

    private List<ImageView> mSlots = new ArrayList<>();

    private List<Info> mData = new ArrayList<>();

    public InformationBar(@NonNull Context context) {
        this(context, null);
    }

    public InformationBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InformationBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context , AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.layout_information_bar, this);
        mSlots.add(findViewById(R.id.slot_0));
        mSlots.add(findViewById(R.id.slot_1));
        mSlots.add(findViewById(R.id.slot_2));
        mSlots.add(findViewById(R.id.slot_3));
        mSlots.add(findViewById(R.id.slot_4));

        if (InformationReceiver.isGPSEnable(context)) {
            addInfo(Info.LOCATION);
        }

        if (InformationReceiver.isWifiEnable(context)) {
            addInfo(Info.WIFI);
        }

        if (InformationReceiver.is3GEnable(context)) {
            addInfo(Info.SIGNAL);
        }

        if (InformationReceiver.isBluetoothEnabled()) {
            addInfo(Info.BLUETOOTH);
        }
    }

    public void addInfo(Info info) {
        if (mData.contains(info)) return;

        mData.add(info);
        refreshInfo();
    }

    public void removeInfo(Info info) {
        mData.remove(info);
        refreshInfo();
    }

    private void refreshInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mData.sort((o1, o2) -> o1.ordinal() - o2.ordinal());
        }

        for (ImageView slot: mSlots)
            slot.setVisibility(GONE);

        for (int i = 0, len = mData.size(); i < len; i++) {
            switch (mData.get(i)) {
                case SIGNAL:
                    mSlots.get(i).setImageResource(R.drawable.ic_signal);
                    mSlots.get(i).setVisibility(VISIBLE);
                    break;
                case LOCATION:
                    mSlots.get(i).setImageResource(R.drawable.ic_location);
                    mSlots.get(i).setVisibility(VISIBLE);
                    break;
                case WIFI:
                    mSlots.get(i).setImageResource(R.drawable.ic_wifi);
                    mSlots.get(i).setVisibility(VISIBLE);
                    break;
                case BLUETOOTH:
                    mSlots.get(i).setImageResource(R.drawable.ic_bluetooth);
                    mSlots.get(i).setVisibility(VISIBLE);
                    break;
                case LAMP:
                    mSlots.get(i).setImageResource(R.drawable.ic_adaptive_lamp);
                    mSlots.get(i).setVisibility(VISIBLE);
                    break;
                default:
                    mSlots.get(i).setVisibility(GONE);
                    break;
            }
        }
    }
}



