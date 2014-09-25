package com.appcenter.testingtool.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by diskzhou on 13-6-11.
 */
public class SpaceManager {




    /**
     *
     */
    public static  void clearAllFiles() {
        String filePath;
        filePath = ConstString.APP_FILE_PATH.endsWith(File.separator) ? ConstString.APP_FILE_PATH : ConstString.APP_FILE_PATH + File.separator;
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                }
            }
        }


    }

    /**
     *
     * @return
     */

    public static long getSDCardFreeSpaceSize() {
        long spaceSize = 0;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();;
            long availCount = sf.getAvailableBlocks();
            spaceSize = blockSize * availCount / (1024 * 1024);
        }
        return spaceSize;
    }



}
