package net.shenru.qqgroup.task;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.orhanobut.logger.Logger;
import com.tencent.mobileqq.activity.SplashActivity;

import net.shenru.qqgroup.util.Shell;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by xtdhwl on 28/01/2018.
 */

public class MainWorker extends BaseWorker {

    @Override
    public void exec() {
        Logger.i("exec()");

    }

    @Override
    public void onEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        if (AccessibilityEvent.TYPE_WINDOWS_CHANGED == eventType) {
            CharSequence className = event.getClassName();
            if (SplashActivity.class.getName().equals(className)) {
                Logger.i("onResult true");
                getCallback().onResult(true, null);
                setFinish(true);
            } else {
                goBack();
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
            }
        }
    }

    private void goBack() {
        Logger.i("goBack()");
        Shell.exec("input keyevent 4");
    }


}
