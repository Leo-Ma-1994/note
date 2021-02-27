package com.yadea.launcher.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yadea.launcher.R;
import com.yadea.launcher.activity.FaultActivity;

public class LockingDialog extends Dialog{

    public LockingDialog(@NonNull Context context) {
        super(context);
    }
    public LockingDialog(@NonNull Context context, int themeResId){
        super(context, themeResId);
    }
    public LockingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setContentView(R.layout.layout_dialog_locked);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);//按需设置
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


    }


}
