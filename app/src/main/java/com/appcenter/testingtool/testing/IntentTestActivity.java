package com.appcenter.testingtool.testing;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appcenter.testingtool.R;
import com.appcenter.testingtool.util.IntensTest;


/**
 * Created by diskzhou on 14-2-24.
 */
public class IntentTestActivity extends Activity implements View.OnClickListener {


    private Button btn_intent_search = null;
    private Button btn_intent_details = null;
    private Button btn_intent_game = null;
    private Button btn_intent_security = null;
    private Button btn_intent_nfc = null;
    private Button btn_intent_update=null;
    private Button btn_intent_broadcast=null;
    private Button btn_tac_go=null;
    private EditText edit_tac=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_test);

        edit_tac= (EditText) findViewById(R.id.edit_tac_test);
        edit_tac.setText("tac://");
        edit_tac.setSelection(edit_tac.getText().toString().length());
        btn_intent_search = (Button) findViewById(R.id.btn_intent_search);
        btn_intent_details = (Button) findViewById(R.id.btn_intent_details);
        btn_intent_game = (Button) findViewById(R.id.btn_intent_game);
        btn_intent_update = (Button) findViewById(R.id.btn_intent_update);
        btn_intent_nfc = (Button) findViewById(R.id.btn_intent_nfc);
        btn_intent_security = (Button) findViewById(R.id.btn_intent_security);

        btn_intent_broadcast = (Button) findViewById(R.id.btn_intent_update);
        btn_tac_go = (Button) findViewById(R.id.btn_go_tac);

        btn_intent_search.setOnClickListener(this);
        btn_intent_details.setOnClickListener(this);
        btn_intent_game.setOnClickListener(this);
        btn_intent_update.setOnClickListener(this);
        btn_intent_nfc.setOnClickListener(this);
        btn_intent_security.setOnClickListener(this);
        btn_tac_go.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();

        if (view.equals(btn_intent_game)) {
            intent.setAction(IntensTest.Action_Export_Game);
            startActivity(intent);   // 跳转GameTab
        } else if (view.equals(btn_intent_details)) {
            intent.setAction(IntensTest.Action_Export_Detail);
            startActivity(intent);   // 跳转应用详情
        } else if (view.equals(btn_intent_search)) {
            intent.setAction(IntensTest.Action_Export_Search);
            startActivity(intent);  // 跳转搜索
        }else if(view.equals(btn_intent_security)){
            intent.setAction(IntensTest.Action_Export_Security);
            startActivity(intent);
        }else if(view.equals(btn_intent_nfc)){
            intent.setAction("com.taobao.appcenter.home.AppCenterMainActivity");
            startActivity(intent);
        }else if(view.equals(btn_intent_update)){
            intent.setAction("com.taobao.appcenter.control.guide.GuideActivity");
            startActivity(intent);
        }else if(view.equals(btn_intent_broadcast)){
            intent.setAction(Intent.ACTION_PACKAGE_ADDED);
            intent.setPackage("com.appcenter.testingtool");
            Uri data = Uri.parse("com.taobao.taobao");
            intent.setData(data);
            this.sendBroadcast(intent);//发送广播
        }else if(view.equals(btn_tac_go)){
            goTac(edit_tac.getText().toString());
        }


    }

    private void goTac(String str){
        if (str.isEmpty()){
            return;
        }
        Intent intent= new Intent(Intent.ACTION_VIEW);
        Uri url = Uri.parse(str);
        intent.setData(url);
        try{
            startActivity(intent);
        }catch (ActivityNotFoundException exception){

        }

    }
}
