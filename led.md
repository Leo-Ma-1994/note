/system/lib/hw中定义了硬件抽象层编译的动态库文件。

APP会通过控件调用framework层的libui库，libui库根据ID号选调hardware层的动态库"/system/lib/hw/gralloc.*.so",然后硬件抽象层再继续调用驱动层的接口/dev/fb0





通过HAL提供的hw_get_module方法来加载模块ID为HELLO_HARDWARE_MODULE_ID的HAL层模块，



已经有了硬件抽象模块，JNI方法提供Java访问硬件服务接口。

在frameworks/base/services/core/jni编写



内核空间中硬件驱动程序

/kernel/frivers/leds/中编译成之后，可以看到有该设备 dev/





通过设备文件来连接HAl层模块和Linux内核驱动程序模块。

HAL层(libhardware)添加接口支持访问硬件，来和驱动程序交互。

/hardware/libhardware/modules/antled/antled.c中



驱动中会创建驱动中会创建dev/hello设备节点和/sys/class/hello/hello/val 设备节点，没有实现proc/下的对应的设备节点。/sys/class/hello/hello/val 主要用于快速测试，而dev/hello则主要用于供上层应用调用。







# HAL规范要求

模块ID

HAL层根据MODULE_ID可以找到该HAL_stub

```c
#define  ANTLED_HARDWARE_MODULE_ID "antled"
```

模块结构体

```c
typedef struct antled_module {
    struct hw_module_t common;
} antled_module_t;
```



硬件接口结构体

```c
struct antled_device;
typedef struct antled_device
{
    struct hw_device_t common;
   /** open  antled
     * @return 0 in case of success, negative errno code else
     */
      int (*flush_led_status)(struct antled_device *antleddev, int *status, int ledNum);

    /** open  antled
     * @return 0 in case of success, negative errno code else
     */
     int (*flush_led_driver_ver)(struct antled_device *antleddev, const char *drversion);

    /** Turn on antled
     * @return 0 in case of success, negative errno code else
     */
    int (*flush_led_power)(struct antled_device *antleddev, int status, int ledNum);

    /** Turn off antled
     * @return 0 in case of success, negative errno code else
     */
    int (*antled_device_open)(const struct hw_module_t *module,const char *name, struct hw_device_t **device);

    /** close  antled
     * @return 0 in case of success, negative errno code else
     */
    int (*antled_device_close)(struct hw_device_t *device);
}antled_device_t;
```



其中函数的声明表示为该HAL对上提供的函数接口。



编译之后，可以保证模块总被硬件抽象层加载到。
ax2129/system/lib下

需要编写JNI方法和在Frameworks增加API接口，才可以让上层Application访问硬件。







# 自动加载驱动模块

在init.rc on boot中添加

chmod 0770 /vendor/lib/modules/leds-gpio.ko 

insmod /vendor/lib/modules/leds-gpio.ko







AIDL的目的是为了跨进程调用Service

# JNI层





编写接口文件aidl给应用程序使用。

定义硬件访问接口并实现硬件访问服务。

为Framework层添加硬件访问服务。

最终效果：

当系统启动时，自动加载LedService服务。

通过hw_module_t找到led_control_device_t

通过硬件模块ID来加载指定的硬件抽象层模块并打开硬件设备。



(运行时库)JNI层实现步骤：



- **step1：编写JNI本地方法**

/frameworks/base/services/core/jni中添加JNI文件，命名为com_android_server_LedService.cpp表示硬件服务方法

在/frameworks/base/services/core/Java/com/android/server

或者frameworks/base/services/java/com/android/server下的LedService.java

编写JNI本地方法

```c
#define LOG_TAG "LedService"
#include "jni.h"
#include <nativehelper/JNIHelp.h>
#include "android_runtime/AndroidRuntime.h"
#include <utils/misc.h>
#include <utils/Log.h>
#include <hardware/hardware.h>
#include <hardware/antled.h>
#include <stdio.h>

namespace android
{
    /*在硬件抽象层中定义的硬件访问结构体*/
    antled_device_t *antled_device = NULL;

    /** helper APIs */
    static inline int led_control_open(const struct hw_module_t *module,
                                       struct antled_device **device)
    {
        //2.根据ID找到对应的设备stub
        return module->methods->open(module,
                                     ANTLED_HARDWARE_MODULE_ID, (struct hw_device_t **)device);
    }

    static jboolean antled_init(JNIEnv *env, jclass clazz)
    {
        antled_module_t *module;
        ALOGI("jni init-----------------------.");
        //1.通过HAL的id找到对应的库
        if (hw_get_module(ANTLED_HARDWARE_MODULE_ID, (const hw_module_t **)&module) == 0)
        {
            //根据LED_HARDWARE_MODULE_ID找到hw_module_t，参考hal层的实现
            ALOGI("LedService JNI: LED Stub found.");
            //2.根据ID找到对应的设备stub
            if (led_control_open(&module->common, &antled_device) == 0)
            {
                //通过hw_module_t找到led_control_device_t
                ALOGI("LedService JNI: Got Stub operations.");
                return 0;
            }
        }
        ALOGE("LedService JNI: Get Stub operations failed.");
        return -1;
    }

    static void antled_set_status(JNIEnv *env, jobject clazz, jint status, jint number)
    {
        int num = number;
        ALOGI("antled JNI: select lighton is %d", num);

        if (!antled_device)
        {
            ALOGI("antled JNI: device ius not open");
            return;
        }
        antled_device->flush_led_power(antled_device, status, num);
    }
    /*JNI方法表*/
    static const JNINativeMethod method_table[] = {

        { "native_antled_init",      "()Z",  (void *)antled_init },//Framework层调用_init时促发
        {"native_antled_status", "(II)V", (void *)antled_set_status},
    };
    /*注册JNI方法*/
    //本地方法对应的java类,LedService
    int register_android_server_LedService(JNIEnv *env)
    {
        return jniRegisterNativeMethods(env, "com/android/server/LedService",
                                        method_table, NELEM(method_table));
    }

}; // namespace android

```





- **step2：动态注册**

  在同目录中onload.cpp中注册本地方法，这样就完成了jni的动态注册。

在Android系统初始化时，就会自动加载该JNI方法调用表。

```c++
int register_android_server_LedService(JNIEnv *env);
register_android_server_LedService(env);
```



- **step3：将JNI添加进system.img**

  在同目录下的Android.bp中添加写好的JNI文件，确保system.img镜像文件包含我们刚才编写的JNI方法了，

```
        "com_android_server_LedService.cpp",
```



- **step4：重新编译system.img**

  mmm frameworks/base/services/

  make snod  重新打包system.img

## 硬件服务实现步骤：

通常硬件服务是运行在独立进程中为各种应用程序提供服务的。

本质通过binder方式进行通信

总体步骤

1. 创建binder接口，可通过aidl方式
2. 创建接口的实现类，即为服务类
3. 添加服务到系统
4. 创建service管理类manage
5. 注册服务对应的manger，以供调用者使用
6. 配置修改SELinux权限。（在Enforcing强制模式下，无法使用）
7. 编译，需先make update-api

- **step1：定义aidl**

在frameworks/base/core/java/android/os中，

定义一个aidl的通信接口

```java
package android.os;

interface ILedService {
    void set_antled_status(int status, int number);
}
```



- **step2：生成stub**

在frameworks/base的Android.bp中加入

```
        "core/java/android/os/ILedService.aidl",
```

编译mmm frameworks/base，根据IHelloService.aidl生成相应的IHelloService.Stub接口。



- **step3：实现服务类**

frameworks/base/services/core/java/com/android/server/LedService.java

新增实现LedService服务类，主要是通过JNI方法来提供LED灯的硬件服务。

```java
package com.android.server;
import android.content.Context;
import android.os.ILedService;
import android.util.Slog;
public class LedService extends ILedService.Stub {
	private static final String TAG = "LedService";
	LedService() {
		Slog.i("LedService", "StartLedService");
		native_antled_init();
	}
	public void set_antled_status(int status, int number) {
		native_antled_status( status,  number);
	}	

	private static native boolean native_antled_init();
    private static native void native_antled_status(int status, int number);
}

```



- **step4: 添加Context常量**

在/frameworks/base/core/java/android/content/Context.java中加入

```java
public static final String LED_SERVICE="led";
```





- **step5：将service加入SystemServer启动流程**

frameworks/base/services/java/com/android/server/SystemServer.java

在SystemServer.java中增加加载服务的代码

```java
            traceBeginAndSlog("StartLedService");
            try {
                Slog.i("maxiang", "StartLedService");
                ServiceManager.addService(Context.LED_SERVICE, new LedService());

            } catch (Throwable e) {
                reportWtf("starting Led Service", e);
            }
            traceEnd();
```



这样重写生成的system.img就在Framework层中包含了自定义的硬件服务AntledService了，并且在系统启动时，自动加载服务。这样上层的应用就可以通过Java接口来访问硬件。



- **step5：创建Service的管理类，创建Manager**

通过该管理类，将Service封装起来，方便调用者使用。

在frameworks/base/core/java/android/app/LEDManager.java

```java
package android.app;


import android.content.Context;
import android.content.Intent;

import android.os.ILedService;
import android.util.Log;

public class LEDManager {
    ILedService mService;
    public LEDManager(Context ctx,ILedService service){
        mService=service;
    }
    
    
    //接口提供
    public void ledctrl(int led_id,int blink  int R, int G, int B){
        //led_id:0~3
        //R,G,B:0~1
    }


    
    public void requestLedRed(){
        try{
            mService.set_antled_status(255,1);
        }catch(Exception e){
            Log.e("LEDManager",e.toString());
            e.printStackTrace();
        }
	}

	public void requestLedYellow(){
        try{
            mService.set_antled_status(255,2);
        }catch(Exception e){
            Log.e("LEDManager",e.toString());
            e.printStackTrace();
        }
	}

	public void requestLedBlue(){
        try{
            mService.set_antled_status(255,3);
        }catch(Exception e){
            Log.e("LEDManager",e.toString());
            e.printStackTrace();
        }
	}
	
}
```



- **step6: 注册管理类**

通过getSystemService来获取管理类对象。

/frameworks/base/core/java/android/app/SystemServiceRegistry.java

在静态代码块中加入

```java
         registerService(Context.LED_SERVICE, LEDManager.class,
                new CachedServiceFetcher<LEDManager>() {
                    @Override
                    public LEDManager createService(ContextImpl ctx) throws ServiceNotFoundException{
                        IBinder b = ServiceManager.getServiceOrThrow(Context.LED_SERVICE);
                        ILedService service = ILedService.Stub.asInterface(b);
                        return new LEDManager(ctx, service);
                    
                    }
                });
```



编译代码

make update-api

修改frameworks层，定义了新的共有变量、常量或方法之后，需要make update-api。

make -j8





make api-stubs-docs-update-current-api







> hw_get_module(CUSTOMLED_HARDWARE_MODULE_ID, (hw_module_t const**)&module);
>
>​	err = customled_open(&module->common, &device);
>
>static inline int customled_open(const struct hw_module_t* module,
>
>​        struct customled_device** device) {
>
>
>
>  printf("customled_open E\n");
>
>  return module->methods->open(module,
>
>  CUSTOMLED_HARDWARE_MODULE_ID, TO_HW_DEVICE_T_OPEN(device));
>
>}





手动载入模块 insmod sdcard/filename.ko







# 回顾

系统自带的Service添加及使用流程

## 应用层调用

通过getSystemService调用Manager

frameworks/base/core/java/android/app/*Manager.java

## Manager

提供给应用程序调用的接口,同Service交互,实现功能

## Service

开机时在SystemServer启动后台服务.

后台服务依赖的JNI实现



## HAL

依赖头文件





# 新建一个Service

　　1) 接口：接口供应用调用

　　frameworks/base/core/java/android/app/ContextImpl.java 加服务名与Manager对应

　　frameworks/base/core/java/android/content/Context.java 加服务名定义

　　2) Manager：提供服务对应的调用接口

　　frameworks/base/core/java/android/app/StartXXXXManager.java 实现调用接口

　　frameworks/base/core/java/android/app/IXXXXManager.aidl 定义调用接口

　　frameworks/base/Android.mk 加入aidl的编译

　　3) service：提供后台服务支持

　　frameworks/base/services/java/com/android/server/XXXXService.java 服务实现

　　frameworks/base/services/java/com/android/server/SystemServer.java 启动服务





# 使用系统服务

有很多系统服务管理,如振东管理服务Vibrator,电池管理服务BatteryManager.

Manager提供对系统层的控制接口.

Manager是对IService的的封装,实际操作都是在IService中完成的.

apk通过系统提供的Manager接口来访问Service提供的支持.

应用层只需要了解这些接口的使用方式,就可以获进行系统的控制.



![img](https://notepic-1302850888.cos.ap-nanjing.myqcloud.com/img/20171220111218423)

```Java
Vibrator mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
mVibrator.vibrate(500);
```

getSyetemService()定义在Context中, 实现是在contextImpl中,





context抽象类,contextImpl实现类,contextWrapper实现类,contextImpl与contextWrapper的关系是装饰者模式.







## 硬件服务添加权限

# Bug解决

## 在framework层增加服务后编译报错

error 3: Added class IHelloService to package android.os

error 3: Added class IHelloService.Stub to package android.os

解决：编译之前先执行

## 添加系统服务时出现Selinux问题

Android5.0开始的SELInux (Security-Enhanced Linux)是一套强制性的安全审查机制(强制访问控制)。限制了root权限。

SELinux模式：

- Enforce模式：打印异常log，拒绝请求。没有权限的请求被拒绝后 执行就会有问题。
- Permissive模式：打印异常log，不拒绝请求。请求没被拒绝，执行不会出现问题。

### 粗暴方法

将selinux检查关闭

修改“system/core/init/selinux.cpp”，加IsEnforcing判断直接返回false。



type AntledService_service, app_api_service,  system_server_service, service_manager_type;

AntledService                             u:object_r:AntledService_service:s0

##  获取当前SELInux

SELinux : avc: 

denied { add } for service=AntledService pid=757 uid=1000  缺少add的权限

scontext=u:r:system_server:s0   System__server缺少权限

tcontext=u:object_r:default_android_service:s0   对default_android_service缺少权限

tclass=service_manager  什么类型的文件。

permissive=0





denied  { add } for service=LedService pid=774 uid=1000 scontext=u:r:system_server:s0 tcontext=u:object_r:default_android_service:s0 tclass=service_manager permissive=0



E SELinux : avc:  denied  { find } for service=LedService pid=3117 uid=10073 scontext=u:r:untrusted_app:s0:c73,c256,c512,c768 tcontext=u:object_r:LedService_service:s0 tclass=service_manager permissive=0



# 正统方法

## sepolicy部分添加

定义SELinux

分别在两个文件中进行配置

/system/sepolicy/public/service.te

type LedService_service, system_api_service, system_server_service, service_manager_type;





/system/sepolicy/private/service_cntexts.te

LedService                                u:object_r:LedService_service:s0



(typeattributeset hal_vr_hwservice_26_0 (hal_vr_hwservice))

step1：\system\sepolicy\public\service.te中定义服务名称和属性

文件 system/sepolicy/prebuilts/api/28.0/public/service.te 和 system/sepolicy/public/service.te 不同

更改

在system/sepolicy/prebuilts/api/28.0/public/service.te添加

type LedService_service, system_api_service, system_server_service, service_manager_type;

权限控制在boot.img





编译之后出现out of space错误

原因系统分区发生变化



- step1

 prebuilts/api/26.0/nonplat_sepolicy.cil 中加入

(typeattribute led_service_26_0)
(roletype object_r led_service_26_0)



 prebuilts/api/27.0/nonplat_sepolicy.cil 中加入

(typeattribute led_service_27_0)
(roletype object_r led_service_27_0)



- step2

prebuilts/api/28.0/private/compat/26.0/26.0.cil

private/compat/26.0/26.0.cil和添加

(typeattributeset led_service_26_0 (led_service))



prebuilts/api/28.0/private/compat/27.0/27.0.cil

和private/compat/27.0/27.0.cil添加

(typeattributeset led_service_27_0 (led_service))





- step 3

prebuilts/api/28.0/private/service_contexts

和private/service_contexts添加

led                                       u:object_r:led_service:s0

prebuilts/api/28.0/public/service.te

和public/service.te添加

type led_service, system_api_service, system_server_service, service_manager_type;





需要先make 

此时直接编译会报out of space错误，因为系统分区改变的问题

此时先make clean

然后在make整编



参考文章

1. [Android 9 中systemServer中的服务配置selinux权限](https://blog.csdn.net/su749520/article/details/82800050?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-4.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-4.channel_param)

## 临时方法

setenforce 0 (临时禁用掉SELinux)

getenforce 得到结果为Permissive



![img](https://notepic-1302850888.cos.ap-nanjing.myqcloud.com/img/0_1291704567gJjG.gif)

jni层注册方法，使得Framework层可以使用这些方法



当驱动层提供了HAL层代码时，需要实现JNI接口，为应用上层提供访问下层硬件服务的接口

提供一个硬件服务类，

在Framework层加载jni库时调用，

Service类中声明jni可以提供的方法。

这样可以确保应用层直接调用系统服务。



在安卓系统初始化时，可以自动加载该JNI的方法调用表、



# 文件节点

传统设备文件/dev/

proc系统文件/proc/

devfs系统属性文件/sys/class/leds









# Android添加JNI到源码中



HAL主要存储在libhardware(新架构，调整为HAL stub模式)中以及libhardware_legcy(旧架构，采取链接库模块观念进行)



selinux



setenforce 0

getenforce

ps -A |grep system_server

kill 733



通过JNI调用HAL层接口

在Framework层提供Java接口的硬件服务，添加硬件访问服务。







//TODO

HIDL





10-28 10:36:07.284   331   331 E SELinux : avc:  denied  { find } for service=led pid=2734 uid=10074 scontext=u:r:untrusted_app:s0:c74,c256,c512,c768 tcontext=u:object_r:led_service:s0 tclass=service_manager permissive=0





audit(0.0:270): avc: denied { write } for name="brightness" dev="sysfs" ino=24189 scontext=u:r:system_server:s0 tcontext=u:object_r:sysfs:s0 tclass=file permissive=0











\#define LED1_DEVICE_BLUE    "/sys/class/leds/info1_blue/brightness"

\#define LED1_DEVICE_RED "/sys/class/leds/info1_red/brightness"

\#define LED1_DEVICE_GREEN   "/sys/class/leds/info1_green/brightness"

\#define LED2_DEVICE_BLUE    "/sys/class/leds/info2_blue/brightness"

\#define LED2_DEVICE_RED "/sys/class/leds/info2_red/brightness"

\#define LED2_DEVICE_GREEN   "/sys/class/leds/info2_green/brightness"

\#define LED3_DEVICE_BLUE    "/sys/class/leds/info3_blue/brightness"

\#define LED3_DEVICE_RED "/sys/class/leds/info3_red/brightness"

\#define LED3_DEVICE_GREEN   "/sys/class/leds/info3_green/brightness"

\#define LED4_DEVICE_BLUE    "/sys/class/leds/info4_blue/brightness"

\#define LED4_DEVICE_RED "/sys/class/leds/info4_red/brightness"

\#define LED4_DEVICE_GREEN   "/sys/class/leds/info4_green/brightness"