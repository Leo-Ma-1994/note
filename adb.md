adb pull将手机中的文件推送到电脑中。

adb pull /storage/sdcard0/TSLog/2018-08-13.txt C:\Users\Administrator\Desktop





- 显示当前所开应用的包名和activity名称

adb shell dumpsys window | grep mCurrentFocus

- 打开应用

adb shell am start com.android.music

特定页面

adb shell am start com.android.music/活动名

更换即可观看结果及地方和古巨基



```
private ImageView mImgSpeedPanel;
private ImageView mBlueHalo;
private ImageView mGreenHalo;
```



1.前置条件:需UI提供完整素材,3人/日.

2.前置条件:需UI提供完整素材,3人/日.





```
private List<View> views;
public  GuideViewPagerAdapter(List<View> views){
    super();
    this.views = views;
}

@Override
public int getCount() {
    if(views != null){
        return views.size();
    }
    return 0;
}

@Override
public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    ((ViewPager)container).removeView(views.get(position));
}

@Override
public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == ((View)object);
}

@NonNull
@Override
public Object instantiateItem(@NonNull ViewGroup container, int position) {
    ((ViewPager)container).addView(views.get(position), 0);
    return views.get(position);
}
```





```
adb root
adb remount
adb shell
cd system/priv-app/YadeaLauncher2/
rm YadeaLauncher2.apk
cd ..
rmdir YadeaLauncher2/
reboot
adb install -t ***.apk
```



1. adb shell reboot bootloader (重启进入bootloader模式)
2. fastboot flash system_a（分区名） system.img（镜像文件）
3. fastboot --set-active=a（设置启动A系统）
4. fastboot reboot (重启进入正常系统)