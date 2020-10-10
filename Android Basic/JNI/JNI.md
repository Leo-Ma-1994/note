[toc]
# 是什么
Java Native Interface

是Java平台的特性，只不过在安卓中大量使用。快速调用C/C++的动态库。除了调用C/C++之外别无它用。用来与其他语言通信。
就是Java调用C/C++函数的接口。
相互调用

Java通过JNI调用本地方法，而本地方法是以库文件的形式存放的(在Win平台上是以DLL文件形式，在Unix机器上是SO文件形式)，通过这种形式，使得Java可以调用系统级的各种接口方法。

通常情况下，当需要节省程序的运行时间时，会选择用C语言进行开发，用Java调用C语言实现一些功能。或者代码的核心算法、密钥等，也会保存在C语言的代码中，以提高代码的安全性。

![img](https://notepic-1302850888.cos.ap-nanjing.myqcloud.com/img/5713484-62edbafafd3f3b2d.png)



# 为什么需要JNI

高级语言需要本机接口来访问低级系统资源和VM服务。由于安全性，可移植性和实现原因，他们不能直接访问低级资源

# 实现步骤
简要流程

![5713484-87fe79f0796a77a3](https://notepic-1302850888.cos.ap-nanjing.myqcloud.com/img/5713484-87fe79f0796a77a3.webp)

## 在类中声明所调用的库名称。




学习jni的时候，javac Helloworld.java 生成 Helloworld.class文件以后，调用javah Helloworld提示找不到类文件

由于javah以后生成的.h文件需要包名+类名，所以必须在包括全包名的目录下执行javah命令，也就是项目\src\在或者项目\bin\classe\下执行 javah 包名.类名的命令，

比如：在src下或者classe文件夹下执行

javah com.example.helloworld.HelloWorld 
最后会在执行命令的路径下面生成.h文件。

 # 安卓使用JNI的步骤





## JNI的使用步骤

第1步：在Java中先声明一个native方法
第2步：编译Java源文件javac得到.class文件
第3步：通过javah -jni命令导出JNI的.h头文件
第4步：使用Java需要交互的本地代码，实现在Java中声明的Native方法（如果Java需要与C++交互，那么就用C++实现Java的Native方法。）
第5步：将本地代码编译成动态库(Windows系统下是.dll文件，如果是Linux系统下是.so文件，如果是Mac系统下是.jnilib)
第6步：通过Java命令执行Java程序，最终实现Java调用本地代码。
.h文件的函数声明，固定格式：JNIEXPORT；返回类型： JNI调用 JNICALL； Java_完整类名_方法名:.


对于Java层来说，只需要加载对应的JNI库，接着声明native方法就可以。

JNI方法注册分为静态注册和动态注册
静态注册多用于NDK开发，动态注册多用于Framework开发。

jni.h和jni_md.h
里面存储了大量的函数和对象，里面有个方法是通过native接口名，获取C函数。
javah的作用是什么
用来在Java做本地调用时，生成C语言的头文件。(不怕麻烦的话其实可以自己写.h文件)
其中最重要的是C函数接口
```C
JNIEXPORT void JNICALL Java_HelloWorld_displayHelloWorld
  (JNIEnv *, jobject);
```
JNIEXPORT:所有本地语言实现JNI接口方法前都有这个关键词
void：方法返回类型
JNICALL：JNI调用的意思
函数名：Java+包名+类名+方法名
JNIENV：指针
jobject:native方法的调用者

有了.h文件，根据.h的接口实现真正的c文件
然后再将.c文件编译成liunx的动态库文件so即可。

Java和JNI基本类型和引用类型的差别
基本类型没有差别，可以直接映射。
引用类型：C++不能直接访问Java的引用类型。
操作数组、方法等有特殊方法。
参考 [JNI基本用法](https://www.jianshu.com/p/6cbdda111570)



JNI是Java和C++进行通信暴露得到接口。
NDK是专门为Android搞的一个快速链接C++的工具包。

如何将.c文件编译成.so文件
gcc -fpic -c fileName.c
gcc -shared -fpic -o ObjectName.so fileName.o

## JNI与Java类型

# ndkbuild和cmake

使用Jni有两种编译方案，传统的ndkbuild和IDE创建项目时使用的cmake。

## ndkbuild

- 新建Java类，声明native方法

  ```java
  package com.leo.demojni;
  
  public class JniDemo {
      public static native String getStringJNI();
  }
  ```

  

- 在需要使用native方法的地方使用

  ```java
         TextView tv =findViewById(R.id.text);
          tv.setText(JniDemo.getStringJNI());
  ```

  

- 编译工程，make project，将native工具类编译成class文件。

  ​	生成的class文件在/app/build/intermediates/javac//debug/classes下

- 生成.h文件，通过javah将.clases文件生成头文件(注意要加包名)

- 编写C/C++代码

  ​	引入生成的.h头文件，实现方法。并将.h文件和.c文件都放在/src/main/jni/下

- 编写Andorid.mk

  ```makefile
  LOCAL_PATH := $(call my-dir)
  include $(CLEAR_VARS)
  LOCAL_MODULE    := ndkdemotest-jni
  LOCAL_SRC_FILES := ndkdemotest.c
  include $(BUILD_SHARED_LIBRARY)
  ```

  LOCAL_MODULE 确定生成的模块名，生成时会自动加上合适的前缀和后缀。

  LOCAL_SRC_FILE:要编译进模块的C或者C++文件

  include $(BUILD_SHARED_LIBRARY) 指定要生成库的格式，是静态库还是动态库。

- 编译方式选择

  使用gradle进行ndk编译，app module设置编译路径

  ```
    externalNativeBuild {
              ndkBuild {
                  path 'src/main/jni/Android.mk'
              }
          }
  ```

- 在native方法的申明引用so库

  ```java
  package com.leo.demojni;
  
  public class JniDemo {
      static {
          System.loadLibrary("getString-jni");
  
      }
      public static native String getStringJNI();
  }
  ```

  

- 运行

## cmake

- [ ] 待整理

# 静态注册与动态注册

## 静态注册

根据函数名建立Java方法与JNI函数一一对应的关系。及上文中ndkbuild中的实现方式。

由Java得到本地方法的声明，然后再通过JNI实现申明该方法。

缺点:

- 编写麻烦，JNI方法名字需要遵照规则且名称长
- 编程过程贼麻烦
- 每次新增或者修改接口，需要重新生成.h文件
- 运行效率低，初次调用native函数，需要根据函数名在JNI层中搜索对应的本地函数。

## 动态注册

利用 RegisterNatives 方法来注册 java 中的native方法与C++ 函数的一一对应关系。

当系统中调用System.loadLibrary函数是，该函数会找到对应的库，然后会试图查询JNI_onLoad函数，如果有则可以与JNIEnv的registerNatives函数结合起来，实现动态的函数替换。

其实是通过反射实现的，通过JNI_onLoad方法拿到虚拟机对象，再通过反射拿到native方法的类对象。

通过JNI重载JNI_OnLoad()实现本地方法，然后直接在Java中调用本地方法。

### 函数映射表

```cpp
static JNINativeMethod method_table[] = {
        {"test1", "(I)I", (void *)n_test1},
        {"test2", "(Ljava/lang/String;)Ljava/lang/String;", (void *)n_test2}
};
```

Java中的方法名、描述Java方法的参数和返回值(签名)、Java接口映射的Native函数指针。

签名的目的是为了函数的重载，()中方法的参数类型，()后面表示返回值的类型。有相应的描述符。



### 实现流程

- 通过签名信息将Java方法和C方法一一对应起来

- 新建Java类，声明要引用的so库

  ```java
  package com.leo.demojni;
  
  public class JniDemo {
      static {
          System.loadLibrary("jni-dynamic");
      }
      public static native String getHello();
  }
  
  ```

  

- 重写注册入口函数和函数映射表

  ```c++
  //#include <stdlib.h>
  //#include <stdio.h>
  //#include <string.h>
  #include "jni.h"
  //需要调用Native方法的Java类
  #define JNI_REG_CLASS "com/leo/demojni/JniDemo" // path of Java file
  //native方法实现
  JNIEXPORT jstring JNICALL get_hello(JNIEnv *env, jclass clazz) {
      return env->NewStringUTF("hello from jni");
  }
  
  //
  static JNINativeMethod g_methods[] = {
          {"getHello", "()Ljava/lang/String;", (void *) get_hello},
  };
  
  //重写JNI_OnLoad()函数
  JNIEXPORT int JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
      JNIEnv *env;
      if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
          return JNI_ERR;
      }
  
      jclass javaClass = env->FindClass(JNI_REG_CLASS);
      if (javaClass == NULL) {
          return JNI_ERR;
      }
  
      int method_count = sizeof(g_methods) / sizeof(g_methods[0]);
      if (env->RegisterNatives(javaClass, g_methods, method_count) < 0) {
          return JNI_ERR;
      }
  
      return JNI_VERSION_1_6;
  }
  
  
  ```

  

- 编写Android.mk文件，同静态注册。

- 完成运行

# native反调用Java层代码



https://www.jianshu.com/p/b71aeb4ed13d










