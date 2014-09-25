package com.appcenter.testingtool.model;

import android.app.Application;
import android.view.WindowManager;

public class MyApplication extends Application {

    /**
     * 创建全局变量
     * 全局变量一般都比较倾向于创建一个单独的数据类文件，并使用static静态变量
     *
     * 这里使用了在Application中添加数据的方法实现全局变量
     * 注意在AndroidManifest.xml中的Application节点添加android:name=".MyApplication"属性
     *
     */
    private WindowManager.LayoutParams wmParams=new WindowManager.LayoutParams();
    private String monitorProcessName="com.taobao.appcenter";

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    private int uid;


    public String getMonitorProcessName() {
        return monitorProcessName;
    }

    public void setMonitorProcessName(String monitorProcessName) {
        this.monitorProcessName = monitorProcessName;
    }


    public WindowManager.LayoutParams getMywmParams(){
        return wmParams;
    }



}
