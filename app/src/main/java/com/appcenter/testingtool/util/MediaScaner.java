package com.appcenter.testingtool.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by diskzhou on 13-9-11.
 */
public class MediaScaner {

    private Context context;

    public MediaScaner(Context context) {
        this.context = context;
    }


    public void scanFile(String file) {
        Uri data = Uri.parse("file://" + file);
        TaoLog.Logd("TAG", "file:" + file);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }

    public void mountSDCard() {
        Uri data = Uri.parse("file://"+ Environment.getExternalStorageDirectory());

        TaoLog.Logd("TAG", "mountSDCard:" );
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, data));
    }

    public void scanFolder(String path) {
        File file = new File(path);

        if (file.isDirectory()) {
            File[] array = file.listFiles();

            for (File f : array) {

                if (f.isFile()) {//FILE TYPE
                    String name = f.getName();
                    if (name.contains(".mp3") || name.contains("jpg") || name.contains("m4v")) {
                        scanFile(f.getAbsolutePath());
                    }
                } else {//FOLDER TYPE
                    scanFolder(f.getAbsolutePath());
                }
            }
        }
    }
}
