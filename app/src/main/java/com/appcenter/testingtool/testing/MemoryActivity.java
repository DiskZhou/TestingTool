package com.appcenter.testingtool.testing;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appcenter.testingtool.R;
import com.appcenter.testingtool.util.TaoLog;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by diskzhou on 13-6-13.
 */
public class MemoryActivity extends Activity implements View.OnClickListener {

    private Button btn_decrease = null;
    private Button btn_stop_increase = null;
    private Button btn_force_fc =null;
    private TextView textView_free_memory = null;
    private static final int STOP_DECREASE = 1;
    private static final int DO_DECREASE = 2;
    private ArrayList<byte[]> mArrayList = new ArrayList<byte[]>();
    private ArrayList<byte[]> mArrayList1 = new ArrayList<byte[]>();
    private static boolean breakTag = true;

    private static final String TAG = "MemoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        btn_decrease = (Button) findViewById(R.id.btn_decrease);
        btn_stop_increase = (Button) findViewById(R.id.btn_stop_decrease);
        textView_free_memory = (TextView) findViewById(R.id.textView_free_memory);
        btn_decrease.setOnClickListener(this);
        btn_stop_increase.setOnClickListener(this);
        btn_force_fc = (Button) findViewById(R.id.btn_gc_memory);
        btn_force_fc.setOnClickListener(this);
        setText();

    }


    @Override
    public void onClick(View view) {
        if (view == btn_decrease) {

            breakTag = true;
            new Thread() {
                @Override
                public void run() {
                    reduceAllHeapsMemory();
                }
            }.start();

        } else if (view == btn_stop_increase) {

            breakTag = false;
            mArrayList.clear();
            TaoLog.Logi(TAG, "BreakTag After Click:" + breakTag);
            sendMessage(STOP_DECREASE);

        }else if (view==btn_force_fc){
            System.gc();
            TaoLog.Logi(TAG, "GC is invoked");
        }

    }

    public int getHeapSize() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return am.getLargeMemoryClass() - getMyMemorySize();

        } else {
            return am.getMemoryClass() - getMyMemorySize();

        }
    }

    public int getFreeMemorySize() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return (int) (mi.availMem / (1024 * 1024));
    }

    private byte[] readFile() {
        byte[] buffer = null;
        InputStream in = getResources().openRawResource(R.raw.text1);
        try {
            int length = in.available();
            buffer = new byte[length];
            in.read(buffer);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return buffer;
        }
    }

    private int getAvaliableMemorySize() {
        int heapSize;
        heapSize = getHeapSize() - getMyMemorySize();
        int freeMemorySize = getFreeMemorySize();
        return freeMemorySize >= heapSize ? heapSize : freeMemorySize;

    }

    private void reduceAllHeapsMemory() {


        int maxSize = getAvaliableMemorySize();
        int before = 0;

        while (maxSize > 1 && breakTag) {
            TaoLog.Logi(TAG, "BreakTag:" + breakTag + "MaxSize:" + maxSize);
            maxSize = getAvaliableMemorySize();
            mArrayList.add(readFile());
            if (maxSize == before) {
                //break;
            } else {
                before = maxSize;
            }
            sendMessage(DO_DECREASE);
        }
        ;


    }

    private Handler hd = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int what = message.what;
            switch (what) {
                case DO_DECREASE:
                    setText();
                    break;
                case STOP_DECREASE:
                    setText();
                    break;
                default:
                    break;
            }
        }
    };

    private void sendMessage(int what) {
        Message m = new Message();
        m.what = what;
        hd.sendMessage(m);
    }

    private void setText() {
        textView_free_memory.setText(String.format("FreeMemory:%d  HeapSize:%d", getFreeMemorySize(), getAvaliableMemorySize()));
    }


    private int getMyMemorySize() {
        return 0;
        //(int) Runtime.getRuntime().totalMemory() / (1024 * 1024);
    }
}
