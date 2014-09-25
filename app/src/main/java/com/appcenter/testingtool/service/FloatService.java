package com.appcenter.testingtool.service;


import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.appcenter.testingtool.R;
import com.appcenter.testingtool.model.CpuInfo;
import com.appcenter.testingtool.model.MemoryInfo;
import com.appcenter.testingtool.model.MyApplication;
import com.appcenter.testingtool.model.NetworkInfo;
import com.appcenter.testingtool.util.DataFormator;
import com.appcenter.testingtool.util.TaoLog;


import java.util.Timer;
import java.util.TimerTask;

public class FloatService extends Service {
    private static final String TAG = "FloatService";
    private final Timer timer = new Timer();
    private WindowManager wm = null;
    private WindowManager.LayoutParams wmParams = null;
    private MemoryInfo memoryInfo;
    private View view;
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    private int state;

    private TextView textview_memoryUsed;
    private TextView textview_memoryTotal;
    private TextView textview_networkReciveSpeed;
    private TextView textview_networkSendSpeed;
    private TextView textview_cpuInfo;

    private float StartX;
    private float StartY;
    private final int DELAY_TIME = 1000;
    private long oldReceiveBytes;
    private long oldSendBytes;

    //Message类型
    private final int UPDATE_MEMORY = 0;
    private final int UPDATE_CPUINFO = 1;
    private final int UPDATE_NETWORSPEED_SEND = 2;
    private final int UPDATE_NETWORSPEED_RECEIVE = 3;

    //更新参数
    private long networkReceiveSpeed;
    private long networkSendSpeed;
    private String cpuUsage;
    private long memoryUsage;
    private String heapSize;
    //时间间隔
    long timeStart_receive;
    long timeStart_send;


    @Override
    public void onCreate() {
        TaoLog.Logd(TAG, "onCreate");

        //初始化界面
        initView();


        //初始化界面数据
        init();

        //初始化Float
        createView();

        timer.schedule(timerTask, DELAY_TIME, DELAY_TIME);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TaoLog.Logd("TAG", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {

        memoryInfo = new MemoryInfo(FloatService.this);
        heapSize = String.valueOf(memoryInfo.getHeapSize())+"MB";

        updateTotoMemory((memoryInfo.getTotalMemorySize()));

        oldReceiveBytes = NetworkInfo.getReceiveDataByte();
        timeStart_receive = System.currentTimeMillis();

        oldSendBytes = NetworkInfo.getSendDataByte();
        timeStart_send= System.currentTimeMillis();

    }

    private void initView() {

        view = LayoutInflater.from(this).inflate(R.layout.floatview, null);
        textview_memoryUsed = (TextView) view.findViewById(R.id.memunused);
        textview_memoryTotal = (TextView) view.findViewById(R.id.memtotal);
        textview_networkReciveSpeed = (TextView) view.findViewById(R.id.receivespeed);
        textview_networkSendSpeed = (TextView) view.findViewById(R.id.sendspeed);
        textview_cpuInfo = (TextView) view.findViewById(R.id.cpuinfo);
        Button btn_gc = (Button) view.findViewById(R.id.btn_gc);
        btn_gc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.gc();
                TaoLog.Logi(TAG,"GC is invoded.");
            }
        });

        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent serviceStop = new Intent();
                        serviceStop.setClass(FloatService.this, FloatService.class);
                        stopService(serviceStop);
            }
        });
        updateReceiveSpeed(0);
        updateSendSpeed(0);
    }

    private void updateTotoMemory(long totalMemory) {
        textview_memoryTotal.setText("总体内存:" + DataFormator.formatSize(totalMemory));
    }

    private void updateReceiveSpeed(long receiveSpeed) {
        textview_networkReciveSpeed.setText("接收速度:" + DataFormator.formatSize(DataFormator.ConvertB2b(receiveSpeed)) + "/秒");
    }

    private void updateSendSpeed(long sendSpeed) {
        textview_networkSendSpeed.setText("发送速度:" + DataFormator.formatSize(DataFormator.ConvertB2b(sendSpeed)) + "/秒");
    }

    private void updateCpuUsage() {
        textview_memoryUsed.setText("内存占用:" + DataFormator.formatSize(memoryUsage)+"/"+heapSize);
        textview_cpuInfo.setText("CPU占用:" + cpuUsage);
    }

    private void createView() {
        SharedPreferences shared = getSharedPreferences("float_flag",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("float", 1);
        editor.commit();
        // 获取WindowManager
        wm = (WindowManager) getApplicationContext().getSystemService("window");
        // 设置LayoutParams(全局变量）相关参数
        wmParams = ((MyApplication) getApplication()).getMywmParams();
        wmParams.type = 2002;
        wmParams.flags |= 8;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角
        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 0;
        // 设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.format = 1;
        wm.addView(view, wmParams);

        view.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // 获取相对屏幕的坐标，即以屏幕左上角为原点
                x = event.getRawX();
                y = event.getRawY() - 25; // 25是系统状态栏的高度
                TaoLog.Logi("currP", "currX" + x + "====currY" + y);// 调试信息
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        state = MotionEvent.ACTION_DOWN;
                        StartX = x;
                        StartY = y;
                        // 获取相对View的坐标，即以此View左上角为原点
                        mTouchStartX = event.getX();
                        mTouchStartY = event.getY();
                        TaoLog.Logi("startP", "startX" + mTouchStartX + "====startY"
                                + mTouchStartY);// 调试信息
                        break;
                    case MotionEvent.ACTION_MOVE:
                        state = MotionEvent.ACTION_MOVE;
                        updateViewPosition();
                        break;

                    case MotionEvent.ACTION_UP:
                        state = MotionEvent.ACTION_UP;

                        updateViewPosition();
                        mTouchStartX = mTouchStartY = 0;
                        break;
                }
                return true;
            }
        });


    }



    //刷新界面数据
    private Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_CPUINFO:
                    updateCpuUsage();
                    break;
                case UPDATE_MEMORY:
                    break;
                case UPDATE_NETWORSPEED_RECEIVE:
                    updateReceiveSpeed(networkReceiveSpeed);
                    break;
                case  UPDATE_NETWORSPEED_SEND:
                    updateSendSpeed(networkSendSpeed);
                    break;
            }
            if (wm != null && view != null) {
                wm.updateViewLayout(view, wmParams);

            }

        }
    };


    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            new Thread() {

                @Override
                public void run() {
                    doSendSpeedoRefresh();
                }
            }.start();
            new Thread() {

                @Override
                public void run() {
                    doReceiveSpeedRefresh();
                }
            }.start();
            new Thread() {

                @Override
                public void run() {
                    doCpuInfoRefresh();
                }
            }.start();
        }

    };

    public void doCpuInfoRefresh() {

        cpuUsage = new CpuInfo().getCpuUsageByName(((MyApplication) getApplication()).getMonitorProcessName());
        memoryUsage = memoryInfo.getProcessMemorySize(((MyApplication) getApplication()).getMonitorProcessName());

        sendMessage(UPDATE_CPUINFO);
    }

    public void doSendSpeedoRefresh() {
        long newSendByte = NetworkInfo.getSendDataByte();
        long time_end = System.currentTimeMillis();
        networkSendSpeed = 0;
        networkSendSpeed = (newSendByte - oldSendBytes)*1000/(time_end-timeStart_send);
        timeStart_send=time_end;
        oldSendBytes = newSendByte;
        sendMessage(UPDATE_NETWORSPEED_SEND);
        TaoLog.Logi("NetWorkSendSpeed", String.valueOf(networkSendSpeed));


    }

    public void doReceiveSpeedRefresh(){
        long newReceiveByte = NetworkInfo.getReceiveDataByte();
        long time_end =System.currentTimeMillis();
        networkReceiveSpeed = 0;
        networkReceiveSpeed = (newReceiveByte - oldReceiveBytes)*1000/(time_end-timeStart_receive);
        timeStart_receive=time_end;
        oldReceiveBytes = newReceiveByte;
        sendMessage(UPDATE_NETWORSPEED_RECEIVE);
        TaoLog.Logi("NetWorkReceiveSpeed", String.valueOf(networkReceiveSpeed));
    }


    private void sendMessage(int what) {
        Message m = new Message();
        m.what = what;
        if (hd != null) {
            hd.sendMessage(m);
        }
    }


    private void updateViewPosition() {
        // 更新浮动窗口位置参数
        wmParams.x = (int) (x - mTouchStartX);
        wmParams.y = (int) (y - mTouchStartY);
        wm.updateViewLayout(view, wmParams);
    }


    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        hd = null;

        TaoLog.Logd("FloatService", "onDestroy");
        wm.removeViewImmediate(view);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
