package com.goodocom.gocsdk.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> listFragments;

    public FragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.listFragments = list;
    }

    @Override // android.support.v4.app.FragmentPagerAdapter
    public Fragment getItem(int arg0) {
        return this.listFragments.get(arg0);
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        return this.listFragments.size();
    }
}
