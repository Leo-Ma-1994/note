package com.yadea.launcher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yadea.launcher.LauncherApplication;
import com.yadea.launcher.R;
import com.yadea.launcher.api.CarProps;

public class VersionActivity extends Activity {
    private static final String TAG = "VersionActivity";
    private LauncherApplication mApplication;

    private Button mReturnBtn;
    private ImageView mBackgroundView;
    private TextView mDashboardTv;
    private TextView mControllerTv;
    private TextView mMotorControllerTv;
    private TextView mBms0Tv;
    private TextView mBms1Tv;
    private TextView mChargeTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
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

    private void findViews() {
        mReturnBtn = findViewById(R.id.btn_version_return);
        mDashboardTv = findViewById(R.id.tv_dashboard);
        mControllerTv = findViewById(R.id.tv_controller);
        mMotorControllerTv = findViewById(R.id.tv_motor_controller);
        mBms0Tv = findViewById(R.id.tv_bms1);
        mBms1Tv = findViewById(R.id.tv_bms2);
        mChargeTv = findViewById(R.id.tv_charge);
        mBackgroundView = findViewById(R.id.version_bg);
    }

    private void init() {
        String iotVer, bms0Ver, bms1Ver, mcuVer;
        int mainVer, subVer;
        mainVer = mApplication.getProp(CarProps.IOT_MAIN_VERSION, 0);
        subVer = mApplication.getProp(CarProps.IOT_SUB_VERSION, 0);
        iotVer = mainVer + "." + subVer;
        mainVer = mApplication.getProp(CarProps.BMS0_MAIN_VERSION, 0);
        subVer = mApplication.getProp(CarProps.BMS0_SUB_VERSION, 0);
        bms0Ver = mainVer + "." + subVer;
        mainVer = mApplication.getProp(CarProps.BMS1_MAIN_VERSION, 0);
        subVer = mApplication.getProp(CarProps.BMS1_SUB_VERSION, 0);
        bms1Ver = mainVer + "." + subVer;
        mainVer = mApplication.getProp(CarProps.MCU_MAIN_VERSION, 0);
        subVer = mApplication.getProp(CarProps.MCU_SUB_VERSION, 0);
        mcuVer = mainVer + "." + subVer;

        mControllerTv.setText(iotVer);
        mBms0Tv.setText(bms0Ver);
        mBms1Tv.setText(bms1Ver);
        mMotorControllerTv.setText(mcuVer);
        // TODO:: 获取仪表盘版本和充电机版本


        mReturnBtn.setOnClickListener(v -> {
            finish();
            //切换动画
            overridePendingTransition(R.anim.right_to_left_in, R.anim.left_to_right_out);
        });
    }

}
