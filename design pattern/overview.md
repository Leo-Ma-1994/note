# 概念

## 松耦合

两个对象之间松耦合，依然可以交互，但是彼此可以不清楚对方的细节，甚至可以不知道对方的类。

经典模式：观察者模式





- 观察者模式和发布订阅者模式之间的区别

![img](https://notepic-1302850888.cos.ap-nanjing.myqcloud.com/img/v2-87ed5aaf77f78ac6ec834ddba089e577_720w.jpg)

观察者模式：数据源直接通知订阅者发生改变

发布订阅模式：数据源告诉第三方发生了改变，第三方再通知订阅者发生了改变。

关注点不一样：观察者模式关注的是数据源，而发布订阅模式关注的是事件消息(第三方).