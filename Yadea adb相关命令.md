- 强制退出yadea launcher

adb shell am force-stop com.yadea.launcher

- 启动原生设置应用

adb shell am start com.android.settings 

- 启动原生音乐

adb shell am start com.android.music

- 熄屏状态唤醒屏幕

adb shell input keyevent 26

- 卸载yadea Launcher

adb root
adb remount
adb shell rm /system/priv-app/YadeaLauncher/YadeaLauncher.apk

- 获取当前显示的activity

adb shell dumpsys window | grep mCurrentFocus



```
// 添加工模App入口
Intent intent = new Intent(Intent.ACTION_MAIN, null);
intent.addCategory(Intent.CATEGORY_LAUNCHER);
List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
Collections.sort(infos, new ResolveInfo.DisplayNameComparator(pm));
if (infos != null) {
    for (ResolveInfo info : infos) {
        Log.d(TAG, "app info: " + info.activityInfo.packageName);
        if (info.activityInfo.packageName.equals("com.yha.factory")) {
            addFactoryAppByLabel(info, pm);
        }

        if (info.activityInfo.packageName.equals("com.yha.runtime")) {
            addFactoryAppByName(info, pm);
        }


        if (info.activityInfo.packageName.equals("com.huawei.android.gpsselfcheck01")) {
            addGPSSelfCheck();
        }
        if (info.activityInfo.packageName.equals("com.nextdoordeveloper.miperf.miperf")) {
            addIperf();
        }
        if (info.activityInfo.packageName.equals("com.yep.autoreboot")) {
            addSIGNED_AutoReboot();
        }
        //add third party applications
        if (info.activityInfo.packageName.equals("cn.kuwo.kwmusiccar")) {
            addKwPlay();
        }
        if (info.activityInfo.packageName.equals("com.edog.car")) {
            addKradio();
        }
        if (info.activityInfo.packageName.equals("com.tencent.wecarflow")) {
            addWeCarFlow();
        }
        if (info.activityInfo.packageName.equals("com.autonavi.amapauto")) {
            addAmap();
        }
    }
}
addCustomMenuItem("FAULT", "com.yadea.launcher.fault");
addCustomMenuItem("VERSION", "com.yadea.launcher.version");
```

```
android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen">
```





[core]

​    editor = code -w



[alias]

​    st=status

​    br=branch

   amend = commit --amend

   amendf = commit --amend --no-edit

   ct = commit

   co = checkout

   cp = cherry-pick

   df = diff

   ds = diff --staged

   l = log

   lg = log --graph --all --format=format:'%C(bold blue)%h%C(reset) - %C(bold green)(%ar)%C(reset) %C(white)%s%C(reset) %C(bold white)— %an%C(reset)%C(bold yellow)%d%C(reset)' --abbrev-commit --date=relative

   lp = log --pretty=oneline

   sa = stash apply

   sh = show

   ss = stash save







[user]

name = 101012734

email = maxiang@huaqin.com



[review "192.168.132.189:8081"]

username = 101012734



[review "gerrit.huaqin.com:8081"]

username = 101012734



[review "https://gerrit7.huaqin.com:9443"]

username = 101012734



\#1.无锡端

\# ======================================================================== #

\# Huaqin--gerrit

\# ======================================================================== #



[url "ssh://101012734@10.102.17.30:29418"]

insteadOf = ssh://101012734@192.168.130.189:29418

insteadOf = ssh://101012734@192.168.130.181:29418

insteadOf = ssh://101012734@10.100.35.42:29418



[url "ssh://101012734@192.168.130.189:29418"]

pushInsteadOf = ssh://101012734@10.100.35.42:29418

pushInsteadOf = ssh://101012734@192.168.130.181:29418

pushInsteadOf = ssh://101012734@10.102.17.30:29418



\# ======================================================================== #

\# Huaqin--gerrit7

\# ======================================================================== #



[url "ssh://101012734@10.102.17.48:29418"]

insteadOf = ssh://101012734@192.168.128.85:29418

insteadOf = ssh://101012734@10.100.30.63:29418



[url "ssh://101012734@192.168.128.85:29418"]

pushInsteadOf = ssh://101012734@10.100.30.63:29418

pushInsteadOf = ssh://101012734@10.102.17.48:29418



\# ======================================================================== #

\# Huaqin--gerrit3

\# ======================================================================== #

[url "ssh://101012734@10.107.17.52:29418"]

insteadOf = ssh://101012734@192.168.132.189:29418

insteadOf = ssh://101012734@10.100.30.24:29418



[url "ssh://101012734@192.168.132.189:29418"]

pushInsteadOf = ssh://101012734@192.168.132.65:29418

pushInsteadOf = ssh://101012734@10.100.30.24:29418

pushInsteadOf = ssh://101012734@10.107.17.52:29418



\# ======================================================================== #

\# Huaqin--gerrit2

\# ======================================================================== #

[url "ssh://101012734@10.102.17.51:29418"]

insteadOf = ssh://101012734@192.168.130.125:29418

insteadOf = ssh://101012734@10.100.30.23:29418



[url "ssh://101012734@192.168.130.125:29418"]

pushInsteadOf = ssh://101012734@192.168.130.65:29418

pushInsteadOf = ssh://101012734@10.100.30.23:29418

pushInsteadOf = ssh://101012734@10.102.17.51:29418



\# ======================================================================== #

\# Huaqin--awsgerrit03

\# ======================================================================== #

\#[url "ssh://101012734@10.100.35.71:29418"]

\#[url "ssh://101012734@10.100.35.71:29418"]





\# ======================================================================== #

\# Basin .gitconfig

\# ======================================================================== #

\# gerrit6:

\# [url "ssh://ext-huaqin-maxiang@usw2-acos2014-gerrit-mirror.labcollab.net:9418/"]

\# [url "ssh://ext-huaqin-maxiang@cnn1-acos2014-gerrit-mirror.labcollab.net:9418/"]

[url "ssh://ext-huaqin-maxiang@gerrit6-mirror-cnn.labcollab.net:9418/"]

​    insteadOf = git://gerrit6.labcollab.net/

​    insteadOf = https://gerrit6.labcollab.net/gerrit/

​    insteadOf = http://gerrit6.labcollab.net/gerrit/

​    insteadOf = ssh://gerrit6.labcollab.net:9418/

​    insteadOf = ssh://gerrit6.labcollab.net/

​    insteadOf = ssh://ext-huaqin-maxiang@gerrit6.labcollab.net:9418/



[url "ssh://ext-huaqin-maxiang@gerrit6.labcollab.net:9418/"]

​    pushInsteadOf = git://gerrit6-mirror-cnn.labcollab.net/

​    pushInsteadOf = ssh://gerrit6-mirror-cnn.labcollab.net:9418/

​    pushInsteadOf = ssh://ext-huaqin-maxiang@gerrit6-mirror-cnn.labcollab.net:9418/

​    pushInsteadOf = git://gerrit6.labcollab.net/

​    pushInsteadOf = http://gerrit6.labcollab.net/gerrit/

​    pushInsteadOf = https://gerrit6.labcollab.net/gerrit/

​    pushInsteadOf = ssh://gerrit6.labcollab.net:9418/

​    pushInsteadOf = ssh://gerrit6.labcollab.net/

​    pushInsteadOf = ssh://ext-huaqin-maxiang@gerrit6.labcollab.net:9418/



\# vsb-gerrit:

\# [url "ssh://ext-huaqin-maxiang@usw2-vsb-gerrit-mirror.labcollab.net:9418/"]

[url "ssh://ext-huaqin-maxiang@cnn1-vsb-gerrit-mirror.labcollab.net:9418/"]

​    insteadOf = git://vsb-gerrit.labcollab.net/

​    insteadOf = https://vsb-gerrit.labcollab.net/gerrit/

​    insteadOf = http://vsb-gerrit.labcollab.net/gerrit/

​    insteadOf = ssh://vsb-gerrit.labcollab.net:9418/

​    insteadOf = ssh://vsb-gerrit.labcollab.net/

​    insteadOf = ssh://ext-huaqin-maxiang@vsb-gerrit.labcollab.net:9418/



[url "ssh://ext-huaqin-maxiang@vsb-gerrit.labcollab.net:9418/"]

​    pushInsteadOf = git://cnn1-vsb-gerrit-mirror.labcollab.net/

​    pushInsteadOf = ssh://cnn1-vsb-gerrit-mirror.labcollab.net:9418/

​    pushInsteadOf = ssh://ext-huaqin-maxiang@cnn1-vsb-gerrit-mirror.labcollab.net:9418/

​    pushInsteadOf = git://vsb-gerrit.labcollab.net/

​    pushInsteadOf = http://vsb-gerrit.labcollab.net/gerrit/

​    pushInsteadOf = https://vsb-gerrit.labcollab.net/gerrit/

​    pushInsteadOf = ssh://vsb-gerrit.labcollab.net:9418/

​    pushInsteadOf = ssh://vsb-gerrit.labcollab.net/

​    pushInsteadOf = ssh://ext-huaqin-maxiang@vsb-gerrit.labcollab.net:9418/

\# ======================================================================== #

\# Basin .gitconfig

\# ======================================================================== #