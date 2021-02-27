package dongzhong.testforfloatingwindow;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by dongzhong on 2018/5/30.
 */

public class FloatingButtonService extends Service {
    private static final String TAG = "Service";

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private MyView myView;

    private MyReceiver myReceiver;


    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LEO");
        myReceiver = new MyReceiver();
        registerReceiver(myReceiver, intentFilter);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = 500;
        layoutParams.height = 100;
        layoutParams.x = 300;
        layoutParams.y = 300;
        showFloatingWindow();
        super.onCreate();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }


    @Override
    public void unbindService(ServiceConnection conn) {
        Log.d(TAG, "unbindService: ");
        super.unbindService(conn);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //注册接收广播


//        Notification notification = new Notification(R.drawable.ic_launcher_background,
//                getString(R.string.app_name), System.currentTimeMillis());
//
//        startForeground(0x111, notification);
        Log.d(TAG, "onStartCommand: ");



        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);

    }

    private void showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {
            Log.d(TAG, "showFloatingWindow: ");
            myView = new MyView(getApplicationContext());
            myView.setBackgroundColor(Color.BLUE);
            windowManager.addView(myView, layoutParams);
            myView.setAlpha(0);

        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    public class MyReceiver extends BroadcastReceiver {
        private final String TAG = "MyReceiver";
        //页面1是否显示
        private Boolean isShow = true;
        //页面2是否显示
        private  boolean isShow2 = true;

        @Override
        public void onReceive(Context context, Intent intent) {
            if("LEO".equals(intent.getAction())){
                isShow = intent.getBooleanExtra("isShow" , true);
                isShow2 = intent.getBooleanExtra("isShow2", true);
                Log.d(TAG, "onReceive, isShow: " + isShow + ".....isShow2:"+ isShow2);
                if(isShow && isShow2){
                    myView.setAlpha(0.4f);
                }else {
                    myView.setAlpha(0);
                }
            }
        }
    }







}
