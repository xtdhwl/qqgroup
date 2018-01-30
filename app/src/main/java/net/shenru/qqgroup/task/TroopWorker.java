package net.shenru.qqgroup.task;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

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


        boolean b = goTroop(event);
        isRuning = false;
//        if (b) {
//            Observable.timer(10, TimeUnit.MILLISECONDS)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<Long>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(Long aLong) {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            isRuning = false;
//
//                        }
//                    });
//        } else {
//            isRuning = false;
//        }
    }

    private boolean goTroop(AccessibilityEvent event) {

        AccessibilityNodeInfo root = event.getSource().getWindow().getRoot();
        //root.findAccessibilityNodeInfosByViewId("");

        List<AccessibilityNodeInfo> nodeInfoList = root.findAccessibilityNodeInfosByViewId("tbs");
        if (!nodeInfoList.isEmpty()) {
            AccessibilityNodeInfo nodeInfo = nodeInfoList.get(0);
            List<AccessibilityNodeInfo> conNodeInfoList = nodeInfo.findAccessibilityNodeInfosByText("联系人");
            if (!conNodeInfoList.isEmpty()) {
                AccessibilityNodeInfo cNodeInfo = conNodeInfoList.get(0);
                AccessibilityNodeInfo parent = cNodeInfo.getParent();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                return true;
            }
        }
        return false;
    }
}
