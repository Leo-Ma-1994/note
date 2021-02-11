是一个嵌套在Activity中的UI片段，初衷是为了适应大屏幕的平板电脑。使用fragment可以将屏幕划分为几块，生命周期受到宿主Activity的限制，需要嵌套在Activity中使用。





```
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(GroupActivity.this, LauncherActivity.class);
        intent.putExtra("fragment_flag",1);
        startActivity(intent);
    }
});
```