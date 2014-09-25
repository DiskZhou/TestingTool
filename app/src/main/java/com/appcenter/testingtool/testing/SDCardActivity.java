package com.appcenter.testingtool.testing;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appcenter.testingtool.R;
import com.appcenter.testingtool.util.ConstString;
import com.appcenter.testingtool.util.FileUtils;
import com.appcenter.testingtool.util.SpaceManager;

/**
 * Created by diskzhou on 13-6-13.
 */
public class SDCardActivity extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private Button btn_fillSpace = null;
    private Button btn_freeSpace = null;
    private EditText editText_filledNumber = null;
    private RadioGroup mRadioGroup = null;
    private RadioButton radioButton_custom = null;
    private RadioButton radioButton_full = null;
    private TextView spaceSize = null;
    private ProgressBar pb;
    private int processNumber = 0;

    private Boolean isCustomSize = true;
    public static final int ON_PROCESSING = 1;
    public static final int STOP_PROCESS = 0;
    public static final int START_PROCESS = 2;
    private static final int FINISH_PROCESS = 4;

    private static final long SIZE_OF_FILE = 20; //100MB


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcard);

        spaceSize = (TextView) findViewById(R.id.textView_freememory);

        btn_fillSpace = (Button) findViewById(R.id.button_fillspace);
        btn_freeSpace = (Button) findViewById(R.id.button_freespace);
        editText_filledNumber = (EditText) findViewById(R.id.editText_fillednumber);
        pb = (ProgressBar) this.findViewById(R.id.progressBar_fill);
        mRadioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
        radioButton_custom = (RadioButton) findViewById(R.id.radioButton_custom);
        radioButton_full = (RadioButton) findViewById(R.id.radioButton_fullfill);
        radioButton_custom.setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(this);

        pb.setVisibility(ProgressBar.INVISIBLE);
        btn_fillSpace.setOnClickListener(this);
        btn_freeSpace.setOnClickListener(this);
        setFreeSpaceTextView();

    }

    @Override
    public void onClick(View view) {
        if (view.equals(btn_fillSpace)) {
            processNumber = 0;
            Message m = new Message();
            m.what = START_PROCESS;
            hd.sendMessage(m);
            new Thread() {
                @Override
                public void run() {
                    fillDisk();
                }
            }.start();

        } else if (view.equals(btn_freeSpace)) {
            new Thread() {
                @Override
                public void run() {
                    SpaceManager.clearAllFiles();
                    Message m = new Message();
                    m.what = STOP_PROCESS;
                    hd.sendMessage(m);
                }
            }.start();
        }

    }

    private Handler hd = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case START_PROCESS:
                    Toast.makeText(SDCardActivity.this, "填充开始", Toast.LENGTH_SHORT).show();
                    pb.setVisibility(ProgressBar.VISIBLE);
                    pb.setMax(100);
                    pb.setProgress(0);
                    break;
                case ON_PROCESSING:
                    editText_filledNumber.setVisibility(EditText.INVISIBLE);
                    btn_fillSpace.setVisibility(Button.INVISIBLE);
                    setFreeSpaceTextView();
                    pb.setProgress(processNumber);
                    break;
                case FINISH_PROCESS:
                    editText_filledNumber.setVisibility(EditText.VISIBLE);
                    btn_fillSpace.setVisibility(Button.VISIBLE);
                    break;
                case STOP_PROCESS:
                    Toast.makeText(SDCardActivity.this, "清理结束", Toast.LENGTH_SHORT).show();
                    pb.setVisibility(ProgressBar.INVISIBLE);
                    break;
            }
        }
    };

    private void setFreeSpaceTextView() {
        spaceSize.setText(String.format("%d MB", SpaceManager.getSDCardFreeSpaceSize()));
    }

    private void sendMessage(int what) {
        Message m = new Message();
        m.what = what;
        hd.sendMessage(m);
    }

    private void fillDisk() {

        long size = getFilledSpaceSize();
        long totalSize = size;
        while (size > 0) {
            long writeSize = SIZE_OF_FILE < size ? SIZE_OF_FILE : size;
            Double percent;
            FileUtils.createDustFiles(ConstString.APP_FILE_PATH, writeSize, "bin");
            size = size - writeSize;
            percent = (double) ((totalSize - size)) / (double) (totalSize) * 100;

            processNumber = percent.intValue();

            sendMessage(ON_PROCESSING);

        }
        sendMessage(FINISH_PROCESS);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if (i == radioButton_custom.getId()) {
            radioButton_full.setChecked(false);
            editText_filledNumber.setVisibility(EditText.VISIBLE);

            isCustomSize = true;

        } else {
            radioButton_custom.setChecked(false);
            editText_filledNumber.setVisibility(EditText.INVISIBLE);
            isCustomSize = false;

        }

    }

    private long getFilledSpaceSize() {
        long spaceFreeSize = SpaceManager.getSDCardFreeSpaceSize();
        long filedSize = 0;

        if (isCustomSize) {
            String fillNumber = String.valueOf(editText_filledNumber.getText());
            if (fillNumber.length() > 0) {
                filedSize = Long.parseLong(fillNumber) <= spaceFreeSize ? Long.parseLong(fillNumber) : spaceFreeSize;
            }
        } else {
            filedSize = spaceFreeSize;
        }

        return filedSize;
    }
}
