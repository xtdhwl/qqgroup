package net.shenru.qqgroup.util;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by xtdhwl on 29/01/2018.
 */

public class SRLog {

    public static void init(){
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
