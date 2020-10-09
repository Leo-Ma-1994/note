展示View的三个步骤

Measure(测量)->Layout(摆放)->Draw(绘制)

viewGroup是一个用于存放其他view和viewGroup对象的布局容器。

FrameLayout就是viewGroup的子类，里面的子View是层叠摆放的。









## View的Layout

onLayout的目的是确定子View在父View中的位置，该方法是在父View中实现的。

```java
@Override
//四个参数分别代表了子View距离父View的左、上、右、下的距离
    protected abstract void onLayout(boolean changed,
            int l, int t, int r, int b);
```

