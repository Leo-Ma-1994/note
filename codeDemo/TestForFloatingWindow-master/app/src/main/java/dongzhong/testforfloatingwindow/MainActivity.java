package dongzhong.testforfloatingwindow;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FloatingButtonService.MyReceiver myReceiver;
    WindowManager windowManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        setContentView(R.layout.activity_main);

//        if (FloatingButtonService.isStarted) {
//            return;
//        }
        startFloatingButtonService();

    }

    @Override
    protected void onResume() {
        //launcher 显示时，发送将悬浮窗不可见的广播
        Intent intent = new Intent("LEO");  //Itent就是我们要发送的内容
        intent.putExtra("isShow", false);
        sendBroadcast(intent);   //发送广播
        super.onResume();

    }

    @Override
    protected void onPause() {
        //Launcher退出时，发送将悬浮窗可见的广播
        Intent intent = new Intent("LEO");  //Itent就是我们要发送的内容
        intent.putExtra("isShow", true);
        sendBroadcast(intent);   //发送广播
        super.onPause();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }



    public void startFloatingButtonService() {
//        if (FloatingButtonService.isStarted) {
//            return;
//        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        } else {
            startService(new Intent(MainActivity.this, FloatingButtonService.class));
        }
    }




}
