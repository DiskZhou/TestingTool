package com.appcenter.testingtool.util;


import android.util.Log;
/**
 *
 * @author kangyong.lt
 *
 *
 */
public final class TaoLog {

    private static boolean isPrintLog = true;

    public static final String TAOBAO_TAG= "Taobao";
    public static final String ETAO_TAG= "Etao";
    public static final String ETAOLOCAL_TAG = "EtaoLocal";
    public static final String MEM_TRACE = "mem_Trace";
    public static final String IMGPOOL_TAG = "TaoSdk.ImgPool";
    public static final String PANELMANAGER_TAG = "PanelManager";
    public final static String ETAO_APIURL_TAG = "etao_apiurl";
    public static final String APICONNECT_TAG = "TaoSdk.ApiRequest";
    public static final String STARTUTCASE_TAG = "TaoSdk.StartUT";
    public static final String ENDCASE_TAG = "TaoSdk.EndUT";
    public static final String SIGN_TAG = "tag_sign";

    public static boolean getLogStatus(){
        return isPrintLog;
    }

    public static void setLogSwitcher(boolean open){
        isPrintLog = open;
    }

    public static void Logd(String tag,String msg)
    {
        if(tag != null && msg != null)
        {
            if(isPrintLog)
            {
                Log.d(tag, msg);
            }
        }

        return ;
    }

    public static void Loge(String tag,String msg)
    {

        if(tag != null && msg != null)
        {
            if(isPrintLog)
            {
                Log.e(tag, msg);
            }
        }

        return ;
    }

    public static void Logi(String tag,String msg)
    {
        if(tag != null && msg != null)
        {
            if(isPrintLog)
            {
                Log.i(tag, msg);
            }
        }

        return ;
    }

    public static void Logv(String tag,String msg)
    {
        if(tag != null && msg != null)
        {
            if(isPrintLog)
            {
                Log.v(tag, msg);
            }
        }

        return ;
    }

    public static void Logw(String tag,String msg)
    {
        if(tag != null && msg != null)
        {
            if(isPrintLog)
            {
                Log.w(tag, msg);
            }
        }

        return ;
    }


}
