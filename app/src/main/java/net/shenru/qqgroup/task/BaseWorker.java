package net.shenru.qqgroup.task;

import android.view.accessibility.AccessibilityEvent;

/**
 * exec();
 * <p>
 * finish();
 * <p>
 * Created by xtdhwl on 28/01/2018.
 */

public abstract class BaseWorker implements IWorker {

    private Callback mCallback;
    private Task task;
    private boolean isFinish = false;

    public synchronized boolean isFinish() {
        return isFinish;
    }

    public synchronized void setFinish(boolean finish) {
        isFinish = finish;
    }

    @Override
    public Callback getCallback() {
        return mCallback;
    }

    @Override
    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }


    public void onEvent(AccessibilityEvent event) {
        
    }
}
