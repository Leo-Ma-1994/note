## step 1

准备数据

## step 2

item布局





axel -n 100 http://10.102.17.68/cloud/v4/patchDFS/DCC/IP3057_119__SACP001/ip3057/IP3057_119__SACP001_patchbuild_20201104110641/IP3057_119__SACP001_patchbuild_20201104110641_DCC/IP3057_119__SACP001_patchbuild_20201104110641.rar







```Java
package com.leo.viewdemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.leo.viewdemo.model.FaultInfo;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class FaultActivity extends Activity {


    //字体
    private Typeface mTextfont;
    private Typeface mItemfont;
    private TextView mReturnText;
    private TextView mFaultTitleText;
    private TextView mCancelText;
    private Button mReturn_btn;

    //存储故障信息
    private SharedPreferences faultSP;
    private SharedPreferences.Editor editor;
    Set<FaultInfo> setFault;



    private  List<String> mFaultDesc;

    private GridView mFaultGrid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault);
        overridePendingTransition(R.anim.right_to_left_in, R.anim.left_to_right_out);

        mTextfont = Typeface.createFromAsset(getAssets(), "fonts/MicroTechnic.otf");
        mReturnText = findViewById(R.id.textReturn);
        mCancelText = findViewById(R.id.text_clean);
        mReturn_btn = findViewById(R.id.btn_return);
        mFaultTitleText = findViewById(R.id.text_fault_title);
        mReturnText.setTypeface(mTextfont);
        mCancelText.setTypeface(mTextfont);
        mFaultTitleText.setTypeface(mTextfont);


        faultSP = getSharedPreferences("fault_info", MODE_PRIVATE);
        editor = faultSP.edit();
        mFaultGrid = findViewById(R.id.fault_grid);

        try {
            addFaultInfo();
            showFaultInfo();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        

        mReturn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //切换动画
                overridePendingTransition(R.anim.right_to_left_in, R.anim.left_to_right_out);
            }
        });
    }


    /**
     * 添加存储信息
     */
    private void addFaultInfo() throws IOException {
        editor.putString("1",serialize(new FaultInfo("01","扳手故障", false)));
        editor.putString("2",serialize(new FaultInfo("02","转把故障", false)));
        editor.putString("3",serialize(new FaultInfo("03","电机故障", false)));
        editor.putString("4",serialize(new FaultInfo("04","BMS通讯故障", false)));
        editor.putString("5",serialize(new FaultInfo("05","MCU通讯故障", false)));
        editor.putString("6",serialize(new FaultInfo("06","充电器通讯故障", false)));
        editor.putString("7",serialize(new FaultInfo("07","上位机通讯故障", false)));
        editor.commit();
        //    扳手故障、转把故障、电机故障
//    BMS通讯故障、MCU通讯故障、充电器通讯故障、上位机通讯故障
//
    }

    /**
     * 显示故障信息
     */
    private void showFaultInfo() throws IOException, ClassNotFoundException {
       Map<String, ?> faultMap =faultSP.getAll();
       mFaultDesc = new ArrayList<>();
       FaultInfo faultInfo;

        Iterator iter = faultMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String val = (String) entry.getValue();
            faultInfo = deSerialization((String)val);
            if(faultInfo.isFault()){
                mFaultDesc.add(faultInfo.getDescription());
            }
        }
        String[] strings = new String[mFaultDesc.size()];
        mFaultGrid.setAdapter(new GridAdapter(mFaultDesc.toArray(strings), this));


    }

    /**
     * 序列化对象
     */
    private String serialize(FaultInfo object) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }


    /**
     * 反序列化对象
     *

     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private FaultInfo deSerialization(String str) throws IOException,
            ClassNotFoundException {


        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        FaultInfo object = (FaultInfo) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();

        return object;
    }

    /**
     * 模拟发送故障信息
     */






}
```