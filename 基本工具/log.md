[toc]



##Log等级



| 日志级别 | 颜色值 |
| -------- | ------ |
| FATAL/S  | FF6B68 |
| Error    | FF0006 |
| Warning  | BBBB23 |
| Info     | 48BB31 |
| Debug    | 0070BB |
| Verbose  | BBBBBB |





adb logcat

输出log，格式为日志事件，进程号、线程号、Log级别、TAG、Log内容



adb logcat -v

设置输出LOG格式



adb logcat -c 

清除已有的日志并退出

adb logcat [TAG:Level] [TAG:Level]

输出TAG中级别大于等于Level的Log和其他Log。相当于只过滤掉TAG级别小于Level的Log

如果想只显示某一个TAG中的LOG，可以使用*:s，*是所有的意思。

如adb logcat LeoMa:d *:s

只显示TAG为LeoMa级别为debug及以上 的log





grep正则表达式

adb logcat | grep Name

只显示日志中包含Name关键字的Log，一般以Tag为过滤关键字



- isLoggable使用

```java
public static final boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG)；
```

通过设置property属性来打印log

在需要查看log时候,通过设置property即可查看Log.

```
adb shell setprop log.tag.Main D
```

