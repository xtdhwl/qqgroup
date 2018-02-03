package net.shenru.qqgroup.service;

import android.view.accessibility.AccessibilityEvent;

import com.orhanobut.logger.Logger;

import net.shenru.qqgroup.SettingsConstant;
import net.shenru.qqgroup.task.BaseWorker;
import net.shenru.qqgroup.task.IWorker;
import net.shenru.qqgroup.task.Task;
import net.shenru.qqgroup.work.settings.SelectWork;
import net.shenru.qqgroup.work.settings.SettingMainWork;
import net.shenru.qqgroup.work.settings.ToggleWork;

/**
 * Created by xtdhwl on 04/02/2018.
 */

public class SettingsManager {


    private static final String TAG = SettingsManager.class.getSimpleName();
    public static SettingsManager mInstance = new SettingsManager();
    private BaseWorker mCurrentWorker;
    private AccessibilityEvent mLastAccessibilityEvent;

    public static SettingsManager getInstance() {
        return mInstance;
    }

    public void onEvent(AccessibilityEvent event) {
        Logger.i(event.toString());
        Logger.i(event.getSource().toString());
        mLastAccessibilityEvent = event;
        BaseWorker currentWorker = getCurrentWorker();
        if (currentWorker != null && !currentWorker.isFinish()) {
            Logger.i("ignore run:%s ", currentWorker.toString());
            currentWorker.onEvent(event);
            return;
        }


        Task task = TaskManager.getInstance().getTask();
        dispatchWork(task, event);
    }


    private void dispatchWork(Task task, AccessibilityEvent event) {
        Logger.i("Current Task:%s", task.toString());

        if (SettingsConstant.ACTIVITY_SETTINGS.equals(task.getEvent())) {
            SettingMainWork worker = new SettingMainWork();
            worker.setTask(task);
            worker.setCallback(new IWorker.Callback() {
                @Override
                public void onResult(boolean success, Object result) {
                    if (success) {
                        TaskManager.getInstance().nextTask();
                        setCurrentWorker(null);
                        onEvent(mLastAccessibilityEvent);
                    }
                }
            });
            exec(worker, event);
        } else if (SettingsConstant.ACTIVITY_TOGGLE.equals(task.getEvent())) {
            ToggleWork worker = new ToggleWork();
            worker.setTask(task);
            worker.setCallback(new IWorker.Callback() {
                @Override
                public void onResult(boolean success, Object result) {
                    if (success) {
                        TaskManager.getInstance().nextTask();
                        setCurrentWorker(null);
                        onEvent(mLastAccessibilityEvent);
                    }
                }
            });
            exec(worker, event);
        } else if (SettingsConstant.ACTION_SELECT.equals(task.getEvent())) {
            SelectWork worker = new SelectWork();
            worker.setTask(task);
            worker.setCallback(new IWorker.Callback() {
                @Override
                public void onResult(boolean success, Object result) {
                    if (success) {
                        TaskManager.getInstance().nextTask();
                        setCurrentWorker(null);
                        onEvent(mLastAccessibilityEvent);
                    }
                }
            });
            exec(worker, event);
        }
    }

    private void exec(BaseWorker worker, AccessibilityEvent event) {
        setCurrentWorker(worker);
        worker.exec();
        worker.onEvent(event);
    }

    public AccessibilityEvent getLastAccessibilityEvent() {
        return mLastAccessibilityEvent;
    }

    public BaseWorker getCurrentWorker() {
        return mCurrentWorker;
    }

    public void setCurrentWorker(BaseWorker currentWorker) {
        mCurrentWorker = currentWorker;
    }


}
