[toc]

adb pull将手机中的文件推送到电脑中。

adb pull /storage/sdcard0/TSLog/2018-08-13.txt C:\Users\Administrator\Desktop





- 显示当前所开应用的包名和activity名称

adb shell dumpsys window | grep mCurrentFocus



## 启动/关闭基本组件

### 启动activity

adb shell am start -n ｛包名｝/｛包名｝. {活动(activity)名称}

```
adb shell am start -n com.android.browser/com.android.browser.BrowserActivity
```

### 启动service

adb shell am satrtservice  -n  {包名｝/｛包名｝. {服务(service)名称}

```
//启动service
adb shell am startservice -n com.android.traffic/com.android.traffic.maniservice
//停止service
adb shell am stopservice -n com.android.traffic/com.android.traffic.maniservice
```



### 发送broadcast

```
adb shell am broadcast -a android.net.conn.CONNECTIVITY_CHANGE
```



### 关闭应用

adb shell am force-stop {包名}

```
adb shell am force-stop com.android.browser
```



### grep过滤关键字

```
adb logcat | grep -E "keyword1|keyword2"
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