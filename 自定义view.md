Android设备启动经历的三个阶段,bootloader ,Linux Kernel和Android系统服务.

# bootloader

bootloader阶段要实现的功能,在系统上电或者复位后,将系统的软硬件环境带到一个合适的状态,为最终调用程序准备好正确的环境.



Android系统实际上是运行与Linux内核上的一系列服务进程,不算一个完整意义上的操作系统.

init启动后,代表系统已经顺利的启动了Linux内核.