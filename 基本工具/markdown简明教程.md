这是一篇markdown简单使用的笔记。

[toc]

#标题



在行首插入 1 到 6个#，分别表示标题 1 到标题 6

示例：\##标题




#有序列表

```

1. 有序列表1

2. 有序列表2

3. 有序列表3

```

注意：数字和英文句点,句点后需要 有一个空格。

1. 有序列表1

1. 有序列表2

1. 有序列表3

#无序列表

```

在行首增加*或- 与文字之间要有空格

* 无序列表1

* 无序列表2

* 无序列表3

```

* 无序列表1

* 无序列表2

* 无序列表3

#目录

```

在文件头添加[toc]

```

#代码

```C++

int i = 0; i = 1; 

for (int i = 0; i < 100; i++)

{

       printf("hello markdown!\n");

}

```

```

在```后加上具体语言,如```java

```

#插入图片

![](http://cdn.wiz.cn/wp-content/uploads/2015/06/wiz_logo.png)

多种用法:目前采用基本的![]后跟图片地址。

#插入链接

\[描述](链接地址) 

例如：\[百度](http://www.baidu.com) 

[百度](http://www.baidu.com)

#粗体、斜体、删除线、引用

粗体：在文字前后添加 ** (注意符号与文字间不要有空格） 

斜体：在文字前后添加 * 

粗斜体：在文字前后添加***

删除线：在文字前后添加 ~~

引用：在文字前添加>

* **粗体**

* *斜体*

***粗斜体***

* ~~删除线~~

* >这是一条引用

#表格

```

| 为知笔记|更新 | 版本 |

|---|----|---|

| WizNote | Markdown| Latest |

```

效果

| 为知笔记|更新 | 版本 |

|---|---|---|

| WizNote | Markdown| Latest |

#任务列表

```

- [x] 已完成任务

- [ ] 未完成任务

```

- [x] 已完成任务

- [ ] 未完成任务

#分割线

\***

***

#字体样式

利用html格式实现一些字体样式

```

<span  style="color: #5bdaed; ">颜色1</span>

<span  style="color: #AE87FA; ">颜色2</span> 

<span  style="font-size:1.3em;">字体大小</span>

<font color = 4285f4 size = 12>*g*</font><font color = ea4335>*o*</font><font color = fbbc05>*o*</font><font color = 4285f4>*g*</font><font color = 34a853>*l*</font><font color = ea4335>*e*</font>

<p style="text-align:center">内容居中</p>

```

<span  style="color: #5bdaed; ">颜色1</span>

<span  style="color: #AE87FA; ">颜色2</span> 

<span  style="font-size:1.3em;">字体大小</span>

<font color = 4285f4 size = 12>*g*</font><font color = ea4335 size = 12>*o*</font><font color = fbbc05 size = 12>*o*</font><font color = 4285f4 size = 12>*g*</font><font color = 34a853 size = 12>*l*</font><font color = ea4335 size = 12>*e*</font>

<p style="text-align:center">内容居中</p>

