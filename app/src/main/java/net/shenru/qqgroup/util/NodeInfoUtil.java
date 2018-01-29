package net.shenru.qqgroup.util;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xtdhwl on 26/01/2018.
 */

public class NodeInfoUtil {


    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(AccessibilityNodeInfo source, String id) {
        return source.findAccessibilityNodeInfosByViewId(id);
    }


    public static List<AccessibilityNodeInfo> findAccessibilityNodeInfo(AccessibilityNodeInfo source, String className) {
        List<AccessibilityNodeInfo> list = new ArrayList<>();
        int childCount = source.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = source.getChild(i);
            if (className.equals(child.getClassName())) {
                list.add(child);
            }
            List<AccessibilityNodeInfo> childList = findAccessibilityNodeInfo(child, className);
            list.addAll(childList);
        }
        return list;
    }

    /**
     * log
     *
     * @param tag
     * @param source
     */
    public static void logAccessibilityNodeInfo(String tag, AccessibilityNodeInfo source) {
        int childCount = source.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = source.getChild(i);
            Log.d(tag, child.toString());
            logAccessibilityNodeInfo(tag, child);
        }
    }
}
