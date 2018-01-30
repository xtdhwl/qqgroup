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

    public static QQManager getInstance() {
        return mInstance;
    }

    public void onEvent(AccessibilityEvent event) {
        Logger.i(event.getSource().toString());

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
                    if (success) {
                        TaskManager.getInstance().nextTask();
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

    public BaseWorker getCurrentWorker() {
        return mCurrentWorker;
    }

    public void setCurrentWorker(BaseWorker currentWorker) {
        mCurrentWorker = currentWorker;
    }

    private void typeWindowStateChanged(AccessibilityEvent event) {
        CharSequence className = event.getClassName();
        if (TroopAssistantActivity.class.getName().equals(className)) {
            AccessibilityNodeInfo source = event.getSource();
            NodeInfoUtil.logAccessibilityNodeInfo(TAG, source);
            //TODO samsung sdk 15
//            List<AccessibilityNodeInfo> listNodeInfo = NodeInfoUtil.findAccessibilityNodeInfo(source, "com.tencent.widget.XListView");
//            Log.d(TAG, "com.tencent.widget.XListView:" + listNodeInfo.size());
            //TODO SM-C5000 24
            List<AccessibilityNodeInfo> listNodeInfo = NodeInfoUtil.findAccessibilityNodeInfo(source, "android.view.ViewGroup");
            Log.d(TAG, "android.view.ViewGroup:" + listNodeInfo.size());
            if (!listNodeInfo.isEmpty()) {
                AccessibilityNodeInfo listNode = listNodeInfo.get(0);
                int childCount = listNode.getChildCount();
                Log.d(TAG, "android.view.ViewGroup childCount:" + childCount);
                for (int i = 0; i < childCount; i++) {
                    AccessibilityNodeInfo listItemNode = listNode.getChild(i);
                    Log.d(TAG, listItemNode.toString());
                    listItemNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                    return;
                }
            }
        }
    }
}
