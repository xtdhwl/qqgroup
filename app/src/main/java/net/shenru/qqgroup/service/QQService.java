package net.shenru.qqgroup.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.tencent.mobileqq.activity.TroopAssistantActivity;

import net.shenru.qqgroup.util.NodeInfoUtil;

import java.util.List;

import static android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;

/**
 * Created by xtdhwl on 23/01/2018.
 */

public class QQService extends AccessibilityService {

    private static final String TAG = QQService.class.getSimpleName();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, event.toString());

        QQManager.getInstance().onEvent(event);
    }




    @Override
    public void onInterrupt() {

    }
}
