package com.app.abby.bottomdialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;

/**
 * Created by Abby on 6/27/2018.
 */

public class BottomDialogView{

    private View mView;
    private BottomDialog.State mState;
    private int mCurrentPosition;
    private String mLabel;
    private Config mConfig;
    private ActionsObserver mObserver;


    public BottomDialogView(View view, BottomDialog.State state, int pos, String label, Config config,ActionsObserver observer){
        mView=view;
        mState=state;
        mCurrentPosition=pos;
        mLabel=label;
        mConfig=config;
        mObserver=observer;
    }

    public View getView(){
        return mView;
    }
    public BottomDialog.State getState(){
        return mState;
    }
    public int getCurrentPos(){
        return mCurrentPosition;
    }
    public String getLabel(){
        return mLabel;
    }
    public void setView(View view){
        mView=view;
    }
    public void setState(BottomDialog.State state){
        mState=state;
    }
    public void setCurrentPos(int current_pos){
        mCurrentPosition=current_pos;
    }
    public void setLabel(String label){
        mLabel=label;
    }


    public void expandPartial(){

        ObjectAnimator animator;

        //if the view height is less than the partial translation height
        //then expand it totally
        if(mView!=null&&mView.getHeight()<mConfig.partialTranslation()) {
            animator = ObjectAnimator.ofFloat(mView, "translationY", -mView.getHeight());
        }else{
            animator=ObjectAnimator.ofFloat(mView,"translationY",-mConfig.partialTranslation());
        }

        animator.setInterpolator(mConfig.interpolator());

        animator.setDuration(mConfig.animationDuration());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation){
                super.onAnimationEnd(animation);
                if (mView!= null) {
                    if(mView.getHeight()<mConfig.partialTranslation()){
                        setState(BottomDialog.State.TOTAL_EXPANDED);
                        setCurrentPos(mConfig.initPosition()-mView.getHeight());
                    }else{
                        setState(BottomDialog.State.PARTIAL_EXPANDED);
                        setCurrentPos(mConfig.initPosition()-mConfig.partialTranslation());
                    }
                }

                mObserver.actionDone();
            }
        });
        if (mView != null) {
            setState(BottomDialog.State.ANIMATING);
        }
        animator.start();
    }

    public void expandTotally(){

        ObjectAnimator animator;
        animator=ObjectAnimator.ofFloat(mView,"translationY",-mConfig.maxTranslation());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setState(BottomDialog.State.TOTAL_EXPANDED);
                setCurrentPos(-mConfig.maxTranslation());

                mObserver.actionDone();
            }
        });
        animator.setInterpolator(mConfig.interpolator());
        animator.setDuration(mConfig.animationDuration());
        if (mView != null) {
            setState(BottomDialog.State.ANIMATING);
        }
        animator.start();
    }

    public void hide(){
        ObjectAnimator animator=null;

        if(mState== BottomDialog.State.TOTAL_EXPANDED){
            animator = ObjectAnimator.ofFloat(mView, "translationY", 0);
        }else if(mState== BottomDialog.State.PARTIAL_EXPANDED){
            animator = ObjectAnimator.ofFloat(mView, "translationY", 0);
        }

        if(animator!=null){
            animator.setDuration(mConfig.animationDuration());
            animator.setInterpolator(mConfig.interpolator());
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    setState(BottomDialog.State.HIDDEN);

                    mObserver.actionDone();
                }
            });
            if (mView != null) {
                setState(BottomDialog.State.ANIMATING);
            }
            animator.start();
        }
    }
}