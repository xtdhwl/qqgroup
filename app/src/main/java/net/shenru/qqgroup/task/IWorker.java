package net.shenru.qqgroup.task;

/**
 * Created by xtdhwl on 28/01/2018.
 */

public interface IWorker<T, R> {

    interface Callback<R> {
        void onResult(boolean success,R result);
    }

    void exec();

    Callback getCallback();

    void setCallback(Callback callback);
}
