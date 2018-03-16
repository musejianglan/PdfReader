package com.promise.pdfreader;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.promise.pdfreader.dao.GreenDaoManager;
import com.promise.pdfreader.greendao.gen.DaoMaster;
import com.promise.pdfreader.greendao.gen.DaoSession;
import com.promise.pdfreader.uitils.LogUtil;

import java.util.LinkedList;


/**
 * @author: liulei
 * @date:2018/3/15
 */
public class App extends Application implements Application.ActivityLifecycleCallbacks{

    private static App instance;
    public static final boolean DEBUG = true;
    private final static String TAG = "exit";

    public static LinkedList<Activity> activityLinkedList;

    public static App getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initGreenDao();
        //
        activityLinkedList = new LinkedList<>();
        registerActivityLifecycleCallbacks(this);

        initLogger();

    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(0)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("pdfReader")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public void initGreenDao(){
        GreenDaoManager.getInstance();
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtil.d("onActivityCreated: " + activity.getLocalClassName());
        activityLinkedList.add(activity);
        // 在Activity启动时（onCreate()） 写入Activity实例到容器内
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtil.d( "onActivityDestroyed: " + activity.getLocalClassName());
        activityLinkedList.remove(activity);
        // 在Activity结束时（Destroyed（）） 写出Activity实例

    }

    public void exitApp() {

        LogUtil.d("容器内的Activity列表如下 ");
        // 先打印当前容器内的Activity列表
        for (Activity activity : activityLinkedList) {
            LogUtil.d(activity.getLocalClassName());
        }

        LogUtil.d("正逐步退出容器内所有Activity");

        // 逐个退出Activity
        for (Activity activity : activityLinkedList) {
            activity.finish();
        }

        //  结束进程
        System.exit(0);
    }
}
