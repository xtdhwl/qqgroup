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
import io.reactivex.disposables.Disposable;

import static android.content.ContentValues.TAG;


/**
 * Created by xtdhwl on 29/01/2018.
 */

public class TroopWorker extends BaseWorker {
    @Override
    public void exec() {
        AccessibilityEvent event = getEvent();
        CharSequence className = event.getClassName();
        if (TroopActivity.class.getName().equals(className)) {
            getCallback().onResult(true, null);
            setFinish(true);
        } else {
            if (SplashActivity.class.getName().equals(className)) {
                boolean b = goTroop(event);
                Observable.timer(1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        exec();
                    }
                });
            } else {
                //TODO main task
                Logger.e("去首页");
            }
        }
    }

    private boolean goTroop(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();
        List<AccessibilityNodeInfo> nodeInfoList = NodeInfoUtil.findAccessibilityNodeInfosByViewId(source, "tbs");
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
