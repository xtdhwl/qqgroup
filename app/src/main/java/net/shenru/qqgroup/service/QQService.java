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
        if (event.getEventType() == TYPE_WINDOW_STATE_CHANGED) {
            typeWindowStateChanged(event);
        }
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


    @Override
    public void onInterrupt() {

    }
}
