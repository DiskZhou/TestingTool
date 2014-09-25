package com.appcenter.testingtool.util;

import java.text.DecimalFormat;

/**
 * Created by diskzhou on 13-8-14.
 */
public class DataFormator {


    public static long ConvertB2b(long size){
        return size;
    }
    public static String formatSize(long size) {
        String suffix = "b";
        float fSize=0;

        if (size >= 1024) {
            suffix = "KB";
            fSize=size / 1024;
            if (fSize >= 1024) {
                suffix = "MB";
                fSize /= 1024;
            }
            if (fSize >= 1024) {
                suffix = "GB";
                fSize /= 1024;
            }
        } else {
            fSize = size;
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
        if (suffix != null)
            resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

}
