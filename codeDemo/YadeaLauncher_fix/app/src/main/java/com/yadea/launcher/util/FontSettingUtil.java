package com.yadea.launcher.util;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.yadea.launcher.R;

/**
 * 字体设置工具类
 */
public enum FontSettingUtil{
    MT_REGULAR(R.font.micro_technic),
    PF_REGULAR(R.font.pf_regular),
    PF_SEMIBOLD(R.font.pf_semibold);

    private static final String TAG = "FontSettingUtil";
    private int font;
    private FontSettingUtil(int font) {
        this.font = font;
    }
    public  int getFont(){
        return font;
    }

    public static void setTvFont(Context context, TextView textView, FontSettingUtil fonts){

        try{
            Typeface font = ResourcesCompat.getFont(context, fonts.getFont());
            textView.setTypeface(font);
        }catch (Exception e ){
            Log.w(TAG, "setFont: " + fonts + "找不到资源" );
        }

    }

    public static void setPaintFont(Context context, Paint paint, FontSettingUtil fonts){

        try{
            Typeface font = ResourcesCompat.getFont(context, fonts.getFont());
            paint.setTypeface(font);
        }catch (Exception e ){
            Log.w(TAG, "setFont: " + fonts + "找不到资源" );
        }

    }
}


