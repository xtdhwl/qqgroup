package net.shenru.qqgroup;

import android.app.Application;

import net.shenru.qqgroup.util.SRLog;

/**
 * Created by xtdhwl on 29/01/2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SRLog.init();
    }
}
