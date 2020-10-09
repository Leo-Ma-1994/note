[toc]



# fork

功能是克隆进程，用于自己独立的地址空间。

fork在执行之后，会创建出一个新的进程，这个新的进程内部的数据是原进程所有数据的一份拷贝。因此fork就相当于把某个进程的全部资源复制了一遍。

fork出的子进程是从fork调用之后开始的。

![img](https://notepic-1302850888.cos.ap-nanjing.myqcloud.com/img/v2-bc182cdb16e164bb90ccae9fb0bee257_b.jpg)

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