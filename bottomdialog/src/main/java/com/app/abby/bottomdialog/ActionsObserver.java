package com.app.abby.bottomdialog;

import android.util.Log;

import java.util.Queue;

/**
 * Created by Abby on 6/27/2018.
 */


public  class ActionsObserver{

    private Queue<DialogAction> mActions;

    public ActionsObserver(){

    }

    public ActionsObserver(Queue<DialogAction> actions){
        mActions=actions;
    }


    public void observe(Queue<DialogAction> actions){
        mActions=actions;
    }

    /**
     * notify the observer that an action is done and run next
     */
    public void actionDone(){
        mActions.remove();
        DialogAction act=mActions.peek();
        if(act!=null){
            act.run();
        }
    }

    /**
     * dispatch an action
     * add the action to the queue
     * and if the current action is at the head ,fire it
     */
    public void dispatchAction(DialogAction action){
        mActions.add(action);
        if(mActions.element()==action){
            action.run();
        }
    }


}