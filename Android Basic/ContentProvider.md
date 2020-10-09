> 混沌迈向有序
>
> <p style="text-align:right">——羊习习的马</p>
***
[TOC]


# 概述

通常情况下，Android中应用的内部数据是对外隔离的，为了使不同的应用程序共享交换数据，提供统一的接口。

跨进程通信，底层用到的是Binder跨进程通信的原理

如果开发的APP不需要与其他APP有数据交互，基本用不到。

比如系统中的电话簿程序，其数据库中保存了联系人信息，第三方APP往往需要调用它的信息。

ContentProvider可以选择只对哪一部分内容进行共享，从而保证隐私数据不会被泄露。





Contentprovider以uri的形式对外提供数据，允许其他应用访问或修改数据。

其他应用程序使用ContentReslover



# 作用

<img src="https://notepic-1302850888.cos.ap-nanjing.myqcloud.com/img/aHR0cDovL3VwbG9hZC1pbWFnZXMuamlhbnNodS5pby91cGxvYWRfaW1hZ2VzLzk0NDM2NS0zYzQzMzljNWYxZDRhMGZkLnBuZz9pbWFnZU1vZ3IyL2F1dG8tb3JpZW50L3N0cmlwJTdDaW1hZ2VWaWV3Mi8yL3cvMTI0MA" alt="示意图"  />

两种形式：

- 使用现有的内容体工程来读取和操作程序中的数据
- 创建自己的内容提供者给该程序的数据提供外部访问的接口。

# 使用方法

需要在AndroidManifest.xml配置

外界进程通过URI找到对应的ContentProvider和其中的数据，再进行数据操作

# uri 统一资源标识符

是ContentProvider和ContentReslover进行数据交换的标识。外界进程通过URI找到对应的ContentProvider和其中的数据项，再进行操作。

## uri格式

协议部分+域名部分+资源部分

**协议部分**：固定格式 content://

**域名部分**：是为了对不同的应用程序进行区分，通常采用程序包名

**资源部分**：path 对程序中不同资源(不同的数据表)做区分。

| URI格式 | 协议部分 | 授权信息/域名部分 | 资源部分 | 记录 |
| ------- | -------- | ----------------- | -------- | ---- |
|URI格式|Schema|Authority|Path|ID|
|示例|content://|com.leoma.provider|words|1|

> 表示调用方希望访问的是com.leoma.provider这个应用的words表中id为1的数据

##ContentUris

用来操作Uri字符串的工具类，核心方法

withAppendedId(uri, id)为路径加上ID部分

parseId(uri)从指定的uri中解析出ID值

\```Java

Uri uri = Uri.parse("content://cn.scu.myprovider/user") 

Uri resultUri = ContentUris.withAppendedId(uri, 7); 

// 最终生成后的Uri为：content://cn.scu.myprovider/user/7

Uri uri = Uri.parse("content://cn.scu.myprovider/user/7") 

long personid = ContentUris.parseId(uri); 

//结果为7

\```

##UriMatcher



# 创建自己的Provider

## 步骤

- 创建自己的数据列表

创建SQLiteHelper的子类DBOpenHelper，在onCreate方法中执行设计好的SQL语句(通常是数据表)。

创建的数据库位于/data/data/包名/databases/中

- 自定义ContentProvider实现相关的抽象方法
- 在AndroidManifest中声明provider以及定义相关访问权限
- 通过ContentResolver根据URI进行增删改查

自定义的类需要继承ContentProvider，重写6个方法

在清单文件中注册

```xml
<provider
    android:authorities="com.keven.jianshu.Part1dMyProvider"
    android:name=".part1.Part1dMyProvider"
    android:exported="true"/>
```

name:ContentProvider的全称类名

authorities：



# 使用系统提供的Provider访问数据



#ContentProvider

子类需要实现CRUD的相关方法，这些方法不是给自身调用的，而是供其他应用调用的。其他APP通过ContentReslover调用CRUD方法时，实际上就是调用uri对应的ContentProvider的CRUD方法。

\##单实例模式

\##生命周期

为了方便其他应用访问，通常会把ContentProvider的Uri、数据列等信息以常量的形式公布出来。





#ContentObserver

#ContentReslover

如果要访问内容提供器中共享的数据，一定要借助ObsverReslover

#示例

##读取系统联系人

Android系统用于管理联系人的ContentProvider的Uri



联系人表的URI —— content://com.android.contacts/contacts ，对应类静态常量为ContactsContract.Contacts.CONTENT_URI

联系人电话URI —— content://com.android.contacts/data/phones ，对应静态常量为ContactsContract.CommonDataKinds.Phone.CONTENT_URI

联系人邮箱URI —— content://com.android.contacts/data/emails ，对应静态常量为ContactsContract.CommonDataKinds.Email.CONTENT_URI