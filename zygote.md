[toc]

# 整体调用

bootloader-> kernel->init->app_process->zygote(Java世界开启)

init进程在解析.rc文件时，会启动一些service，这些service通常是daemon(守护进程)。

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

该函数主要工作是做参数的解析，有两种启动模式：

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



# fork

功能是克隆进程，用于自己独立的地址空间。

fork在执行之后，会创建出一个新的进程，这个新的进程内部的数据是原进程所有数据的一份拷贝。因此fork就相当于把某个进程的全部资源复制了一遍。

fork出的子进程是从fork调用之后开始的。

![img](https://pic3.zhimg.com/v2-bc182cdb16e164bb90ccae9fb0bee257_b.jpg)

父进程P0执行了全部的代码，而子进程只执行了PartB的代码。



一次调用，有两次返回

1. 该进程为父进程时，返回子进程的pid
2. 该进程为子进程时，返回0
3. fork如果执行失败，返回-1

> fork给父进程返回子进程pid，给其拷贝出来的子进程返回0

例子

```c
#include "stdio.h"
#include "sys/types.h"
#include "unistd.h"
int main()
{
    pid_t pid1;
    pid1 = fork();
    printf("pid1:%d\n",pid1);
}
```

假定原有进程为P0，执行fork后新建了进程P1。执行结束后fork给父进程P0返回P1的id为子进程的pid，给P1返回0。

> 结果为
>
> pid1:7218
> pid1:0



```c
#include "stdio.h"
#include "sys/types.h"
#include "unistd.h"
int main()
{
    pid_t pid1;
    pid_t pid2;
    pid1 = fork();
    pid2 = fork();
    printf("pid1:%d, pid2:%d\n",pid1, pid2);
}
```

这里一个会有四个进程，分别是原有的父进程P0，由P0 fork出的子进程P1和P2，由P1 fork出的子进程P3(P1下的P2)。要注意子进程的父进程是谁，搞清楚父子关系。

- 对于P0，其pid1和pid2很简单，7420和7421
- 对于P1，pid1就是其自身，因此为0。P1进程中会fork出新的P2，因此pid2为7422
- 对于P2，pid1继承了父进程pid1的值，为7420。pid2为自身，因此为0。
- 对于P3，pid1继承了P1的pid1的值，因此为0，pid2为自身，因此为0。

>结果为
>
>pid1:7420, pid2:7421
>pid1:7420, pid2:0
>pid1:0, pid2:7422
>pid1:0, pid2:0



# gcc编译C

一步到位直接生成可执行程序

gcc hello.c -o hello



# 参考

1. [Android系统启动流程之zygote进程](https://www.jianshu.com/p/42adb1eda320)

2. [Android系统启动-zygote篇](http://gityuan.com/2016/02/13/android-zygote/)

  
  
  
  
   

