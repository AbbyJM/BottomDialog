package com.app.abby.bottomdialog;

import android.util.Log;

/**
 * Created by Abby on 6/27/2018.
 */

public  class DialogAction{

    private BottomDialogView mView;
    private BottomDialog.ACTION mAction;
    private ActionListener mListener;
    public DialogAction(BottomDialogView view,BottomDialog.ACTION action){
        mView=view;
        mAction=action;
    }
    public void setAction(BottomDialog.ACTION action){
        mAction=action;
    }
    public void setView(BottomDialogView view){
        mView=view;
    }

    public void run(){
        if(mListener!=null){
            mListener.start();
        }
        switch (mAction){
            case TOTAL_EXPAND:
                totalExpand();
                break;
            case PARTIAL_EXPAND:
                partialExpand();
                break;
            case HIDE:
                hide();
                break;
        }
    }

    public void partialExpand(){
        mView.expandPartial();
    }

    public void totalExpand(){
        mView.expandTotally();
    }

    public void hide(){
        mView.hide();
    }

    public interface ActionListener{
        void start();
        void done();
    }

    public void setActionListener(ActionListener listener){
        mListener=listener;
    }

    public void done(){
        if(mListener!=null)
        mListener.done();
    }


}
