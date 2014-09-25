package com.appcenter.testingtool.testing;

import com.appcenter.testingtool.model.MyApplication;
import com.appcenter.testingtool.service.FloatService;
import com.appcenter.testingtool.R;
import com.appcenter.testingtool.util.TaoLog;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MonitorFloatActivity extends Activity {

    private static final String TAG = "MonitorFloatActivity";
    private static final int UPDATE_PROCESS_NAME = 0;


    //def the item on UI
    private Button btnstart = null;
    private Button btnstop = null;
    private Button btn_select_process;
    private TextView tv_description;
    private TextView monitor_process_name;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_network);
        monitor_process_name = (TextView) findViewById(R.id.tv_mon_process);

        btnstart = (Button) findViewById(R.id.btnstart);
        btnstart.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent service = new Intent();
                service.setClass(MonitorFloatActivity.this, FloatService.class);
                startService(service);
            }
        });


        btnstop = (Button) findViewById(R.id.btnstop);
        btnstop.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceStop = new Intent();
                serviceStop.setClass(MonitorFloatActivity.this, FloatService.class);
                stopService(serviceStop);
            }
        });


        btn_select_process = (Button) findViewById(R.id.btn_select_process);
        btn_select_process.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                createAlter();


            }
        });

        tv_description = (TextView) findViewById(R.id.tv);
        String str = new StringBuilder()
                .append("\n")
                .append("说明：").append("\n")
                .append("1.悬浮窗可随意移动").append("\n")
                .append("2.实时显示当前内存数据").append("\n")
                .append("3.上层数据表示可用内存值").append("\n")
                .append("4.下层数据表示总内存值").append("\n")
                .append("5.点击悬浮窗出现关闭小图标可直接关闭").append("\n").append("\n")

                .toString();
        tv_description.setText(str);
    }

    // 处理process name 的更新
    private Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_PROCESS_NAME:
                    monitor_process_name.setText(((MyApplication) getApplication()).getMonitorProcessName());
                    break;
            }
        }
    };


    /**
     * 获取Running的 processName
     *
     * @return
     */
    private List<String> getProcesses() {
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager
                .getRunningAppProcesses();
        List<String> processesName = new ArrayList<String>();

        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
            processesName.add(appProcessInfo.processName);

        }

        return processesName;
    }

    /**
     * 创建ProessName的弹出列表
     */
    private void createAlter() {
        final List<String> processName = getProcesses();
        if (processName != null) {
            new AlertDialog.Builder(this)
                    .setTitle("进程列表")
                    .setItems(processName.toArray(new String[processName.size()]), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((MyApplication) getApplication()).setMonitorProcessName(processName.toArray(new String[processName.size()])[which]);
                            ((MyApplication) getApplication()).setUid(getUidByProcessName(processName.toArray(new String[processName.size()])[which]));
                            sendMessage(UPDATE_PROCESS_NAME);

                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }

    }

    private void sendMessage(int what) {
        Message m = new Message();
        m.what = what;
        hd.sendMessage(m);
    }


    @Override
    protected void onStop() {
        super.onStop();
        TaoLog.Logv("stop", "stop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        TaoLog.Logv("restart", "restart");

    }

    private int getUidByProcessName(String processName){
        ActivityManager activityManager= (ActivityManager) MonitorFloatActivity.this
                .getSystemService(Context.ACTIVITY_SERVICE);

        // 通过调用ActivityManager的getRunningAppProcesses()方法获得系统里所有正在运行的进程
        List<ActivityManager.RunningAppProcessInfo> appProcessList = activityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {

            TaoLog.Logi(TAG,appProcessInfo.processName);

            if (appProcessInfo.processName.equalsIgnoreCase(processName)){
                TaoLog.Logi(TAG,String.valueOf(appProcessInfo.uid));
                return appProcessInfo.uid;


            }

        }
        //查无进程返回0
        return 0;
    }

}