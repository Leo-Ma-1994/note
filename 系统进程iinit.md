[TOC]



# Android系统启动顺序

![](https://wizpic-1302850888.cos.ap-nanjing.myqcloud.com/%E7%AC%94%E8%AE%B0%E6%8F%92%E5%9B%BE/Android%E7%B3%BB%E7%BB%9F/%E5%AE%89%E5%8D%93%E7%B3%BB%E7%BB%9F%E5%90%AF%E5%8A%A8.png)
1. 启动电源以及系统启动

   按下电源，引导芯片代码从ROM开始执行，加载引导程序bootloader到RAM。

2. 引导程序
   bootloader
   
3. 内核
  
   当linux内核启动完成后，会首先在系统文件中寻找“init”文件。
   /system/core/init/init.cpp中的main()方法

4. init进程
  

Androids设备的启动包括三个阶段，bootloader、linux Keernel和Android系统服务。

# Bootloader的作用(简单了解)
系统启动加载器
是在操作系统启动前，运行的一段小程序。该程序的目的是为了初始化硬件设备，以便为调用操作系统做好准备。

init位置
源码中system/core/init

init.rc位置
system/core/rootdir/Init.rc

#
init第一阶段
init第二阶段
init.rc文件解析

# 第一阶段
## 挂载文件系统并创建目录
## 创建设备节点
## 初始化日志输出系统
## 启用SELinux安全策略

# 第二阶段
## 创建进程会话密钥并初始化属性系统
## 进行SELinux第二阶段并恢复一些文件安全上下文
## 新建epoll并初始化子进程终止信号处理函数
## 设置其他系统属性并开启系统属性服务

# Android系统分区
|系统分区|功能|
|--|--|
|/boot||
|/system||

# init的作用
1. 挂载目录
2. 运行init.rc脚本
3. 创建Zygote孵化器和属性服务等。
由内核启动的第一个用户级进程，pid为1
是所有用户进程的爸爸。

# Android初始化语言init.rc
在Android 7.0以前，init进程只解析跟目录下的init.rc文件，但是rc文件越来越臃肿，一些业务被拆分到了/system.etc/init,/vendor/etv/init和/odm/etc/init三个目录中。

完整的脚本通常有4种类型组成，

## service

Service其实是可执行程序，在一些约束下会被init重启或运行。

通常格式为

```
service <name><pathname> [<argument>]
	<option>
	<option>
```




# 杂项
 etc目录一般都是重要的配置文件

![2020-09-15 20-23-55屏幕截图](../2020-09-15%2020-23-55%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png)

