[toc]



# 启动流程

Android系统启动的最后一步是启动Launcher，在启动过程中会请求系统返回已经安装的apk信息，并将信息封装成快捷图标显示在屏幕上。



界面主要由SearchDrop TargetBar、Workspace、CellLayout、PageIndicator、Hotseat组成。

手机屏幕上左右滑好几个屏幕的叫workspace  CellLayout为单独的一屏

# bug



## 安装apk时，出现Failure [INSTALL_FAILED_VERSION_DOWNGRADE]



原因安装apk过程中，要安装的版本低于机器中已经有的apk版本。j

解决方法：卸载机器中原有的应用。

卸载时候可能会卸载不干净，



# Android apk安装路径

system /app

安卓系统自带的应用程序

system/priv-app

安卓系统自带的特权应用

data/app

用户安装的第三方应用

data/data

存放应用程序的数据

system/product/app

针对该产品的自带的应用

system/product/priv-app

针对该产品自带的特权应用



# 常用类

## Launcher

继承Activity，桌面的主界面，用来显示图标、wifget和floder等。

## LanucherMode

继承BroadReceiver，用来接受广播。加载数据

## LauncherProvider

处理数据路操作

## LauncherAppState

单例模式的全局管理类，处理数据库操作

## compat

带该后缀的都是兼容包。

## ItemInfo

ItemInfo 保存了桌面上每个项目的信息，包括图标的位置等。

桌面一般包含几种类型的Item，不同的item，对应着不同的子类，ItemInfo是它们的父类。

| Item类别 | 含义                     | 对应子类              |
| :------: | ------------------------ | --------------------- |
|  wiget   | 小工具，如是时钟小工具等 | LauncherAppWidgetInfo |
| 快捷方式 | 快速启动应用的图标       | ShortcutInfo          |
|  文件夹  | 将多个快捷方式放在一起   | FolderInfo            |

重要属性

- container：int型。图标放的位置，是在workspace、Hotset还是文件夹中。

- cellX，cellY：图标在哪个位置。

- spanX，spanY：图标的高和宽。

- titie：标题，小工具无。

- itemType表面是哪种类型的ItemInfo。
## LauncherModel

主要负责数据层的东西，继承BroadcastReceiver。内部类LoaderTask用来初始化桌面。






# 布局过程

 


最外层是LauncherRootView  

继承关系

FrameLayout

​	->InsettableFrameLayout

​		->LauncherRootView

布局内部为DragLayer



## 第一次运行配置好的图标



根据屏幕大小来进行适配选择用哪个布局。

res/xml/device_profiles.xml

布局文件

res/xml/default_workspace_4x4.xml

```

```



```
launcher:className：应用的类名。
launcher:packageName：该应用的包名。
launcher:screen：当前屏幕位置，0-4屏，共5屏。
launcher:x：图标X位置，左上角第一个为0，向右递增0-4共5个。
launcher:y：图标Y位置，左上角第一个为0，向下递增，0-2共3个。
launcher:spanX：在X方向上所占格数。
launcher:spanY：在Y方向上所占格数。
```



# 启动流程

Init进程->Zygote孵化器->SystemServer->ActivityManagerService(向packageManagerService查询CATEGORY_HOME的Activity)

Launcher继承Activity，从LauncherAppstate获取手机配置，从LauncherModel对数据绑定，再把数据回调给Launcher。

Oncreate的主要步骤:

初始化对象

加载布局

注册事件监听

数据加载

