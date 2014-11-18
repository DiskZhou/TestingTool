package com.appcenter.testingtool.testing;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appcenter.testingtool.R;
import com.appcenter.testingtool.model.WifiProxyManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by diskzhou on 14/11/3.
 */
public class ProxyActivity extends Activity {


    private EditText et_proxy_ip=null;
    private EditText et_proxy_port=null;
    private TextView tv_proxy_info = null;
    private Button btn_proxy_customer = null;
    private Button btn_proxy_auto = null;
    private Button btn_proxy_clear = null;
    private static final String DISK_PROXY ="10.32.80.31";
    private static final int DEFAULT_PORT=8888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
        initUI();

    }





    private  void initUI(){
        et_proxy_ip = (EditText) findViewById(R.id.et_proxy_ip);
        et_proxy_port = (EditText) findViewById(R.id.et_proxy_port);
        tv_proxy_info = (TextView) findViewById(R.id.tv_proxy_info);
        btn_proxy_customer = (Button) findViewById(R.id.btn_proxy_customer);
        btn_proxy_auto = (Button) findViewById(R.id.btn_proxy_auto);
        btn_proxy_clear = (Button) findViewById(R.id.btn_proxy_clear);

        btn_proxy_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setProxy();
                    }
                }).start();
            }
        });

        btn_proxy_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setProxyAuto();
                    }
                }).start();
            }
        });
        btn_proxy_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        clearProxy();
                    }
                }).start();
            }
        });
    }


    private void checkSDKVersion(){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB_MR1||Build.VERSION.SDK_INT>20){
            btn_proxy_customer.setClickable(false);
            btn_proxy_auto.setClickable(false);
            btn_proxy_clear.setClickable(false);
            updateText("Your SDK version does not support this tool , if your have root you can try ProxyDroid");
        }
    }

    private void updateText(final String content){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null!=tv_proxy_info){
                    tv_proxy_info.append(content+"\n");
                }
            }
        });
    }


    private void setProxy(){
        String ip = et_proxy_ip.getText().toString();
        int port =Integer.parseInt(et_proxy_port.getText().toString());
        if (port<=0){
            port=DEFAULT_PORT;
        }
        if (ip!=""&&isIpv4(ip)){

            if(WifiProxyManager.Instance(this).setWifiProxySettings(ip,port)){
                updateText("Set Proxy Success, with "+ip);
            }else{
                updateText("set failed please try again");
            }
        }else{
            updateText("your enter a wrong ip.");
        }
    }

    private void clearProxy(){
        if(WifiProxyManager.Instance(this).unsetWifiProxySettings()){
            updateText("clear Proxy Success");
        }else{
            updateText("clear failed please try again");
        }

    }

    private void setProxyAuto(){

        if(WifiProxyManager.Instance(this).setWifiProxySettings(DISK_PROXY,DEFAULT_PORT)){
            updateText("Set Proxy Success, with "+DISK_PROXY);
        }else{
            updateText("set failed please try again");
        }

    }
    /**
     * 判断是否为合法IP
     * @return true or false
     */
    public static boolean isIpv4(String ipAddress) {
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }
}
