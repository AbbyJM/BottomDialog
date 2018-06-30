package com.app.abby.bottomdialog;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;


import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;


import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * Created by Abby on 6/25/2018.
 */

public class BottomDialog extends FrameLayout {

    /**
     * the state of the bottom dialog,
     * which hidden means the dialog is out of screen
     * animating indicates that the dialog is animating in or out,
     * half_expanded indicates that the dialog is half expanded
     * total_expanded indicates that the dialog is totally expanded,with the max height
     */
    public enum State{
        HIDDEN,
        ANIMATING,
        PARTIAL_EXPANDED,
        TOTAL_EXPANDED
    }

    public enum ACTION{
        PARTIAL_EXPAND,
        TOTAL_EXPAND,
        HIDE
    }

    private Config mConfig;
    private Context mContext;
    private ActionsObserver mObserver;
    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;
    //view of the content
    private View mContentView;
    //the default state is hidden
    private Map<String,BottomDialogView> mTranslationViews=new HashMap<>();

    //10 actions at most in the queue
    private Queue<DialogAction> mActions=new ArrayBlockingQueue<DialogAction>(10);
    public BottomDialog(Context context){
        super(context);
        mContext=context;
        init();
    }

    public BottomDialog(Context context,AttributeSet attrs){
        super(context,attrs);
        mContext=context;
        init();
    }

    public BottomDialog(Context context, AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        mContext=context;
        init();
    }

    /**
     * the initialize method
     */
    private void init(){
        mConfig=new Config(mContext);
        setFocusableInTouchMode(true);
        checkChildCount();
        mObserver=new ActionsObserver(mActions);
        final ViewTreeObserver mViewTreeObserver = getViewTreeObserver();

        mGlobalLayoutListener=new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //make sure our bottom dialog is initialized
                mConfig.setInitialized(true);
            }
        };
        mViewTreeObserver.addOnGlobalLayoutListener(mGlobalLayoutListener);

    }


    public void setPartialTranslation(int translation){
        mConfig.setPartialTranslation(translation);
    }

    public void setInitPosition(int position){
        mConfig.setInitPosition(position);
    }

    public void setMaskView(View maskView){
        mConfig.setMaskView(maskView);
    }

    public void setMaxTranslation(int translation){
        mConfig.setMaxTranslation(translation);
    }

    public void setInterpolator(Interpolator interpolator){
        mConfig.setInterpolator(interpolator);
    }

    public void setAnimationDuration(int duration){

        mConfig.setAnimationDuration(duration);
    }

    public boolean isInitialized(){
        return mConfig.initialized();
    }

    public void setTouchSlop(int slop){
        mConfig.setSlop(slop);
    }

    /**
     * indicate if we can scroll from partial expand to total expand
     * @param enable,true if enable
     */
    public void enableScrollToExpand(boolean enable){
        mConfig.setEnableScrollToExpand(enable);
    }

    /**
     * indicate if we can cancel this bottom dialog by touching the outside area of the client
     * @param enable,true if enable
     */
    public void enableTouchToCancel(boolean enable){
        mConfig.setEnableTouchToCancel(enable);
    }

    /**
     * find out if noe of the dialog view is showing
     */
    public boolean isDialogShowing(){
        for(Map.Entry<String,BottomDialogView> entry:mTranslationViews.entrySet()){
            BottomDialogView view=entry.getValue();
            if(view.getState()==State.PARTIAL_EXPANDED||view.getState()==State.TOTAL_EXPANDED){
                return true;
            }
        }
        return false;
    }


    /**
     * find out which dialog is showing
     */
    public BottomDialogView whichIsShowing(){
        for(Map.Entry<String,BottomDialogView> entry:mTranslationViews.entrySet()){
            BottomDialogView view=entry.getValue();
            if(view.getState()==State.PARTIAL_EXPANDED||view.getState()==State.TOTAL_EXPANDED){
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed,left,top,right,bottom);
        //we make the first child the root content view
        mContentView=getChildAt(0);
    }


    /**
     * enable ScrollToExpand or TouchToCancel if needed
     *
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        BottomDialogView which=whichIsShowing();

        if(!mConfig.enableScrollToExpand()&&!mConfig.enableTouchToCancel()|| which==null){
            return super.onTouchEvent(event);
        }
        super.onTouchEvent(event);
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if(mConfig.enableTouchToCancel()){
                    if(!isPointContained(event.getX(),event.getY(),which)){
                        hide(which);
                    }
                }
                mConfig.setLastPointX(event.getX());
                mConfig.setLastPointY(event.getY());

                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                float diff=-(event.getY()-mConfig.lastPointY());
                if(diff>mConfig.slop()&&which.getState()==State.PARTIAL_EXPANDED){
                    if(which.getView()!=null&&which.getState()==State.PARTIAL_EXPANDED&&isPointContained(mConfig.lastPointX(),mConfig.lastPointY(),which)){
                        expandTotally(which);
                    }
                }else if(diff<-mConfig.slop()){
                    if(which.getState()==State.TOTAL_EXPANDED||which.getState()==State.PARTIAL_EXPANDED&&isPointContained(mConfig.lastPointX(),mConfig.lastPointY(),which)){
                        hide(which);
                    }
                }
                mConfig.setLastPointY(event.getY());
                break;
        }
        return true;
    }

    /**
     * see if the point(x,y) lies in the translation view client
     */
    private boolean isPointContained(float x,float y,BottomDialogView child) {
        View view=null;
        if(child!=null){
           view=child.getView();
        }

        return view != null && x >= view.getX() && x <= view.getX() + view.getWidth() && y >= view.getY() && y <= view.getY() + view.getHeight();
    }

    /**
     * add a translation view with a label for later accessing
     * @param child,the view to be added
     * @param label,the unique label of this view
     */
    public void addView(final View child,String label){

        if(mTranslationViews.containsKey(label)){
            throw new RuntimeException("the label "+label+" already exists,consider replacing it with another");
        }
        BottomDialogView view=new BottomDialogView(child,State.HIDDEN,mConfig.initPosition(),label,mConfig,mObserver);
        mTranslationViews.put(label,view);
        addView(child);
        layoutView(child);

    }

    /**
     *  check the child count in compile time
     *  child views should be add in run time
     */
    private void checkChildCount(){
        if(getChildCount()>=2){
            throw new RuntimeException("views should be added using the dynamic method instead of putting in the layout xml");
        }
    }


    /**
     * put the view in the correct position
     */
    public void layoutView(final View child){
        child.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
            public void onGlobalLayout() {
                child.layout(child.getLeft(),mConfig.initPosition(),child.getRight(),mConfig.initPosition()+child.getHeight());
            }
        });
    }


    /**
     * partially expand the bottom dialog view with the label
     */
    public void expandPartial(String label){
        if(!mConfig.initialized()){
            throw new RuntimeException("the bottom dialog view is not totally initialized yet");
        }

        BottomDialogView which=whichIsShowing();
        if(which!=null){
            hide(which);
        }
        final BottomDialogView  child=mTranslationViews.get(label);
        expandPartial(child);
    }

    /**
     * partially expand the bottom dialog view with the BottomDialogView object
     */
    public void expandPartial(final BottomDialogView child){

        DialogAction action=new DialogAction(child,ACTION.PARTIAL_EXPAND);
        action.setActionListener(new DialogAction.ActionListener() {
            @Override
            public void start() {
                if(indexOfChild(mConfig.maskView())!=-1){
                    removeView(mConfig.maskView());
                }
                int index=indexOfChild(child.getView());
                addView(mConfig.maskView(),index);
            }

            @Override
            public void done() {

            }
        });
        mObserver.dispatchAction(action);
    }

    /**
     * totally expand the BottomDialogView with label
     */
    public void expandTotally(String label){
        if(!mConfig.initialized()){
            throw new RuntimeException("the bottom dialog view is not totally initialized yet");
        }
        final BottomDialogView  child=mTranslationViews.get(label);
        expandTotally(child);
    }


    /**
     * totally expand the BottomDialogView with the object itself
     */
    public void expandTotally(final BottomDialogView child){
        //child.expandTotally();
        DialogAction action=new DialogAction(child,ACTION.TOTAL_EXPAND);
        action.setActionListener(new DialogAction.ActionListener() {
            @Override
            public void start() {
                if(indexOfChild(mConfig.maskView())!=-1){
                    removeView(mConfig.maskView());
                }
                int index=indexOfChild(child.getView());
                addView(mConfig.maskView(),index);
            }

            @Override
            public void done() {

            }
        });
        mObserver.dispatchAction(new DialogAction(child,ACTION.TOTAL_EXPAND));
    }


    /**
     * hide the dialog view with the label
     */
    public void hide(String label){
        BottomDialogView which=whichIsShowing();
        if(which==null){
            return;
        }
        BottomDialogView child=mTranslationViews.get(label);
        hide(child);
    }


    /**
     * iterate all view to find if there is a view expanded
     * hide it if we found one
     * typically we should call hide(String label)
     *
     */
    public void hide(){
        for(Map.Entry<String,BottomDialogView> entry:mTranslationViews.entrySet()){
            State state=entry.getValue().getState();
            if(state==State.PARTIAL_EXPANDED||state==State.TOTAL_EXPANDED){
                hide(entry.getValue());
            }
        }
    }


    /**
     * hide the BottomDialogView
     * the view will not be destroy after it hides
     */
    public void hide(final BottomDialogView child){
        if(child==null){
            return;
        }
        removeView(mConfig.maskView());
        mObserver.dispatchAction(new DialogAction(child,ACTION.HIDE));
    }

    /**
     * gc method
     */
    public void destroyView(){
        mContext=null;
        mTranslationViews.clear();
        removeAllViews();
    }


    //default mask view
    public static class MaskView extends View{

        public  static final String DEFAULT_MASK_COLOR="#86222222";
        public MaskView(Context context){
            super(context);
        }

        public MaskView(Context context,AttributeSet attrs){
            super(context,attrs);
        }
        public MaskView(Context context,AttributeSet attrs,int defStyle){
            super(context,attrs,defStyle);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        }

        @Override
        public void onDraw(Canvas canvas){
            canvas.drawColor(Color.parseColor(DEFAULT_MASK_COLOR));
        }
    }

}
