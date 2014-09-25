package com.appcenter.testingtool.testing;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appcenter.testingtool.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

/**
 * Created by diskzhou on 14-6-18.
 */
public class NetworkMontor extends Activity {

    TextView network_log=null;
    private final ContentValues contentValues=new ContentValues();
    private final String yq = System.getProperty("line.separator");
    private TelephonyManager mTelephonyManager;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_monitor_activity);
        network_log = (TextView) findViewById(R.id.edit_network_check_log);
        Button btn_network_check = (Button) findViewById(R.id.btn_network_check);
        btn_network_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

    }


    private void updateTextView(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                network_log.append(str);
            }
        });
    }

    public void check() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ping("rj.m.taobao.com");
                updateTextView(getDNSInfo());
                updateTextView(getClientIpInfo());
            }
        }).start();
    }


    private String ping(String url)
    {
        StringBuilder localStringBuilder;
        localStringBuilder = new StringBuilder();
        try
        {
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(String.format("ping -c 5 %s", url)).getInputStream()));
            String str1;
            while (true)
            {
                str1 = localBufferedReader.readLine();
                if(str1==null){
                    break;
                }
                localStringBuilder.append(str1);
                updateTextView(str1);

            }
        }
        catch (IOException localIOException)
        {
            return "Get CDN ip failed!" + this.yq;
        }catch (Exception e){
           e.printStackTrace();
        }

        synchronized (this.contentValues)
        {
            this.contentValues.put("cdnInfo", localStringBuilder.toString());
            return localStringBuilder.toString();
        }
    }

    private final void getGprsStatus()
    {
        int i = this.mTelephonyManager.getDataState();
        String str = "unknown";
        switch (i)
        {
            default:
            case 2:
                str = "connected";
                break;
            case 1:
                str = "connecting";
                break;
            case 0:
                str = "disconnected";
                break;
            case 3:
                str = "suspended";
                break;
        }
        synchronized (this.contentValues)
        { this.contentValues.put("gprsState", str);
        }


    }


    private String getDNSInfo()
    {
        StringBuilder localStringBuilder;
        BufferedReader localBufferedReader = null;
        try
        {
            localStringBuilder = new StringBuilder();
             localBufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop").getInputStream()));
            while (true)
            {
                String str1 = localBufferedReader.readLine();
                if (str1 == null)
                    break;
                StringTokenizer localStringTokenizer = new StringTokenizer(str1, ":");
                if (TextUtils.indexOf(localStringTokenizer.nextToken(), "dns") <= -1)
                    continue;
                String str2 = localStringTokenizer.nextToken();
                if ((TextUtils.isEmpty(str2)) || (TextUtils.isEmpty(str2.replaceAll("[ \\[\\]]", ""))))
                    continue;
                localStringBuilder.append(str1 + this.yq);
            }

        }catch (IOException localIOException)
        {
            return "Get DNS ip address failed!" + this.yq;
        }finally {
            try {
                if (localBufferedReader != null) {
                    localBufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        synchronized (this.contentValues)
        {
            this.contentValues.put("dnsInfo", localStringBuilder.toString());
            String str3 = localStringBuilder.toString();
            return str3;
        }
    }

    private void getWifiStatus()
    {
        WifiInfo localWifiInfo = ((WifiManager)getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
        synchronized (this.contentValues)
        {
            this.contentValues.put("linkSpeed", String.valueOf(localWifiInfo.getLinkSpeed()) + " Mbps");
            this.contentValues.put("rssi", String.valueOf(localWifiInfo.getRssi()));
            this.contentValues.put("ssid", localWifiInfo.getSSID());
            this.contentValues.put("macAddr", localWifiInfo.getMacAddress());
        }
    }


    public static String getClientIpInfo() {
        final String strUrl="http://iframe.ip138.com/ic.asp";

        try {
            URL url = new URL(strUrl);
            BufferedReader br = new BufferedReader(new InputStreamReader(url
                    .openStream()));
            String s = "";
            StringBuffer sb = new StringBuffer("");



            String webContent = "";
            while ((s = br.readLine()) != null) {

                sb.append(s + "\r\n");
            }
            br.close();

            webContent = sb.toString();

            int start = webContent.indexOf("[")+1;

            int end = webContent.indexOf("]");

            webContent = webContent.substring(start,end);



            return webContent;
        } catch (Exception e) {

            e.printStackTrace();

            return "error open url:" + strUrl;
        }

    }

}