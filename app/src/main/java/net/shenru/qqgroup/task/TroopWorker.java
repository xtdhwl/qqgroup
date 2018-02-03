package net.shenru.qqgroup.task;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import com.orhanobut.logger.Logger;
import com.tencent.mobileqq.activity.SplashActivity;
import com.tencent.mobileqq.activity.contact.troop.TroopActivity;

import net.shenru.qqgroup.util.NodeInfoUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static android.content.ContentValues.TAG;
import static android.view.accessibility.AccessibilityEvent.TYPE_VIEW_CLICKED;


/**
 * Created by xtdhwl on 29/01/2018.
 */

public class TroopWorker extends BaseWorker {

    private AccessibilityEvent mEvent;
    private boolean isRuning = false;

    @Override
    public void exec() {
        Logger.i("exec()");
    }

    @Override
    public void onEvent(AccessibilityEvent event) {
        mEvent = event;
        if (isRuning) {
            return;
        }
        isRuning = true;


        CharSequence className = event.getClassName();
        int eventType = event.getEventType();


        if (eventType == AccessibilityEvent.TYPE_WINDOWS_CHANGED
                || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (TroopActivity.class.getName().equals(className)) {
                getCallback().onResult(true, null);
                setFinish(true);
                return;
            } else if (SplashActivity.class.getName().equals(className)) {
                //在主页
            } else {
                //TODO main task
//                Logger.e("去首页");
                //TODO 去主页
                return;
            }

        }

        if (event.getEventType() == TYPE_VIEW_CLICKED) {
            List<CharSequence> texts = event.getText();
            if (!texts.isEmpty()) {
                CharSequence text = texts.get(0);
                if ("群组".equals(text)) {
                    Logger.i("ignore TYPE_VIEW_CLICKED");
                    isRuning = false;
                    return;

                }
            }
        }
        boolean b = goTroop(event);
        isRuning = false;
    }

    private boolean goTroop(AccessibilityEvent event) {
        AccessibilityNodeInfo root;
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            root = event.getSource();
        } else {
            root = NodeInfoUtil.getRootParent(event.getSource());
        }

        if (root == null) {
            Logger.w("AccessibilityNodeInfo root null");
            return false;
        }
//        List<AccessibilityNodeInfo> conNodeInfoList = root.findAccessibilityNodeInfosByText("联系人");
        List<AccessibilityNodeInfo> conNodeInfoList = root.findAccessibilityNodeInfosByText("群组");
        if (!conNodeInfoList.isEmpty()) {
            AccessibilityNodeInfo cNodeInfo = conNodeInfoList.get(0);
            cNodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            return true;
        }
        return false;
    }
}
