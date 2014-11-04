package com.appcenter.testingtool.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.appcenter.testingtool.util.TaoLog;

/**
 * Created by diskzhou on 14-4-14.
 */
public class AppReciver extends BroadcastReceiver {

    private final static String TAG="AppReciver";
    @Override
    public void onReceive(Context context, Intent intent) {
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            TaoLog.Logi(TAG,"haha");
            String packageName = intent.getDataString();
            TaoLog.Logi(TAG,"安装了:" +packageName + "包名的程序");
            TaoLog.Logi(TAG,intent.toString());
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            TaoLog.Logi(TAG,"卸载了:"  + packageName + "包名的程序");
        }
    }
}
