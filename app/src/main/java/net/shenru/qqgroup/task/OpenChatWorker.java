package net.shenru.qqgroup.task;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.orhanobut.logger.Logger;
import com.tencent.mobileqq.activity.ChatActivity;
import com.tencent.mobileqq.activity.SplashActivity;
import com.tencent.mobileqq.activity.contact.troop.TroopActivity;

import net.shenru.qqgroup.util.NodeInfoUtil;

import java.util.List;

import static android.view.accessibility.AccessibilityEvent.TYPE_VIEW_CLICKED;

/**
 * Created by xtdhwl on 31/01/2018.
 */

public class OpenChatWorker extends BaseWorker {

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
            if (ChatActivity.class.getName().equals(className)) {
                getCallback().onResult(true, null);
                setFinish(true);
                return;
            } else if (TroopActivity.class.getName().equals(className)) {
                //在群组页面
            } else {
                //TODO main task
//                Logger.e("去首页");
                //TODO 去主页
                return;
            }
        }

//        if (event.getEventType() == TYPE_VIEW_CLICKED) {
//            isRuning = false;
//            return;
//        }
        boolean b = goChat(event);
        isRuning = false;
    }


    //id/qb_troop_list_view
    //com.tencent.mobileqq.widget.SlideDetectListView@1f4425c1
    private boolean goChat(AccessibilityEvent event) {
        //event.getSource().getChild(3).getChild(3).getChild(0)
        AccessibilityNodeInfo root = NodeInfoUtil.getRootParent(event.getSource());
        if (root == null) {
            Logger.w("AccessibilityNodeInfo root null");
            return false;
        }

        if (root.getChildCount() != 5) {
            Logger.w(root.toString());
            Logger.w("AccessibilityNodeInfo root childCount nq 5. getChildCount:%d", root.getChildCount());
            return false;
        }

        AccessibilityNodeInfo viewGroupNodeInfo = root.getChild(3);
        if (viewGroupNodeInfo.getChildCount() > 0) {
            Logger.w("viewGroupNodeInfo.getChildCount:%d", viewGroupNodeInfo.getChildCount());
            return false;
        }

        AccessibilityNodeInfo child = viewGroupNodeInfo.getChild(1);
        child.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        return true;
    }
}
