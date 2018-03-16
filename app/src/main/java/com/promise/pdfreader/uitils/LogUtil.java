package com.promise.pdfreader.uitils;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.promise.pdfreader.App;

/**
 * @author: liulei
 * @date:2018/3/15
 */
public class LogUtil {
    private static final String TAG = "pdfreader";

    public static void d(String msg){

        if (App.DEBUG) {
//            Log.d(TAG,msg);
            Logger.d(msg);
        }

    }

    public static void i(String msg){

        Logger.i(msg);

    }


    public static void e(String msg){

        Logger.e(msg);

    }



}
