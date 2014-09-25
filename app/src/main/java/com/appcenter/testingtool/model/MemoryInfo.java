package com.appcenter.testingtool.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ActivityManager;

import android.content.Context;
import android.os.Build;
import android.os.Debug;

public class MemoryInfo {

    private static final String TAG = "MemoryInfo";

   private Context context;
   private  ActivityManager activityManager;

    public MemoryInfo(Context mContext){
        this.context =mContext;
        activityManager= (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
    }

    public long getFreeMemorySize() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(mi);
        return mi.availMem;
    }

    public long getHeapSize(){
        return activityManager.getMemoryClass();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public long getTotalMemorySize() {
        if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
            return getTotalMemByShell();
        }
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(mi);
        return mi.totalMem;
    }



    public long getProcessMemorySize(String mProcessName) {


        // 通过调用ActivityManager的getRunningAppProcesses()方法获得系统里所有正在运行的进程
        List<ActivityManager.RunningAppProcessInfo> appProcessList = activityManager
                .getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {

           // TaoLog.Logi(TAG,appProcessInfo.processName);

            if (appProcessInfo.processName.equalsIgnoreCase(mProcessName)){
                // 进程ID号
                int pid = appProcessInfo.pid;
                // 获得该进程占用的内存
                int[] myMempid = new int[] { pid };

                // 此MemoryInfo位于android.os.Debug.MemoryInfo包中，用来统计进程的内存信息
                Debug.MemoryInfo[] memoryInfo = activityManager
                        .getProcessMemoryInfo(myMempid);
                // 获取进程占内存用信息 kb单位
                return memoryInfo[0].dalvikPrivateDirty*1024;

            }

        }
        //查无进程返回0
        return 0;
    }



    public  long getTotalMemByShell() {
        long mTotal;
        // 系统内存
        String path = "/proc/meminfo";
        // 存储器内容
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                // 采集内存信息
                content = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // beginIndex
        int begin = content.indexOf(':');
        // endIndex
        int end = content.indexOf('k');
        // 采集数量的内存
        content = content.substring(begin + 1, end).trim();
        // 转换为Int型
        mTotal = Integer.parseInt(content);
        return mTotal;
    }


    private void getRunningAppProcessInfo() {
        ActivityManager mActivityManager = (ActivityManager) this.context.getSystemService(Context.ACTIVITY_SERVICE);

        //获得系统里正在运行的所有进程
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = mActivityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessesList) {
            // 进程ID号
            int pid = runningAppProcessInfo.pid;
            // 用户ID
            int uid = runningAppProcessInfo.uid;
            // 进程名
            String processName = runningAppProcessInfo.processName;
            // 占用的内存
            int[] pids = new int[] {pid};
            Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(pids);
            int memorySize = memoryInfo[0].dalvikPrivateDirty;

            System.out.println("processName="+processName+",pid="+pid+",uid="+uid+",memorySize="+memorySize+"kb");
        }
    }
}
