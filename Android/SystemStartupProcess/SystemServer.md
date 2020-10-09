[toc]

# 1. 简介

如果init.rc中为Zygote指定了启动参数--start-system-server,ZygoteInit就会调用startSystemServer进入SystemServer

SystemServer是由Zygote进程fork而来的.

Zygote与System的通信方式采用Socket。

提供了一大坨由Java写的系统服务，比如ActivityManagerService(AMS),WindowManagerService(WMS)等，这些系统服务都是以一个线程的方式存在SystemServer进程中的。

## 作用

用户管理整个Java framework层的各种系统服务。各种应用需要使用系统服务是也是通过SystemServer进行通信来执行响应的操作的

# 2. 服务分类

## Bootstrap Services

引导程序，系统服务中最核心的部分，且Services之间依赖性很强



## Core Services

## Other Services



# 3. 流程

## 3.1 



