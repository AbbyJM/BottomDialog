package com.app.abby.bottomdialog;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Abby on 6/25/2018.
 */

public class Util {

    public static int getScreenHeight(Context context){
        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(dm);
        }
        return dm.heightPixels;
    }

    public static int getStatusBarHeight(Context context){
        int resId=context.getResources().getIdentifier("status_bar_height","dimen","android");
        return context.getResources().getDimensionPixelSize(resId);
    }

    public static int getActionBarHeight(Context context){
        TypedArray array=context.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int height= (int)array.getDimension(0,0);
        array.recycle();
        return height;
    }


}
