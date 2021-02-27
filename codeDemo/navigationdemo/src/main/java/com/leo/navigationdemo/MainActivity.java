package com.leo.navigationdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.leo.navigationdemo.adapter.NavigationGridviewAdapter;
import com.leo.navigationdemo.adapter.NavigationViewPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ViewPager中第一页为Launcher界面
 * 其余为应用页.
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    /**
     * Launcher view页
     */
    private static final int LAUNCHER_VIEW = R.layout.guid_view1;

    /**
     * Gridview中展示item的数量
     */
    public static final int ITEM_GRID_NUM = 10;

    /**
     * Gridview中一行显示的数量
     */
    public static final int ITEM_GIRD_COLUMNS_NUM = 5;

    /**
     * 存储应用数据List
     */
    List<AppInfoBean> mAppsInfoList;

    //页面页数
    int pageSizes;


    private List<View> gridList = new ArrayList<>();

    private ViewPager mNavigationViewPager;
    private NavigationViewPagerAdapter mNavigationViewPagerAdapter;


    /**
     * TODO
     * 圆点指示器
     */
    private LinearLayout mLl_DotIndicator;

    private int mCurrentItem = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDatas();

        mNavigationViewPager.addOnPageChangeListener(new PageChangeListener());
        setIndictor();

    }

    /**
     * 初始化页面
     */
    public void initViews() {
        mAppsInfoList = new ArrayList<>();
        //初始化ViewPager
        mNavigationViewPager = (ViewPager) findViewById(R.id.view_pager);
        mNavigationViewPagerAdapter = new NavigationViewPagerAdapter();
        mNavigationViewPager.setAdapter(mNavigationViewPagerAdapter);

        //加载Launcher view
        View view = LayoutInflater.from(this).inflate(LAUNCHER_VIEW, null);
        gridList.add(view);
        //TODO
        // 初始化圆点指示器
        mLl_DotIndicator = findViewById(R.id.dot_indicator);
    }

    /**
     * 初始化数据
     */
    public void initDatas() {

        //从package中获取应用的相关数据,包括应用名,intent和图标.
        if (mAppsInfoList.size() > 0) {
            mAppsInfoList.clear();
            loadAppInfomation();
        }
        //加载应用数据,并保存在list中
        loadAppInfomation();

        //计算viewPager需要显示几页
        pageSizes = (int) Math.ceil(mAppsInfoList.size() * 1.0 / ITEM_GRID_NUM);

        for (int i = 0; i < pageSizes; i++) {
            //每个页面inflate出一个新实例
            GridView gridView = new GridView(this);
            gridView.setNumColumns(ITEM_GIRD_COLUMNS_NUM);

            //设置每行的间距
            gridView.setVerticalSpacing(50);
            //设置每列的间距
            gridView.setHorizontalSpacing(20);
            NavigationGridviewAdapter gdAdapter = new NavigationGridviewAdapter(mAppsInfoList, i, ITEM_GRID_NUM);
            gridView.setAdapter(gdAdapter);
            gridList.add(gridView);

            //为gridView中的item设置click监听器
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    startActivity(mAppsInfoList.get((int) gdAdapter.getItemId(position)).getAppIntent());
                }
            });
        }
        mNavigationViewPagerAdapter.add(gridList);
    }


    //设置圆点指示器
    private void setIndictor() {
        View viewIndicator;
        //加上表盘页
        for (int i = 0; i < pageSizes + 1; i++) {
            //创建imageview作为小圆点
            viewIndicator = new View(this);
            //设置默认背景
            viewIndicator.setBackgroundResource(R.drawable.dot_selector);
            viewIndicator.setEnabled(false);
            //设置指示器宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
            //除了第一个小圆点，其他小圆点都设置边距
            if (i != 0) {
                layoutParams.leftMargin = 20;
            }
            // 设置布局参数
            viewIndicator.setLayoutParams(layoutParams);
            //添加指示器到布局
            mLl_DotIndicator.addView(viewIndicator);

        }
        //默认选中第一个指示器
        mLl_DotIndicator.getChildAt(0).setEnabled(true);
    }


    //加载应用数据
    private void loadAppInfomation() {
        PackageManager pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        Collections.sort(infos, new ResolveInfo.DisplayNameComparator(pm));
        if (infos != null) {
            mAppsInfoList.clear();
            for (ResolveInfo info : infos) {
                AppInfoBean app = new AppInfoBean();

                app.setAppName(info.loadLabel(pm).toString());
                app.setIcon(info.loadIcon(pm));
                app.setPkgName(info.activityInfo.packageName);
                app.setAppIntent(pm.getLaunchIntentForPackage(info.activityInfo.packageName));
                mAppsInfoList.add(app);
            }
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        /*当滑动状态改变时调用*/

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        /*当前页面被滑动时调用*/

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /*当新的页面被选中时调用*/
        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected: " + position);
            Log.d(TAG, "onPageSelected: " + mCurrentItem);
            if (position >= 0 && position < pageSizes + 1) {
                mLl_DotIndicator.getChildAt(mCurrentItem).setEnabled(false);
                mLl_DotIndicator.getChildAt(position).setEnabled(true);
                mCurrentItem = position;

            }

        }
    }
}