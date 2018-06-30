# BottomDialog
An android bottom dialog view component with multiple views supports.

[中文版](README_CN.md) | **English** </br>

## Introduction
BottomDialog is an component for android,it allows you to git a bottom dialog view to you application.With mutiple views supported,you can easily switch bwtween serveral views.

## The final effect 

<div>
  <img width="236" height="384" src="https://github.com/AbbyJM/BottomDialog/blob/master/demo.gif"/>
</div>   


## Gradle 
```gradle
dependencies{
      compile 'com.abby.app:bottomdialog:0.1.0'//latest version
}
```

## Simple Usage
for example ,in your activity_main.xml,you may probably have this content view
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
then wrap it with the bottomdialog,but to be carefull that ***it should and only can hava one root content view in compile time,all the bottom dialog view will be added in run time with the method addView***
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
</com.app.abby.bottomdialog.BottomDialog>
  ```
we are done dealing with the xml so far,then in the MainActivity,we add a bottom dialog ***noted with a label***,a label must be unique,later we use this lable for recognizing each dialog,and ***expand it with the label***.If you add a dumplicated label,a run time exception will be thrown
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
finally expand it using expandPartial(String label) to expand the dialog
```java
  mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          mDialog.expandTotally("dialog"); //use the label noted previous
      }
 });
```

## APIS
### class BottomDialog which extends FrameLayout
```java
   /**
   * add a translation view with a label for later accessing
   * the label must be unique or a exception will be thrown
   */
   public void addView(final View child,String label) 
   
   /**
   *  expand partial of the dialog view
   *  you can give a partial translation position or use the default half of the screen height
   */
   public void expandPartial(String label) 
   
   /**
   * expand the view to be top of the screen,recommen the dialog height to be match_parent
   * the screen height-statusbar height
   */
   public void expandTotally(String label)

   /**
   * hide the dialog view if it is expaned,it will check if any of the dialog is expanded 
   * if no dialogs are expanded nothing happend
   */
   public void hide(String label)
  
   /**
   * set the partial translation position,default to be half of the screen height
   */
   public void setPartialTranslation(int translation)

   /**
   * set the mask view which will cover the content view when dialog is expanded 
   * the default mask view is a dark gray view 
   */
   public void setMaskView(View maskView)
   
   /**
   * set the max translation position,default transition is the screenHeight-statusbarHeight
   * recommend using the default height
   * but if you have an actionBar or int some situations you can calculate by youself 
   */
   public void setMaxTranslation(int translation)
   
   /**
   * set an interpolator of the translate animation,default to be a DeccelarateInterpolator with a factor 1.6
   */
   public void setInterpolator(Interpolator interpolator)
   
   /**
   * set the duration of the translation animation
   */
   public void setAnimationDuration(int duration)

   /**
   * determind if the dialog view is initialized
   */
   public boolean isInitialized()
   
   /**
   * set the touch slop
   */
   public void setTouchSlop(int slop)
   
   /**
   * set true to enable ScrollToExpand ,it enables you to scroll a partial expanded view to fully expanded 
   */
   public void enableScrollToExpand(boolean enable)
   
   /**
   * true to enable TouchToCancel,thus you can touch the unclient area to cancel(hide) the dialog view
   */
   public void enableTouchToCancel(boolean enable)
   
   /**
   * see if any of the dialog views is showing 
   * return true if there is one
   */
   public boolean isDialogShowing()

   /**
   * find out which dialog is showing 
   * return a BottomDialogView which is a wrapper class of the dialog view
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


