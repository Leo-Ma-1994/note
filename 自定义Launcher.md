[toc]



# 去掉QuickSearchBar

只有第一屏有QuickSearchBar。QSB默认在第一个屏幕中且无法移动。

> workspace.java中

```Java
    public void bindAndInitFirstWorkspaceScreen(View qsb) {
        if (!FeatureFlags.QSB_ON_FIRST_SCREEN) {
            return;
        }
        
```

将QSB_ON_FIRST_SCREEN改为false即可。



# 横向化改造

把原生Launcher从抽屉形态改造成横向形态

总体：屏蔽原有抽屉化业务，将数据全部放到第一层桌面，然后新增更新第一层桌面的业务代码。

