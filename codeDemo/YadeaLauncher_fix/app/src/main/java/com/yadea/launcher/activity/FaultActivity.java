package com.yadea.launcher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.yadea.launcher.LauncherApplication;
import com.yadea.launcher.R;
import com.yadea.launcher.adapter.FaultGridAdapter;
import com.yadea.launcher.api.CarProps;
import com.yadea.launcher.api.ConstantsApi;
import com.yadea.launcher.util.LogUtils;


import java.util.ArrayList;
import java.util.List;


public class FaultActivity extends Activity {
    private static final String TAG = "FaultActivity";

    private LauncherApplication mApplication;

    private Button mReturnBtn;
    private Button mCleanBtn;
    private GridView mFaultGv;
    private ImageView  mBackgroundView;
    private FaultGridAdapter mGridAdapter;

    //显示故障信息
    private  List<String> mFaultDesc;
    private FaultEventHandler mHandler;


    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault);
        //进场动画
        overridePendingTransition(R.anim.right_to_left_in, R.anim.left_to_right_out);
        mApplication = (LauncherApplication) getApplication();

        findViews();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBackgroundView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_bg_anim));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApplication.unregisterHandler(mHandler);
        mHandler = null;
    }


    private void handleFault(int resId, boolean flag) {
        if (flag) {
            addFaultInfo(resId);
        } else {
            removeFaultInfo(resId);
        }
    }

    private void addFaultInfo(int resId) {
        String faultInfo = getString(resId);
        if (mFaultDesc.contains(faultInfo)) {
            LogUtils.w(TAG, "already has fault info: " + faultInfo);
            return;
        }
        mFaultDesc.add(faultInfo);
        mGridAdapter.notifyDataSetChanged();
    }

    private void removeFaultInfo(int resId) {
        String faultInfo = getString(resId);
        if (!mFaultDesc.contains(faultInfo)) {
            LogUtils.w(TAG, "no need to remove fault info: " + faultInfo);
            return;
        }
        mFaultDesc.remove(faultInfo);
        mGridAdapter.notifyDataSetChanged();
    }

    private void removeAllFaultInfo() {
        LogUtils.d(TAG, "remove all fault info");
        mFaultDesc.clear();
        mGridAdapter.notifyDataSetChanged();
    }

    private void findViews() {
        mBackgroundView = findViewById(R.id.fault_bg);
        mReturnBtn = findViewById(R.id.btn_fault_return);
        mCleanBtn = findViewById(R.id.btn_fault_clean);
        mFaultGv = findViewById(R.id.gv_fault_list);
    }

    private void init() {
        mHandler = new FaultEventHandler();
        mApplication.registerHandler(mHandler);
        mFaultDesc = new ArrayList<>();
        mGridAdapter = new FaultGridAdapter(mFaultDesc, this);
        mFaultGv.setAdapter(mGridAdapter);

        //返回
        mReturnBtn.setOnClickListener(v -> {
            finish();
            //切换动画
            overridePendingTransition(R.anim.right_to_left_in, R.anim.left_to_right_out);
        });

        //清空
        mCleanBtn.setOnClickListener(v -> {
            removeAllFaultInfo();
        });

        // 获取当前所有fault info
        LogUtils.d(TAG, "sync fault info from can service");
        syncFaultInfo(CarProps.WRENCH_FAULT);
        syncFaultInfo(CarProps.ENGINE_FAULT);
        syncFaultInfo(CarProps.MOTOR_FAULT);
        syncFaultInfo(CarProps.MCU_FAULT);
        syncFaultInfo(CarProps.BACKLIGHT_FAULT);
        syncFaultInfo(CarProps.EEPROM_FAULT);
        syncFaultInfo(CarProps.LIGHT_SENSOR_FAULT);
        syncFaultInfo(CarProps.LEFT_LED_FAULT);
        syncFaultInfo(CarProps.RIGHT_LED_FAULT);
    }

    private void syncFaultInfo(int faultId) {
        Message msg = Message.obtain();
        int val = mApplication.getProp(faultId, ConstantsApi.STATE_OFF);
        msg.what = CarProps.WRENCH_FAULT;
        msg.arg1 = faultId;
        msg.arg2 = val;
        mHandler.sendMessage(msg);
    }

    class FaultEventHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            LogUtils.v(TAG, "handleMessage msg: " + msg);
            int faultId = msg.arg1;
            int val = msg.arg2;
            if (!(ConstantsApi.STATE_ON == val || ConstantsApi.STATE_OFF == val)) {
                LogUtils.w(TAG, "invalid fault val: " + val);
                return;
            }
            boolean flag = ConstantsApi.STATE_ON == val;
            if (CarProps.WRENCH_FAULT == msg.what) {
                switch (faultId) {
                    case CarProps.WRENCH_FAULT:
                        handleFault(R.string.WRENCH_FAULT, flag);
                        break;
                    case CarProps.ENGINE_FAULT:
                        handleFault(R.string.ENGINE_FAULT, flag);
                        break;
                    case CarProps.MOTOR_FAULT:
                        handleFault(R.string.MOTOR_FAULT, flag);
                        break;
                    case CarProps.MCU_FAULT:
                        handleFault(R.string.MCU_FAULT, flag);
                        break;
                    case CarProps.BACKLIGHT_FAULT:
                        handleFault(R.string.BACKLIGHT_FAULT, flag);
                        break;
                    case CarProps.EEPROM_FAULT:
                        handleFault(R.string.EEPROM_FAULT, flag);
                        break;
                    case CarProps.LIGHT_SENSOR_FAULT:
                        handleFault(R.string.LIGHT_SENSOR_FAULT, flag);
                        break;
                    case CarProps.LEFT_LED_FAULT:
                        handleFault(R.string.LEFT_LED_FAULT, flag);
                        break;
                    case CarProps.RIGHT_LED_FAULT:
                        handleFault(R.string.RIGHT_LED_FAULT, flag);
                        break;
                    default:
                        LogUtils.w(TAG, "Unknown fault id: " + faultId);
                        break;
                }
            }
        }
    }

}