package net.shenru.qqgroup.service;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.orhanobut.logger.Logger;
import com.tencent.mobileqq.activity.TroopAssistantActivity;

import net.shenru.qqgroup.QQConstant;
import net.shenru.qqgroup.task.BaseWorker;
import net.shenru.qqgroup.task.IWorker;
import net.shenru.qqgroup.task.MainWorker;
import net.shenru.qqgroup.task.OpenChatWorker;
import net.shenru.qqgroup.task.Task;
import net.shenru.qqgroup.task.TroopWorker;
import net.shenru.qqgroup.util.NodeInfoUtil;

import java.util.List;

import static android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;

/**
 * Created by xtdhwl on 28/01/2018.
 */

public class QQManager {

    private static final String TAG = QQManager.class.getSimpleName();
    public static QQManager mInstance = new QQManager();
    private BaseWorker mCurrentWorker;
    private AccessibilityEvent mLastAccessibilityEvent;

    public static QQManager getInstance() {
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

        if (QQConstant.ACTIVITY_SPLASH.equals(task.getEvent())) {
            Logger.i("MainWorker run");
            MainWorker mainWorker = new MainWorker();
            mainWorker.setTask(task);
            mainWorker.setCallback(new IWorker.Callback() {
                @Override
                public void onResult(boolean success, Object result) {
                    Logger.i("MainWorker onResult:%b", success);
                    if (success) {
                        TaskManager.getInstance().nextTask();
                        setCurrentWorker(null);
                        onEvent(mLastAccessibilityEvent);
                    }
                }
            });
            exec(mainWorker, event);
        } else if (QQConstant.ACTIVITY_TROOP.equals(task.getEvent())) {
            Logger.i("TroopWorker run");
            TroopWorker worker = new TroopWorker();
            worker.setTask(task);
            worker.setCallback(new IWorker.Callback() {
                @Override
                public void onResult(boolean success, Object result) {
                    Logger.i("TroopWorker onResult:%b", success);
                    if (success) {
                        TaskManager.getInstance().nextTask();
                        setCurrentWorker(null);
                        onEvent(mLastAccessibilityEvent);
                    }
                }
            });
            exec(worker, event);
        } else if (QQConstant.ACTIVITY_CHAT.equals(task.getEvent())) {
            Logger.i("OpenChatWorker run");
            OpenChatWorker worker = new OpenChatWorker();
            worker.setTask(task);
            worker.setCallback(new IWorker.Callback() {
                @Override
                public void onResult(boolean success, Object result) {
                    Logger.i("OpenChatWorker onResult:%b", success);
                    if (success) {
                        TaskManager.getInstance().nextTask();
                        setCurrentWorker(null);
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
