整个Andoird framework进程启动流程：

init进程-> Zygote进程->SystemServer进程->各种应用进程



通常android系统中进程之间通信的方式是Binder，但是SystemService进程和Zygote进程之间是通过Socket的方式进行通信的。

