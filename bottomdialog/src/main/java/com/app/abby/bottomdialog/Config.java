package com.app.abby.bottomdialog;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.Date;
import java.util.Timer;

/**
 * Created by Abby on 6/27/2018.
 */

/**
 * all configurations lie here
 */
public class Config {

    private Context mContext;
    private int mScreenHeight;
    private int mMaxTranslation;
    private int mPartialTranslation;
    private int mInitPosition;
    private int mSlop;
    private float mLastPointY;
    private float mLastPointX;
    private View mMaskView;
    private Interpolator mInterpolator;
    private int mAnimationDuration;
    private boolean mInitialized;
    private boolean mEnableScrollToExpand;
    private boolean mEnableTouchToCancel;



    public Config(Context context){
        mContext=context;
        mScreenHeight=Util.getScreenHeight(mContext);
        mMaxTranslation=mScreenHeight-Util.getStatusBarHeight(mContext);
        mPartialTranslation=mMaxTranslation/2;
        mInitialized=false;
        mLastPointY=0;
        mLastPointX=0;
        mInitPosition=Util.getScreenHeight(mContext)-Util.getStatusBarHeight(mContext);
        mInterpolator=new DecelerateInterpolator(1.6f);
        mAnimationDuration=400;
        mMaskView=new BottomDialog.MaskView(mContext);
        mSlop=80;
        mEnableScrollToExpand=false;
        mEnableTouchToCancel=false;
    }


    public int screenHeight(){
        return mScreenHeight;
    }

    public void setScreenHeight(int height){
        mScreenHeight=height;
    }

    public int maxTranslation(){
        return mMaxTranslation;
    }

    public void setMaxTranslation(int translation){
        mMaxTranslation=translation;
    }

    public int partialTranslation(){
        return mPartialTranslation;
    }

    public void setPartialTranslation(int translation){
        mPartialTranslation=translation;
    }

    public int initPosition(){
        return mInitPosition;
    }

    public void setInitPosition(int position){
        mInitPosition=position;
    }
    public int slop(){
        return mSlop;
    }

    public void setSlop(int slop){
        mSlop=slop;
    }

    public float lastPointX(){
        return mLastPointX;
    }

    public void setLastPointX(float x){
        mLastPointX=x;
    }

    public float lastPointY(){
        return mLastPointY;
    }

    public void setLastPointY(float y){
        mLastPointY=y;
    }
    public View maskView(){
        return mMaskView;
    }

    public void setMaskView(View view){
        mMaskView=view;
    }

    public Interpolator interpolator(){
        return mInterpolator;
    }

    public void setInterpolator(Interpolator interpolator){
        mInterpolator=interpolator;
    }

    public int animationDuration(){
        return mAnimationDuration;
    }

    public void setAnimationDuration(int duration){
        mAnimationDuration=duration;
    }

    public boolean initialized(){
        return mInitialized;
    }

    public void setInitialized(boolean initialized){
        mInitialized=initialized;
    }

    public boolean enableScrollToExpand(){
        return mEnableScrollToExpand;
    }

    public void setEnableScrollToExpand(boolean enableScrollToExpand){
        mEnableScrollToExpand=enableScrollToExpand;
    }

    public boolean enableTouchToCancel(){
        return mEnableTouchToCancel;
    }


    public void setEnableTouchToCancel(boolean enableTouchToCancel){
        mEnableTouchToCancel=enableTouchToCancel;
    }


}
