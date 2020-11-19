#  Launcher.db

初始化创建Launcher.db时候，数据库创建了favorites和workspaceScreens两张表

## favorites

![image-20200923100219440](https://notepic-1302850888.cos.ap-nanjing.myqcloud.com/img/image-20200923100219440.png)

字段含义：

title:workspace中应用shortcut的标题，hotseat中会隐藏掉。

intent:点击桌面图标时负责启动应用的intent

comtainer：当前shortcut所在容器的类型，在Launcher中有两种，
CONTENT_DESKTOP(-100)、CONTENT_HOTSEAT(-101)

screen:当container为-100时，为shortcut所在的屏序号；当container为-101时，为shortcut在hotseat中的位置。

## workspaceScreen

| _id  | screenRank | modified      |
| ---- | ---------- | ------------- |
| 0    | 0          | 1600396462852 |
| 1    | 1          | 1600396462852 |

screenRank表示创建的workspace处于第几屏



数据库的创建是在LauncherProvider中，通过ContentProvider的形式建立

初始化在LoaderTask中，