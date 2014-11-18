package com.appcenter.testingtool.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by diskzhou on 14/11/18.
 */
public class StorageUtil {


    public static void SaveContent(Context context,String key,String value){
        SharedPreferences settings = context.getSharedPreferences("TestInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key,value);
        editor.commit();
    }


    public  static String ReadContent(Context context,String key){

        SharedPreferences settings = context.getSharedPreferences("TestInfo", 0);
        return settings.getString(key,"");

    }
}
