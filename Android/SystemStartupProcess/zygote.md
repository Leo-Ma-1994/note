[toc]

# 整体调用

Boot Rom->bootloader-> kernel->init->app_process->zygote(Java世界开启)

init进程在解析.rc文件时，会启动一些service，这些service通常是daemon(守护进程)。

Zygote进程是在init进程启动过程中启动的。

# 守护进程的概念
指的是在系统初始化是启动，并一直运行在后台，知道系统关闭时终止。

zygote就是守护进程

# zygote的触发
## 定义
```
import /init.${ro.zygote}.rc
```
> ro.zygote有四个值，zygote32,zygote64,zygote32_64,zygote64_32。也就是有四个rc文件。原因是Android5.0之后，开始支持64位程序，为了兼容才这样定义的。

|名称|含义|
|--|--|
|init.zygote32.rc|对应的执行程序是app_process(纯32bit模式)|
|init.zygote64.rc|对应的执行程序是app_process(纯64bit模式)|
|init.zygote32_64.rc|对应的执行程序是app_process32(主模式)、app_process64|
|init.zygote64_32.rc|对应的执行程序是app_process64(主模式)、app_process32|

```c
// 服务进程名称是zygote,运行的二进制文件在/system/bin
// 启动参数是 -Xzygote /system/bin --zygote --start-system-server
service zygote /system/bin/app_process32 -Xzygote /system/bin --zygote --start-system-server
    class main
    priority -20
    user root
    group root readproc
    socket zygote_secondary stream 660 root system
    onrestart restart zygote
    writepid /dev/cpuset/foreground/tasks//创建子进程时，写入pid

```

## 调用
zygote的触发是在init进程的最后

zygote对应的可执行程序其实就是app_process。

app_process对应的主函数是platform/frameworks/base/cmds/app_process/app_main.cpp。

该函数主要工作是做**参数解析**，有两种启动模式：

- zygote模式

  初始化zygote进程

- application模式

  启动普通应用程序

app_main最后都会调用runtime.start函数，启动虚拟机。



## 创建并启动虚拟机

略

AndroidTime.start

### 初始化JNI

### startVm

虚拟机创建

### startReg

进程NI方法注册



## ZygoteInit

通过JNI反射调用Zygote的main函数，至此Java的世界被开启了。

### 注册socket

registerZygoteSocket

注册一个Server端的Socket，该Socket用来等待AMS来请求Zygote创建新的应用程序。

### 预加载各类资源

preload

### 启动System Server

### runSelectLoop

等待客户端的请求。





![img](https://user-gold-cdn.xitu.io/2020/1/3/16f699bc445a127c?imageslim)



# 作用

1. 创建Java虚拟机
2. 加载系统资源
3. 创建system_server进程
4. 不断接受其他进程的信号，随时创建子进程(app继承)



# gcc编译C

一步到位直接生成可执行程序

gcc hello.c -o hello



# 参考

1. [Android系统启动流程之zygote进程](https://www.jianshu.com/p/42adb1eda320)

2. [Android系统启动-zygote篇](http://gityuan.com/2016/02/13/android-zygote/)

  
  
  
  
   

