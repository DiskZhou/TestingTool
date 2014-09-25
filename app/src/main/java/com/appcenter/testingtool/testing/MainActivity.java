package com.appcenter.testingtool.testing;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.appcenter.testingtool.R;
import com.appcenter.testingtool.util.ConstString;
import com.appcenter.testingtool.util.FileUtils;


public class MainActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rl_memory = null;
    private RelativeLayout rl_sdcard = null;
    private RelativeLayout rl_network = null;
    private RelativeLayout rl_testdata = null;
    private RelativeLayout rl_switchenvironment = null;
    private RelativeLayout rl_inetent_test = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        rl_memory = (RelativeLayout) findViewById(R.id.rl_memory);
        rl_sdcard = (RelativeLayout) findViewById(R.id.rl_sdcard);
        rl_network = (RelativeLayout) findViewById(R.id.rl_network);
        rl_testdata = (RelativeLayout) findViewById(R.id.rl_testdata);
        rl_switchenvironment = (RelativeLayout) findViewById(R.id.rl_swtichevnironment);
        rl_inetent_test = (RelativeLayout) findViewById(R.id.rl_intents);

        rl_memory.setOnClickListener(this);
        rl_sdcard.setOnClickListener(this);
        rl_network.setOnClickListener(this);
        rl_testdata.setOnClickListener(this);
        rl_switchenvironment.setOnClickListener(this);
        rl_inetent_test.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        menu.add(0, 0, 0, "关于");
        menu.add(0, 1, 1, "退出");
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()) {
            case 0:
                Toast.makeText(MainActivity.this, "Test About", Toast.LENGTH_LONG).show();
                break;
            case 1:
                this.finish();
        }

        return true;
    }


    @Override
    public void onClick(View view) {
        if (view.equals(rl_memory)) {
            Intent intent = new Intent(this, MemoryActivity.class);
            startActivity(intent);
        } else if (view.equals(rl_sdcard)) {
            Intent intent = new Intent(this, SDCardActivity.class);
            startActivity(intent);
        } else if (view.equals(rl_network)) {
            Intent intent = new Intent(this, MonitorFloatActivity.class);
            startActivity(intent);
        } else if (view.equals(rl_testdata)) {
            Intent intent = new Intent(this, TestDataActivcity.class);
            startActivity(intent);
        } else if (view.equals(rl_switchenvironment)) {
            Intent intent = new Intent(this, EnvrionmentSwitchActivity.class);
            startActivity(intent);
        } else if (view.equals(rl_inetent_test)) {
            Intent intent = new Intent(this, IntentTestActivity.class);
            startActivity(intent);
        }

    }


    private void init() {
        FileUtils.CreateFolder(ConstString.APP_FILE_PATH);
    }
}
