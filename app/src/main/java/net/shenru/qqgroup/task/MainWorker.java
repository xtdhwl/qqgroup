package net.shenru.qqgroup.task;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.orhanobut.logger.Logger;
import com.tencent.mobileqq.activity.SplashActivity;

import net.shenru.qqgroup.util.Shell;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
import static android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;


/**
 * Created by xtdhwl on 28/01/2018.
 */

public class MainWorker extends BaseWorker {

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


        int eventType = event.getEventType();
        if (eventType == AccessibilityEvent.TYPE_WINDOWS_CHANGED
                || eventType == TYPE_WINDOW_CONTENT_CHANGED
                || eventType == TYPE_WINDOW_STATE_CHANGED) {
            isRuning = true;
            CharSequence className = event.getClassName();
            if (SplashActivity.class.getName().equals(className)) {
                getCallback().onResult(true, null);
                setFinish(true);
            } else {
                goBack();
                Observable.timer(1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Long>() {
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
                                Logger.d("onComplete");
                                isRuning = false;
                                onEvent(mEvent);
                            }
                        });
            }
        }
    }

    private void goBack() {
        Logger.i("goBack()");
        Shell.exec("input keyevent 4");
    }


}
