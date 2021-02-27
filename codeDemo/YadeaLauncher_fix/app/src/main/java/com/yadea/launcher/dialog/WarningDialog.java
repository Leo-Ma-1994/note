package com.yadea.launcher.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yadea.launcher.R;

public class WarningDialog extends Dialog {

    private int FLAG_DISMISS = 1;
    private boolean flag = true;
    public WarningDialog(@NonNull Context context) {
        super(context);
    }

    public WarningDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WarningDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.layout_dialog_warning);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    @Override
    public void show() {
        super.show();
        mThread.start();

    }

    private Thread mThread = new Thread(){
        @Override
        public void run() {
            super.run();
            while (flag){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = mHandler.obtainMessage();
                msg.what = FLAG_DISMISS;
                mHandler.sendMessage(msg);
            }
        }
    };

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == FLAG_DISMISS){
                dismiss();
            }
        }
    };
}
