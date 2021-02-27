package com.yadea.launcher.activity;
/**
 * TODO:
 * 菜单页待优化:
 * 数据预加载
 * 数据保存
 * 应用排序顺序
 * 圆点指示器是否符合客户要求
 */

import androidx.annotation.LongDef;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.yadea.launcher.LauncherApplication;
import com.yadea.launcher.R;
import com.yadea.launcher.adapter.NavigationGridviewAdapter;
import com.yadea.launcher.adapter.NavigationViewPagerAdapter;
import com.yadea.launcher.bean.AppInfoBean;
import com.yadea.launcher.receiver.InformationReceiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.yadea.launcher.receiver.InformationReceiver.PACKAGE_ADD;
import static com.yadea.launcher.receiver.InformationReceiver.PACKAGE_REMOVED;

public class MenuActivity extends Activity {

    private static final String TAG = "MenuActivity";

    /**
     * Gridview中展示item的数量
     */
    public static final int ITEM_GRID_NUM = 8;

    /**
     * Gridview中一行显示的数量
     */
    public static final int ITEM_GIRD_COLUMNS_NUM = 4;

    /**
     * 存储应用数据List
     */
    List<AppInfoBean> mAppsInfoList;

    /**
     * 菜单应用页数
     */
    int mAppPageSizes;

    /**
     * 圆点指示器
     */
    private LinearLayout mLl_DotIndicator;

    /**
     * 检测滑动位置
     */
    float startX;
    float endX;

    /**
     * 存储gridView
     */
    private List<View> gridList;

    /**
     * 上一次所在页面Index
     */
    private int mLastPageIndex = 0;

    private ViewPager mViewPager;

    private NavigationViewPagerAdapter mNavigationViewPagerAdapter;

    private PageChangeListener mPageChangeListener;

    private InformationReceiver mReceiver;

    private LauncherApplication mApplication;

    //TODO::测试时间,待删除
    long startTime;
    long endTime;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: ");
        startTime = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
        //TODO::菜单页加载的信息待优化
        initViewsAndDatas();

        endTime = System.currentTimeMillis();
        Log.d(TAG, "onCreate: 耗费时间" + (endTime - startTime));

    }

     private void init(){
         gridList = new ArrayList<>();
         mAppsInfoList = new ArrayList<>();
         //初始化ViewPager
         mViewPager = (ViewPager) findViewById(R.id.view_pager);
         //设置ViewPager适配器
         mNavigationViewPagerAdapter = new NavigationViewPagerAdapter();
         mViewPager.setAdapter(mNavigationViewPagerAdapter);

         mReceiver = new InformationReceiver();
         registerBroadcastReceiver();

         mApplication = (LauncherApplication) getApplicationContext();
         mApplication.setInformationListener(mInfoListener);


     }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                endX = ev.getX();
                Log.d(TAG, "dispatchTouchEvent: down"+ startX);
                Log.d(TAG, "dispatchTouchEvent: UP"+ endX);
                Log.d(TAG, "dispatchTouchEvent: " + mViewPager.getCurrentItem());
                if(mViewPager.getCurrentItem() == 0 && (startX < 250) && (endX - startX) > 150){
                    Log.d(TAG, "dispatchTouchEvent: 右滑");
                    finish();
                    overridePendingTransition(0,R.anim.out_to_right);

                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    /**
     * 加载其他页面、菜单页数据、和圆点指示器
     * 首次加载时，直接加载数据
     * 当有删减应用的情况时，清空gridList中除Launcher页的其他页，重新加载。
     */
    public void initViewsAndDatas() {
        //有删减应用的情况时
        if (mAppsInfoList.size() > 0) {
            gridList.clear();
            mAppsInfoList.clear();
        } else {
            //首次加载应用数据,并保存在list中
        }
        loadAppInfomation();
        for (int i = 0; i < mAppPageSizes; i++) {
            //每个页面inflate出一个新实例
            GridView gridView = new GridView(this);
            gridView.setColumnWidth(500);
            gridView.setNumColumns(ITEM_GIRD_COLUMNS_NUM);
            gridView.setGravity(Gravity.CENTER);
            //设置每行的间距
            gridView.setVerticalSpacing(60);
            //设置每列的间距
            gridView.setHorizontalSpacing(20);
            gridList.add(gridView);

            NavigationGridviewAdapter mGdAdapter = new NavigationGridviewAdapter(mAppsInfoList, i, ITEM_GRID_NUM);
            gridView.setAdapter(mGdAdapter);
            //为gridView中的item设置click监听器
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(mAppsInfoList.get((int) mGdAdapter.getItemId(position)).getAppIntent());
                }
            });
        }

        mNavigationViewPagerAdapter.add(gridList);
        mPageChangeListener = new PageChangeListener();
        mViewPager.setOnPageChangeListener(mPageChangeListener);
        // 初始化圆点指示器
        mLl_DotIndicator = findViewById(R.id.dot_indicator);
        setIndictor();
    }

    /**
     * 加载菜单页的数据
     */
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
                Log.d(TAG, "loadAppInfomation: " + app.toString());
            }
        }
        //计算viewPager需要显示几页
        mAppPageSizes = (int) Math.ceil(mAppsInfoList.size() * 1.0 / ITEM_GRID_NUM);
    }
    //LinearLayout.LayoutParams mSelectLayoutParams = new LinearLayout.LayoutParams(42, 15);
    //LinearLayout.LayoutParams mUnSelectLayouParams = new LinearLayout.LayoutParams(15, 15);

    //设置圆点指示器
    private void setIndictor() {
        View viewIndicator;
        mLl_DotIndicator.removeAllViewsInLayout();
        //加上表盘页
        for (int i = 0; i < mAppPageSizes; i++) {
            //创建imageview作为小圆点
            viewIndicator = new View(this);
            //设置默认背景
            viewIndicator.setBackgroundResource(R.drawable.dot_selector);
            viewIndicator.setEnabled(false);

            LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(30,30);;


            if (i != 0) {
                //设置指示器宽高
                mLayoutParams.leftMargin = 20;

            }
            // 设置布局参数
            viewIndicator.setLayoutParams(mLayoutParams);
            //添加指示器到布局
            mLl_DotIndicator.addView(viewIndicator);
        }
        //设置当前所处页面指示器
        mLl_DotIndicator.getChildAt(mViewPager.getCurrentItem()).setEnabled(true);
    }

    //注册广播接收器
    private void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        //安装应用更改状态
        filter.addAction(PACKAGE_ADD);
        filter.addAction(PACKAGE_REMOVED);
        filter.addDataScheme("package");
        registerReceiver(mReceiver, filter);
    }


    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {
        }

        //滑动时调用此方法,滑动停止前一直回调此方法
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        //当新的页面被选中时调用
        @Override
        public void onPageSelected(int position) {
            if (position >= 0 && position < mAppPageSizes + 1) {
                mLl_DotIndicator.getChildAt(mLastPageIndex).setEnabled(false);
                mLl_DotIndicator.getChildAt(position).setEnabled(true);
                mLastPageIndex = position;
            }

        }
    }


    private LauncherApplication.InformationListener mInfoListener = new LauncherApplication.InformationListener() {
        @Override
        public void onWifiStatusChanged(boolean status) {
        }

        @Override
        public void onBlueToothStatusChanged(boolean status) {
        }

        @Override
        public void onGpsStatusChanged(boolean status) {
        }

        @Override
        public void onSignalStatusChanged(boolean status) {
        }

        @Override
        public void onAppStatusChanged(int status, String pkgName) {
            initViewsAndDatas();
        }
    };

}