package com.appcenter.testingtool.testing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.appcenter.testingtool.R;
import com.appcenter.testingtool.util.CosineSimilarAlgorithm;


import java.util.List;

/**
 * Created by diskzhou on 13-10-8.
 */
public class EnvrionmentSwitchActivity extends Activity{

    private TextView tv_evnironment=null;


//
//   private void save(int what){
//       SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
//       SharedPreferences.Editor editor = sharedPref.edit();
//       editor.putInt("Envrionment", what);
//       editor.commit();
//   }
//
//    private int read(){
//        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
//        int defaultValue =0;
//        return sharedPref.getInt("Envrionment", defaultValue);
//    }
//
//    /**
//     * 创建ProessName的弹出列表
//     */
//    private void createAlter() {
//        final String[] arryEnvrionment =  getResources().getStringArray(R.array.envrionment);
//
//        if (arryEnvrionment != null) {
//            new AlertDialog.Builder(this)
//                    .setTitle("环境列表")
//                    .setItems(arryEnvrionment, new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            save(which);
//                        }
//                    })
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
////                    }).show();
////        }
//
//    }


    private void compareText(){
        String str1="ac";
        String str2="ab";
                String text= CosineSimilarAlgorithm.levenshtein(str1,str2);
        tv_evnironment.setText(text);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envrionment);
        Button btn_environment = (Button) findViewById(R.id.btn_switchenvironment);
        tv_evnironment = (TextView) findViewById(R.id.tv_environment);
        btn_environment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compareText();
            }
        });
    }
}
