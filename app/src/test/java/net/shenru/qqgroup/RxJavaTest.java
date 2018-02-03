package net.shenru.qqgroup;

import com.orhanobut.logger.Logger;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by xtdhwl on 30/01/2018.
 */

public class RxJavaTest {


    @Test
    public void testTime(){
        long currentTimeMillis = System.currentTimeMillis();
        Observable.timer(5, TimeUnit.SECONDS).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Logger.d("onSubscribe");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Logger.d("onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("onError");
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete");
                        Logger.d(Thread.currentThread().getName());
                        long timeMillis = System.currentTimeMillis();
                        Logger.d(timeMillis - currentTimeMillis);
                    }
                });
    }
}
