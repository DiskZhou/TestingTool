package com.appcenter.testingtool.model;

import android.net.TrafficStats;

/**
 * Created by diskzhou on 13-8-12.
 */
public class NetworkInfo {

    public static long getReceiveDataByte(){
        return TrafficStats.getTotalRxBytes();
    }

    public static long getSendDataByte(){
        return TrafficStats.getTotalTxBytes();
    }

    public static long getReceiveDataByte(int uid){
        return TrafficStats.getUidRxBytes(uid);
    }
    public static long getSendDataByte(int uid){
        return TrafficStats.getUidTxBytes(uid);
    }
}
