# BottomDialog
An android bottom dialog view component with multiple views supports.

[English](README.md) | **中文** </br>

## Introduction
BottomDialog是一个安卓底部dialog控件，支持多个dialog和他们之间的相互切换

## 最终效果

<div>
  <img width="236" height="384" src="https://github.com/AbbyJM/BottomDialog/blob/master/demo.gif"/>
</div>   


## Gradle 
```gradle
dependencies{
      compile 'com.abby.app:bottomdialog:0.2.0'//最新版本
}
```

## 简单用法
比如，可以在activity_main.xml中，如果我有这样一个布局
```xml
  <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
   <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="This is a bottom dialog view demo">
  </LinearLayout>

```
然后我们可以给它加一个BottomDialog父布局，但是需要注意的是编译时BottomDialog只能有一个子View。我们可以在运行时动态地添加dialog view
```xml
<com.app.abby.bottomdialog.BottomDialog
    android:id="@+id/dialog"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
       <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="This is a bottom dialog view demo">
   </LinearLayout>
</com.app.abby.bottomdialog.BottomDialog
```
现在布局文件已经写好，我们可以在MainActivity中添加一个dialog view，同时给它一个label作为第二个参数。label必须是唯一的，否则会抛出异常。
```java
  
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mDialog=findViewById(R.id.dialog_view);
        dialogView= LayoutInflater
                    .from(this)
                    .inflate(R.layout.dialog,mDialog,false);
        mDialog.addView(dialogView,"dialog");
        mDialog.enableTouchToCancel(true)
    }
```
最后调用 expandPartial(String label) 来展开dialog
```java
  mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          mDialog.expandTotally("dialog"); //用之前添加时候指定的label
      }
 });
```

## APIS
### class BottomDialog which extends FrameLayout
```java
   /**
   * 添加一个dialog view，同时指定一个label用于后面的访问
   * label必须是唯一的，否则会抛出异常
   */
   public void addView(final View child,String label) 
   
   /**
   *  展开一部分dialog view
   *  你可以设置展开的高度，默认是屏幕的一半高度
   */
   public void expandPartial(String label) 
   
   /**
   * 完全展开dialog view，也就是展开到屏幕顶部，高度默认为屏幕高度减去状态栏高度
   */
   public void expandTotally(String label)

   /**
   * 通过label隐藏正在展开的dialog，只有有dialog已经展开时才有效
   */
   public void hide(String label)
  
   /**
   * 设置部分展开时的高度，默认情况下是屏幕的一半高度
   */
   public void setPartialTranslation(int translation)

   /**
   * 设置mask view，也就是dialog展开时非dialog区域的蒙版效果
   */
   public void setMaskView(View maskView)
   
   /**
   * 设置最大展开高度，默认情况下是屏幕高度减去状态栏高度
   * 但是某些情况下我们可能想自己计算需要的高度
   */
   public void setMaxTranslation(int translation)
   
   /**
   * 设置展开动画的interpolator,默认情况下是 DeccelarateInterpolator 
   */
   public void setInterpolator(Interpolator interpolator)
   
   /**
   * 设置展开动画的duration
   */
   public void setAnimationDuration(int duration)

   /**
   * 检测dialog是否已经完全初始化
   */
   public boolean isInitialized()
   
   /**
   * 设置手指触摸的slop
   */
   public void setTouchSlop(int slop)
   
   /**
   * 设置是否启用滑动展开，当dialog部分展开时，可以向上滑完全展开，反之亦然
   */
   public void enableScrollToExpand(boolean enable)
   
   /**
   * 设置是否打开触摸取消，当dialog部分展开时，可以点击非dialog区域隐藏dialog
   */
   public void enableTouchToCancel(boolean enable)
   
   /**
   * 检测是否有dialog已经展开，返回真如果有
   */
   public boolean isDialogShowing()

   /**
   * 找出哪个dialog已经展开，返回一个BottomDialogView对象
   */
   public BottomDialogView whichIsShowing(){

```

# License
      Copyright 2018 AbbyJM

      Licensed under the Apache License, Version 2.0 (the "License");
      you may not use this file except in compliance with the License.
      You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

      Unless required by applicable law or agreed to in writing, software
      distributed under the License is distributed on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      See the License for the specific language governing permissions and
      limitations under the License.


