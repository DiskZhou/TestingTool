package com.appcenter.testingtool.testing;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appcenter.testingtool.R;
import com.appcenter.testingtool.util.TaoLog;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by diskzhou on 14/9/25.
 */
public class FileScanActivity extends Activity{

    private  final  int UPDATE_PATH= 0x001;
    private final int SCAN_FILE_DONE=0x002;
    private final int START_FILE_SCAN=0x003;
    Button btn_file_scan = null;
    TextView file_scan_log = null;

    private  long startTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.file_scanner);

         btn_file_scan = (Button) findViewById(R.id.btn_file_scan);
         file_scan_log = (TextView) findViewById(R.id.file_scan_log);

        btn_file_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = hd.obtainMessage();
                msg.what=START_FILE_SCAN;
                hd.sendMessage(msg);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> fileList=ScanFiles("/sdcard");
                        Message msg = hd.obtainMessage();
                        msg.what=SCAN_FILE_DONE;
                        hd.sendMessage(msg);
                        TaoLog.Logi("FileScan",fileList.toString());
                    }
                }).start();
            }
        });
    }


    Handler hd = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_PATH:
                    file_scan_log.setText(msg.obj.toString());
                    break;
                case SCAN_FILE_DONE:
                    String log = String.format("total:%d",System.currentTimeMillis()-startTime);
                    file_scan_log.setText(log);
                    break;
                case START_FILE_SCAN:
                    startTime = System.currentTimeMillis();
                    break;
            }
        }
    };

    public ArrayList<String> ScanFiles(String path){

        ArrayList<String> fileList = new ArrayList<String>();

        File file = new File(path);

        if (file.isDirectory()&&file.getTotalSpace()>=1000){

            for (File f: file.listFiles()){
                fileList.addAll(ScanFiles(f.getAbsolutePath()));
            }

        }else{
            Message msg = hd.obtainMessage();
            msg.what=UPDATE_PATH;
            msg.obj=file.getAbsolutePath();
            hd.sendMessage(msg);
            fileList.add(file.getAbsolutePath());

        }
        return fileList;
    }
}
