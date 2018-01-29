package net.shenru.qqgroup.util;

import java.io.IOException;

/**
 * Created by xtdhwl on 28/01/2018.
 */

public class Shell {


    public static void exec(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
