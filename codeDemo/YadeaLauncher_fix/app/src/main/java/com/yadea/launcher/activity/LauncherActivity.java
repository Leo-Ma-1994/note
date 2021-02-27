package com.yadea.launcher.activity;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import android.animation.ValueAnimator;
import android.app.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yadea.launcher.LauncherApplication;
import com.yadea.launcher.R;
import com.yadea.launcher.adapter.NavigationGridviewAdapter;
import com.yadea.launcher.adapter.NavigationViewPagerAdapter;
import com.yadea.launcher.adapter.StatusBarAdapter;
import com.yadea.launcher.api.CarProps;
import com.yadea.launcher.api.ConstantsApi;
import com.yadea.launcher.bean.AppInfo;
import com.yadea.launcher.bean.AppInfoBean;
import com.yadea.launcher.receiver.InformationReceiver;
import com.yadea.launcher.stateGear.GearAction;
import com.yadea.launcher.stateGear.GearBase;
import com.yadea.launcher.stateGear.StateContext;
import com.yadea.launcher.util.Constants;
import com.yadea.launcher.util.LogUtils;
import com.yadea.launcher.view.BatteryDashboardView;
import com.yadea.launcher.view.InformationBar;
import com.yadea.launcher.view.PhonePanelView;
import com.yadea.launcher.view.SpeedDashboradView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.io.File;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import android.text.TextUtils;
import android.widget.Toast;

import static com.yadea.launcher.receiver.InformationReceiver.ACTION_BT_STATE_CHANGED;
import static com.yadea.launcher.receiver.InformationReceiver.ACTION_GPS_STATE_CHANGED;
import static com.yadea.launcher.receiver.InformationReceiver.ACTION_WIFI_STATE_CHANGED;
import static com.yadea.launcher.receiver.InformationReceiver.PACKAGE_ADD;
import static com.yadea.launcher.receiver.InformationReceiver.PACKAGE_REMOVED;

public class LauncherActivity extends Activity implements GestureDetector.OnGestureListener {
    private static final String TAG = "LauncherActivity";

    //TODO
    //****************************/

    /**
     * Gridview中展示item的数量
     */
    public static final int ITEM_GRID_NUM = 12;

    /**
     * Gridview中一行显示的数量
     */
    public static final int ITEM_GIRD_COLUMNS_NUM = 6;

    /**
     * 表盘开启动画显示时间,ms计
     */
    public static final int START_ANIM_TIME = 1000;

    /**
     * 档位切换弹窗动画显示事件
     */
    public static final int SWITCH_GEAR_TIME = 1000;

    /**
     * SharedPreferences存储变量
     */
    public static final String SP_NAME = "launcher_info";
    public static final String TRIP_DRIVER_NAME = "TRIP_DRIVER";
    public static final String TOTAL_DRIVER_NAME = "TOTAL_DRIVER";

    /**
     * 存储应用数据List
     */
    List<AppInfoBean> mAppsInfoList;

    /**
     * 菜单应用页数
     */
    int mAppPageSizes;


    private GestureDetector detector;

    private View mLauncherView;
    /**
     * 圆点指示器
     */
    private LinearLayout mLl_DotIndicator;
    private int mLastPageIndex = 0;

    /**
     * 档位状态
     */
    private StateContext gearContext;

    /**
     * 速度采集计次
     */
    private int mSpeedCount = -1;

    /**
     * speed临时变量
     */
    private int mLastSpeed = -10;

    /**
     * 速度动画时间
     */
    private static final int SPEED_SHOW_DURATION = 400;

    /**
     * 消息处理
     */
    private CanEventHandler mHandler;

    /**
     * SharedPreferences用于存
     * 单次里程
     * 总计里程
     */
    private SharedPreferences mSP;
    SharedPreferences.Editor mEditor;



    //!****************************/


    /**
     * 垃圾代码,需重构
     */







    private boolean isPark = true;

    //速度信号
    private static final int SPEED_ERROR_MSG = 150;

    private int mLastBatteryCurrent = 0;
    private int mLastBatteryCurrent2 = 0;
    // 主电池无效信号
    private static final int INVALID_BATTERY = 110;
    private static final int VALID_BATTERY = 120;
    // 副电池无效信号
    private static final int INVALID_SUB_BATTERY = 130;
    private static final int VALID_SUB_BATTERY = 140;

    private static final int INVALID_BATTERY_ALL = 150;
    // 临时存放电池电量
    private int mTempBattery;
    private int mTempBatterySub;
    private int mTempSmartKey;
    private int mTempBatteryChargeState;
    // 坐垫感应 0 未坐人，1 坐人
    private int mSeatSense = 0;
    // 座锁状态 0 已锁，1 未锁
    private int mSeatLocked = 0;
    // 自适应大灯状态 0 关闭， 1 打开
    private int mLamp = 0;
    // 左右转向灯
    private int mTempSignalLeft = 0;
    private int mTempSignalRight = 0;
    // 双闪
    private int mTempSignalBoth = 0;
    // 报警状态 0 无报警 [1， 4] 报警
    private int mAlarmState = 0;
    // Iot通讯故障 1 有故障 0 无故障
    private int mIotFault = 0;

    // 主副电池模式, 默认双电池模式
    private BatteryDashboardView.PanelStatus mBatteryMode = BatteryDashboardView.PanelStatus.BATTERY;
    private BatteryDashboardView.PanelStatus mBatteryModeBackup = BatteryDashboardView.PanelStatus.BATTERY;
    private int mBatteryState = Constants.BATTERY_INIT;

    private LauncherApplication mApplication;

    /**
     * 背景相关
     */
    private ImageView mBgBarView;
    private ImageView mBgLeftHalo;
    private ImageView mBgRightHalo;


    private BatteryDashboardView mBatteryDashboard;
    private SpeedDashboradView mSpeedDashboradView;
    private InformationBar mInformationBar;
    private GridView mStatusBar;

    //中间栏相关信息
    //private TextClock mTextClock;
    private TextView mTextClock;
    private ImageView mGearImg;
    private ImageView mTripImg;
    private TextView mSingleDriverTv;
    private ImageView mKmImg1;
    private ImageView mOdoImg;
    private TextView mTotalDrriverTv;
    private ImageView mKmImg2;
    // 速度仪表盘单位控件
    private ImageView mSpeedKmImg;
    private ImageView mSignalLeft;
    private ImageView mSignalRight;
    private ImageView mReadyImg;
    //弹窗信息
    private ImageView mPopUps;
    private TextView mPopUpsTv;
    private Dialog mDialog;

    private StatusBarAdapter mStatusBarAdapter;

    //中间里程着色器
    private Shader mMileageShader;

    //时间显示
    private String mIotHour;
    private String mIotMinute;


    InformationReceiver mReceiver = new InformationReceiver();

    private final String RED_LED_DEV = "/sys/devices/platform/1000b000.pinctrl/led_red";
    private final String GREEN_LED_DEV = "/sys/devices/platform/1000b000.pinctrl/led_green";
    private final String SMART_LED_DEV = "/sys/class/leds/blue/brightness";
    private final String LIGHT_ON = "1";
    private final String LIGHT_OFF = "0";
    private final int FAULT = 1;
    private final int BAT_CHARGING = 2;
    private final int BAT_FULL = 3;

    private Handler midHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == Constants.MID_REMOVE_MSG) {
                //弹窗显示结束
                mPopUpsTv.clearAnimation();
                mPopUps.clearAnimation();
                mPopUpsTv.setVisibility(View.INVISIBLE);
                mPopUps.setVisibility(View.INVISIBLE);
                setMidBarVisibility(View.VISIBLE);
            }
        }
    };

    private void findView() {

        //背景左右呼吸,背景bar
        mBgLeftHalo = findViewById(R.id.background_left_halo);
        mBgRightHalo = findViewById(R.id.background_right_halo);
        mBgBarView = findViewById(R.id.background_bar);


        mReadyImg = findViewById(R.id.ready_img);

        //左右仪表盘
        mBatteryDashboard = findViewById(R.id.battery_dashboard);
        mSpeedDashboradView = findViewById(R.id.dashboard_speed);


        //左右转向灯
        mSignalLeft = findViewById(R.id.signal_left);
        mSignalRight = findViewById(R.id.signal_right);

        //中间栏相关
        mTextClock = findViewById(R.id.time_tc);
        mGearImg = findViewById(R.id.gear);
        mTripImg = findViewById(R.id.trip_img);
        mSingleDriverTv = findViewById(R.id.single_drive);
        mPopUps = findViewById(R.id.img_pop_ups);
//        mKmImg1 = mLauncherView.findViewById(R.id.km_img_1);
//        mOdoImg = mLauncherView.findViewById(R.id.odo_img);
        mTotalDrriverTv = findViewById(R.id.total_drive);
//        mKmImg2 = mLauncherView.findViewById(R.id.km_img_2);
//
//        mStatusBar = mLauncherView.findViewById(R.id.status_bar);
//        mInformationBar = mLauncherView.findViewById(R.id.information_bar);


        // mPopUpsTv = findViewById(R.id.tv_pop_ups);
        mSpeedKmImg = findViewById(R.id.km_img_speed);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        detector = new GestureDetector(this);
        mApplication = (LauncherApplication) getApplicationContext();

        findView();

        //TODO::DOING:初始化各种数据.apadter
        init();


        //设置中间里程着色器
//        setFontShader(mSingleDriverTv, 36);
//        setFontShader(mTotalDrriverTv, 36);
//        setFontShader(mPopUpsTv, 36);
//
//        mStatusBarAdapter = new StatusBarAdapter(this, new ArrayList<>(), mStatusBar);
//        mStatusBar.setAdapter(mStatusBarAdapter);
////        setMidBarVisibility(View.INVISIBLE);
//
//


    }

    @Override
    protected void onResume() {
        super.onResume();
        //背景光晕呼吸灯效果
        mBgLeftHalo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_bg_anim));
        mBgRightHalo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_bg_anim));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !mApplication.getStartAnim()) {
            startAppAnimation();
            mApplication.setStartAnim(true);
        }
    }

    /**
     * 每次开启表盘时动画效果
     */
    private void startAppAnimation() {

        // 开机进度条动画
        mSpeedDashboradView.showAnimation(0, 100, START_ANIM_TIME);
        mBatteryDashboard.showAnimation(0, 80, START_ANIM_TIME);
        mBatteryDashboard.showAnimation2(0, 8, START_ANIM_TIME);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setDuration(START_ANIM_TIME);
        valueAnimator.start();
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) animation.getAnimatedValue();
                mSpeedDashboradView.setmSpeed(curValue);
            }
        });
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                mSpeedDashboradView.showAnimation(100, 0, START_ANIM_TIME);
                mBatteryDashboard.showAnimation(80, 0, START_ANIM_TIME);
                mBatteryDashboard.showAnimation2(8, 0, START_ANIM_TIME);
                ValueAnimator valueAnimator = ValueAnimator.ofInt(100, 0);
                valueAnimator.setDuration(START_ANIM_TIME);
                valueAnimator.start();
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int curValue = (int) animation.getAnimatedValue();
                        mSpeedDashboradView.setmSpeed(curValue);
                    }
                });
                return false;
            }
        }).sendEmptyMessageDelayed(0, 1000);
    }


    private void init() {

        mHandler = new CanEventHandler();
        mApplication.registerHandler(mHandler);
        mApplication.setInformationListener(mInfoListener);
        registerBroadcastReceiver();


        //TODO:初始化档位状态,设置档位状态行为
        gearContext = new StateContext();
        gearContext.setAction(StateContext.PARKING, ParkingAction);
        gearContext.setAction(StateContext.ECO, EcoAction);
        gearContext.setAction(StateContext.SPORT, SportAction);
        gearContext.setAction(StateContext.REVERSE, ReverseAction);


        mSP = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        mEditor = mSP.edit();


//        mBatteryDashboard.setPhoneListener(new PhonePanelView.OnPhoneCallListener() {
//            @Override
//            public void answer() {
//                Toast.makeText(getApplicationContext(), "answer the call", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void hangup() {
//                Toast.makeText(getApplicationContext(), "hangup the call", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    /**
     * 注册广播监听 WIfi Bt GPS 的状态,应用更改状态
     */
    private void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_WIFI_STATE_CHANGED);
        filter.addAction(ACTION_BT_STATE_CHANGED);
        filter.addAction(ACTION_GPS_STATE_CHANGED);
        //安装应用更改状态
        filter.addAction(PACKAGE_ADD);
        filter.addAction(PACKAGE_REMOVED);
        filter.addDataScheme("package");
        registerReceiver(mReceiver, filter);
    }

    /**
     * 更换图片资源
     */
//    private void replaceImgSrc(int bgPic, int bgBarPic, int popPic, int gear, int showTime) {
//        mBackgroundView.setImageResource(bgPic);
    //TODO::
//        mBgBarView.setImageResource(bgBarPic);
//        mPopUps.setImageResource(popPic);
//        mGearImg.setImageResource(gear);
//        mPopUps.setVisibility(View.VISIBLE);
//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_gear_anim);
//        mPopUps.startAnimation(animation);
//        midHandler.removeMessages(MID_MSG);
//        midHandler.sendEmptyMessageDelayed(MID_MSG, showTime);
//    }

    /**
     * 设置中间信息栏的可见性
     */
    public void setMidBarVisibility(int visibility) {
        LogUtils.d(TAG, "setMidBarVisibility: " + visibility);
        mTextClock.setVisibility(visibility);
        mGearImg.setVisibility(visibility);
        mTripImg.setVisibility(visibility);
        mSingleDriverTv.setVisibility(visibility);
        mKmImg1.setVisibility(visibility);
        mOdoImg.setVisibility(visibility);
        mTotalDrriverTv.setVisibility(visibility);
        mKmImg2.setVisibility(visibility);
    }

    //弹出报警窗口
//    private void popAlarmDialog(Dialog dialog) {
//        dialog = new WarningDialog(this);
//        dialog.show();
//        ImageView bgAlarmImg = dialog.findViewById(R.id.bg_alarm);
//
//        TextView titleTv = dialog.findViewById(R.id.tv_alarm_title);
//        TextView descTv = dialog.findViewById(R.id.tv_alarm_desc);
//        setFontShader(titleTv, 67);
//        setFontShader(descTv, 29);
//        setMidBarVisibility(View.VISIBLE);
//        bgAlarmImg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_bg_anim));
//    }
//
//    //弹出设防窗口
//    private void popLockedDialog(Dialog dialog) {
//        dialog = new LockingDialog(this);
//        dialog.show();
//        ImageView bgLockImg = dialog.findViewById(R.id.bg_lock);
//        TextView titleTv = dialog.findViewById(R.id.tv_locked_title);
//        TextView descTv = dialog.findViewById(R.id.tv_locked_desc);
//        setFontShader(titleTv, 67);
//        setFontShader(descTv, 29);
//        bgLockImg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_bg_anim));
//        setMidBarVisibility(View.VISIBLE);
//        dialog.dismiss();
//    }


    /**
     * 设置字体渐变色
     *
     * @param tv
     * @param i
     */
    private void setFontShader(TextView tv, int i) {
        Shader shader = new LinearGradient(0, 0, 0, i, new int[]{0xFFFFFFFF, 0xFF586F90}, new float[]{0, 1}, Shader.TileMode.CLAMP);
        tv.getPaint().setShader(shader);
    }

    /**
     * TODO:: 更换背景资源
     * 背景光晕
     * 背景bar
     */
    private  void replaceBackgroundSrc(int bgLeftHalo, int bgRightHalo, int bgBar){
        mBgLeftHalo.setImageResource(bgLeftHalo);
        mBgRightHalo.setImageResource(bgRightHalo);
        mBgBarView.setImageResource(bgBar);
    }

    /**
     * 切换档位,更换图片资源和弹窗效果
     */
    private void replaceGearSwitchSrc(int bgLeftHalo, int bgRightHalo, int bgBar, int gearPop, int gear){
        mBgLeftHalo.setImageResource(bgLeftHalo);
        mBgRightHalo.setImageResource(bgRightHalo);
        mBgBarView.setImageResource(bgBar);
        mGearImg.setImageResource(gear);
        mPopUps.setImageResource(gearPop);
        mPopUps.setVisibility(View.VISIBLE);
        mPopUps.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_gear_anim));
        midHandler.removeMessages(Constants.MID_REMOVE_MSG);
        midHandler.sendEmptyMessageDelayed(Constants.MID_REMOVE_MSG, SWITCH_GEAR_TIME);
    }

    /**
     * 情况弹窗动画
     */
    private void clearPopAnim(){
        setMidBarVisibility(View.INVISIBLE);
        mPopUpsTv.clearAnimation();
        mPopUpsTv.setVisibility(View.INVISIBLE);
        mPopUps.clearAnimation();
        mPopUps.setVisibility(View.INVISIBLE);
    }




    private LauncherApplication.InformationListener mInfoListener = new LauncherApplication.InformationListener() {
        @Override
        public void onWifiStatusChanged(boolean status) {
            if (status)
                mInformationBar.addInfo(InformationBar.Info.WIFI);
            else
                mInformationBar.removeInfo(InformationBar.Info.WIFI);
        }

        @Override
        public void onBlueToothStatusChanged(boolean status) {
            if (status)
                mInformationBar.addInfo(InformationBar.Info.BLUETOOTH);
            else
                mInformationBar.removeInfo(InformationBar.Info.BLUETOOTH);
        }

        @Override
        public void onGpsStatusChanged(boolean status) {
            if (status)
                mInformationBar.addInfo(InformationBar.Info.LOCATION);
            else
                mInformationBar.removeInfo(InformationBar.Info.LOCATION);
        }

        @Override
        public void onSignalStatusChanged(boolean status) {
            if (status)
                mInformationBar.addInfo(InformationBar.Info.SIGNAL);
            else
                mInformationBar.removeInfo(InformationBar.Info.SIGNAL);
        }

        @Override
        public void onAppStatusChanged(int status, String pkgName) {
        }
    };

    private void setLedLight(String path, String value) {
        File file = new File(path);
        String ledData = value;
        String test = "";
        if (file.exists()) {
            try {

                FileOutputStream outStream = new FileOutputStream(file);
                outStream.write(ledData.getBytes());
                outStream.close();

                FileInputStream fs = new FileInputStream(file);
                DataInputStream ds = new DataInputStream(fs);
                while (!(TextUtils.isEmpty(test = ds.readLine()))) {
                    ledData = test;
                    Log.d(TAG, "redData ==" + ledData);
                }
                ds.close();
                fs.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, path + " not exist");
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.detector.onTouchEvent(ev);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling: e1:" + e1.getY() + "e2:" + e2.getY());
        if (e1.getX() - e2.getX() > 150 && e1.getX() > 650) {
            Log.d(TAG, "onFling: 左滑手势呼出Menu");
            startActivity(new Intent(this, MenuActivity.class));
            //设置切换动画，从右边进入
            overridePendingTransition(R.anim.in_from_right, 0);
        } else if (e2.getY() - e1.getY() > 150 && e1.getY() < 150) {
            Log.d(TAG, "onFling: 下拉手势呼出group");
            startActivity(new Intent(this, GroupActivity.class));
            overridePendingTransition(R.anim.top_in, 0);
        }else if (e2.getX() - e1.getX() > 150 && e1.getX() < 200){
            Log.d(TAG, "onFling: 右滑手势呼出导航应用");
            //TODO::待添加导航应用Intent

            startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.autonavi.amapauto")));
            overridePendingTransition(R.anim.in_from_left, 0);
        }
        return false;
    }



    /**
     * 各种档位切换时的操作
     */
    private  GearAction ParkingAction = new GearAction() {
        @Override
        public void doSwitchToParking(int bg) {
            LogUtils.w(TAG, "It is already P gear, no need to switch gear");
        }

        @Override
        public void doSwitchToEco(int bg) {
            clearPopAnim();
            replaceGearSwitchSrc(R.drawable.bg_blue_left_halo,R.drawable.bg_blue_right_halo,
                    R.drawable.ic_bg_bar_blue, R.drawable.pop_e, R.drawable.ic_gear_e);
        }

        @Override
        public void doSwitchToSport(int bg) {
            LogUtils.w(TAG, "P gear cant switch to S gear");
        }

        @Override
        public void doSwitchToReverse(int bg) {
            LogUtils.w(TAG, "P gear cant switch to R gear");

        }

        @Override
        public void doChangeBackground(int bg) {
            if(bg == StateContext.DEFAULT){
                replaceBackgroundSrc(R.drawable.bg_orange_left_halo, R.drawable.bg_orange_right_halo,
                        R.drawable.ic_bg_bar_orange);
            }else if (bg == StateContext.SEAT){
                replaceBackgroundSrc(R.drawable.bg_orange_left_halo,R.drawable.bg_orange_right_halo,
                        R.drawable.ic_bg_bar_orange);
            }else if (bg == StateContext.LOCK){
                replaceBackgroundSrc(R.drawable.bg_red_left_halo,R.drawable.bg_red_right_halo,
                        R.drawable.ic_bg_bar_red);
            }
        }
    };

    private  GearAction EcoAction = new GearAction() {
        @Override
        public void doSwitchToParking(int bg) {
            clearPopAnim();
            replaceGearSwitchSrc(R.drawable.bg_orange_left_halo,R.drawable.bg_orange_right_halo,
                    R.drawable.ic_bg_bar_orange, R.drawable.pop_p, R.drawable.ic_gear_p);
        }

        @Override
        public void doSwitchToEco(int bg) {
            LogUtils.w(TAG, "It is already E gear, no need to switch gear");

        }

        @Override
        public void doSwitchToSport(int bg) {
            LogUtils.d(TAG, "E gear switch to S gear");
            mGearImg.setImageResource(R.drawable.ic_gear_s);
        }

        @Override
        public void doSwitchToReverse(int bg) {
            //TODO::缺R档光晕,待完善
            clearPopAnim();
            replaceGearSwitchSrc(R.drawable.bg_orange_left_halo,R.drawable.bg_orange_right_halo,
                    R.drawable.ic_bg_bar_yellow, R.drawable.pop_r, R.drawable.ic_gear_r);
        }

        @Override
        public void doChangeBackground(int bg) {
            if(bg == StateContext.DEFAULT){
                replaceBackgroundSrc(R.drawable.bg_blue_left_halo, R.drawable.bg_blue_right_halo,
                        R.drawable.ic_bg_bar_blue);
            }else if (bg == StateContext.SEAT){
                replaceBackgroundSrc(R.drawable.bg_orange_left_halo,R.drawable.bg_orange_right_halo,
                        R.drawable.ic_bg_bar_orange);
            }else if (bg == StateContext.LOCK){
                replaceBackgroundSrc(R.drawable.bg_red_left_halo,R.drawable.bg_red_right_halo,
                        R.drawable.ic_bg_bar_red);
            }

        }
    };

    private  GearAction SportAction = new GearAction() {
        @Override
        public void doSwitchToParking(int bg) {
            clearPopAnim();
            replaceGearSwitchSrc(R.drawable.bg_orange_left_halo,R.drawable.bg_orange_right_halo,
                    R.drawable.ic_bg_bar_orange, R.drawable.pop_p, R.drawable.ic_gear_p);
        }

        @Override
        public void doSwitchToEco(int bg) {
            LogUtils.d(TAG, "S gear switch to E gear");
            mGearImg.setImageResource(R.drawable.ic_gear_e);
        }

        @Override
        public void doSwitchToSport(int bg) {
            LogUtils.w(TAG, "It is already S gear, no need to switch gear");
        }

        @Override
        public void doSwitchToReverse(int bg) {
            LogUtils.w(TAG, "S gear cant switch to R gear");
        }

        @Override
        public void doChangeBackground(int bg) {
            if(bg == StateContext.DEFAULT){
                replaceBackgroundSrc(R.drawable.bg_blue_left_halo, R.drawable.bg_blue_right_halo,
                        R.drawable.ic_bg_bar_blue);
            }else if (bg == StateContext.SEAT){
                replaceBackgroundSrc(R.drawable.bg_orange_left_halo,R.drawable.bg_orange_right_halo,
                        R.drawable.ic_bg_bar_orange);
            }else if (bg == StateContext.LOCK){
                replaceBackgroundSrc(R.drawable.bg_red_left_halo,R.drawable.bg_red_right_halo,
                        R.drawable.ic_bg_bar_red);
            }
        }
    };

    private  GearAction ReverseAction = new GearAction() {
        @Override
        public void doSwitchToParking(int bg) {
            clearPopAnim();
            replaceGearSwitchSrc(R.drawable.bg_orange_left_halo,R.drawable.bg_orange_right_halo,
                    R.drawable.ic_bg_bar_orange, R.drawable.pop_p, R.drawable.ic_gear_p);
        }

        @Override
        public void doSwitchToEco(int bg) {
            clearPopAnim();
            replaceGearSwitchSrc(R.drawable.bg_blue_left_halo,R.drawable.bg_blue_right_halo,
                    R.drawable.ic_bg_bar_blue, R.drawable.pop_e, R.drawable.ic_gear_e);

        }

        @Override
        public void doSwitchToSport(int bg) {
            LogUtils.w(TAG, "R gear cant switch to S gear");
        }

        @Override
        public void doSwitchToReverse(int bg) {
            LogUtils.w(TAG, "It is already R gear, no need to switch gear");

        }

        @Override
        public void doChangeBackground(int bg) {
            if(bg == StateContext.DEFAULT){
                //TODO::缺黄色光晕,待修改
                replaceBackgroundSrc(R.drawable.bg_orange_left_halo, R.drawable.bg_orange_right_halo,
                        R.drawable.ic_bg_bar_yellow);
            }else if (bg == StateContext.SEAT){
                replaceBackgroundSrc(R.drawable.bg_orange_left_halo,R.drawable.bg_orange_right_halo,
                        R.drawable.ic_bg_bar_orange);

            }else if (bg == StateContext.LOCK){
                replaceBackgroundSrc(R.drawable.bg_red_left_halo,R.drawable.bg_red_right_halo,
                        R.drawable.ic_bg_bar_red);
            }
        }
    };


    class CanEventHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CarProps.LEFT_LED:
                    //左转向灯
                    LogUtils.d(TAG, "LEFT_LED" + msg.arg1);
                    if(msg.arg1 == ConstantsApi.STATE_ON){
                        mSignalLeft.setVisibility(View.VISIBLE);
                    }else if(msg.arg1 == ConstantsApi.STATE_OFF){
                        mSignalLeft.setVisibility(View.INVISIBLE);
                    }
                    break;
                case CarProps.RIGHT_LED:
                    //右转向灯
                    LogUtils.d(TAG, "RIGHT_LED" + msg.arg1);
                    if(msg.arg1 == ConstantsApi.STATE_ON){
                        mSignalRight.setVisibility(View.VISIBLE);
                    }else if(msg.arg1 == ConstantsApi.STATE_OFF){
                        mSignalRight.setVisibility(View.INVISIBLE);
                    }

                    break;
                case CarProps.SPEED_VALUE:
                    //显示Speed值
                    int value = msg.arg1;
                    LogUtils.d(TAG, "SPEED_VALUE:" + msg.arg1);;
                    if(gearContext.getGear()!= StateContext.PARKING){
                        mReadyImg.setVisibility(value > 0 ? View.INVISIBLE : View.VISIBLE);
                    }
                    int realSpeed = (value > 100) ? 100 : value;
                    mSpeedCount = ++mSpeedCount % 4;
                    //判断是否需要动画
                    boolean isShowAnim = ((Math.abs(realSpeed - mLastSpeed) > 3) ? true : false && mSpeedCount == 0);
                    if(isShowAnim || realSpeed ==0){
                        int showSpeed = (int) (realSpeed * 1.05);
                        ValueAnimator valueAnimator = ValueAnimator.ofInt(mLastSpeed < 0 ? 0 : mLastSpeed, showSpeed);
                        //有效值
                        mSpeedDashboradView.clearAnimation();
                        mSpeedDashboradView.showAnimation(mLastSpeed, showSpeed,  SPEED_SHOW_DURATION);

                        valueAnimator.setDuration(SPEED_SHOW_DURATION);
                        valueAnimator.setInterpolator(realSpeed == 0 ? new AccelerateInterpolator() : new LinearInterpolator());
                        valueAnimator.start();
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                int curValue = (int) animation.getAnimatedValue();
                                mSpeedDashboradView.setmSpeed(curValue);
                            }
                        });
                        mLastSpeed = realSpeed;
                    }


                    break;
                case CarProps.TRIP_VALUE:
                    //当前里程值,需存SharedPreferences
                    LogUtils.d(TAG, "TRIP_VALUE:" + msg.arg1);
                    mSingleDriverTv.setText(msg.arg1 + "");
                    mEditor.putString(TRIP_DRIVER_NAME, String.valueOf(msg.arg1));
                    mEditor.commit();
                    break;
                case CarProps.ODO_VALUE:
                    //总计里程值,需存SharedPreferences
                    mTotalDrriverTv.setText(msg.arg1 + "");
                    mEditor.putString(TOTAL_DRIVER_NAME, String.valueOf(msg.arg1));
                    mEditor.commit();
                    break;
                case CarProps.PARKING_GEAR:
                    //档位相关处理
                    //TODO::
                    if (msg.arg1 == ConstantsApi.GEAR_PARKING) {
                        gearContext.switchToParking();
                    } else if (msg.arg1 == ConstantsApi.GEAR_ECO) {
                        gearContext.switchToECO();
                    } else if (msg.arg1 == ConstantsApi.GEAR_REVERSE) {
                        gearContext.switchToReverse();
                    } else if (msg.arg1 == ConstantsApi.GEAR_SPORT) {
                        gearContext.switchToSport();
                    }
                    break;
                case CarProps.SEAT_SENSE:
                    //坐垫感应处理
                    //STATE_ON:没坐人,STATE_OFF:坐人
                    if (msg.arg1 == ConstantsApi.STATE_ON) {
                        gearContext.setSeated(false);
                        gearContext.changeBackground();
                    } else if (msg.arg1 == ConstantsApi.STATE_OFF) {
                        gearContext.setSeated(true);
                        gearContext.changeBackground();
                    }
                    break;
                case CarProps.ELEC_SEAT_LOCK:
                    //坐垫锁处理
                    if (msg.arg1 == ConstantsApi.STATE_ON) {
                        gearContext.setLocked(false);
                        gearContext.changeBackground();
                        // gearContext
                    } else if (msg.arg1 == ConstantsApi.STATE_OFF) {
                        gearContext.setLocked(true);
                        gearContext.changeBackground();
                    }
                    break;
                case CarProps.METRIC_BRITISH:
                    LogUtils.d(TAG, "metric_british:" + msg.arg1);
                    //公里英里单位切换
                    if (msg.arg1 == ConstantsApi.STATE_OFF) {
                        // 公里
                        mKmImg1.setImageResource(R.drawable.text_km);
                        mKmImg2.setImageResource(R.drawable.text_km);
                        mSpeedKmImg.setImageResource(R.drawable.ic_speed_km_h);
                    } else if (msg.arg1 == ConstantsApi.STATE_ON) {
                        // 英里
                        mKmImg1.setImageResource(R.drawable.text_mi);
                        mKmImg2.setImageResource(R.drawable.text_mi);
                        mSpeedKmImg.setImageResource(R.drawable.ic_speed_mi_h);
                    }

                    break;
                default:
                    break;
            }
        }
    }




}

