

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





**当前设备屏幕总宽度（单位为像素）/ 设计图总宽度（单位为 dp) = density*

1dp占当前设备的多少像素



**屏幕的总 px 宽度 / density = 屏幕的总 dp 宽度**



不管在布局文件下写的是什么单位，都可以通过DisplayMetrics中的值来转换成实际要绘制时的px。



设计稿宽度是360px，那么开发这边就会把目标dp值设为360dp，在不同的设备中，动态修改density值，从而保证(手机像素宽度)px/density这个值始终是360dp,这样的话，就能保证UI在不同的设备上表现一致了。



```java
 public static void setCustomDensity(@NonNull final Activity activity, Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        final float targetDensity = appDisplayMetrics.widthPixels / 360; //360为UI设计图的真实尺寸
        final int targetDpi = (int) (160 * targetDensity);

        displayMetrics.density = targetDensity;
        displayMetrics.densityDpi = targetDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.densityDpi = targetDpi;
    }
}
```

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在设置布局之前调用工具类方法
        ScreenUtils.setCustomDensity(this);
```

