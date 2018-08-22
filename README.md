# VExpandableView
通过属性动画实现的底部展开菜单组件，如图：
<img src="https://github.com/CodeMAI/VExpandableView/blob/master/show.gif"  height="360" width="540">
用法：
```java
final String[] items = new String[]{"J", "底片", "浮雕", "雕刻", "美白", "木刻"};
expandView.setTextList(items);
expandView.setOnItemClickListener(new VExpandableView.OnItemClickListener() {
  @Override
  public void onItemClick(int pos) {
    Toast.makeText(MainActivity.this, items[pos], Toast.LENGTH_SHORT).show();
  }
});
```
