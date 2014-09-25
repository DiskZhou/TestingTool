package com.appcenter.testingtool.testing;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.appcenter.testingtool.R;

import java.util.List;

/**
 * Created by diskzhou on 14-3-13.
 */
public class PackageManagerActivity extends Activity {
    private static String TAG="PACKAGE_MANAGER";

    public  List<PackageInfo> getAppList(){
        List<PackageInfo> appList;
        try {

            PackageManager pm = this.getPackageManager();


            Log.i("PM", "pm.getInstalledPackages");
            appList = pm != null ? pm.getInstalledPackages(PackageManager.GET_ACTIVITIES) : null;

            return appList;
        } catch (Exception e) {
            Log.i(TAG,e.getMessage());
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kindsoftest);
        findViewById(R.id.button_kill_pm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(;;){
                    new Thread(){

                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            super.run();
                            Log.i(TAG,"PM RUN ONE TIME");
                            getAppList();
                        }
                    };
                }
            }
        });
    }
}
