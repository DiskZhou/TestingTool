package com.appcenter.testingtool.testing;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.appcenter.testingtool.R;
import com.appcenter.testingtool.util.ConstString;
import com.appcenter.testingtool.util.FileUtils;

import com.appcenter.testingtool.util.MediaScaner;
import com.appcenter.testingtool.util.TaoLog;
import com.mpatric.mp3agic.ID3Wrapper;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v23Tag;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;


import java.io.IOException;
import java.io.InputStream;


/**
 * Created by diskzhou on 13-8-26.
 */
public class TestDataActivcity extends Activity {

    public static final String TAG = "TestDataActivcity";
    private static String IMG_PATH;
    private static String Music_PATH;
    private static String VIDEO_PATH;
    private static String PHOTO;

    private static final int UPDATE_CPOY_IMG = 0;
    private static final int UPDATE_CPOY_MUSIC = 1;
    private static final int UPDATE_CPOY_VIDEO = 2;
    private static final int UPDATE_START = 99;
    private static final int UPDATE_STOP = 100;

    private static final int CLEAR_ALL_DATA = 1000;
    private int copy_img_count = 0;
    private int copy_video_count = 0;
    private int copy_music_count = 0;

    private Button btn_copymusic;
    private Button btn_copyimg;
    private Button btn_copyvideo;
    private Button btn_bigvideo;
    private Button btn_bigIMG;
    private Button btn_bigMusic;


    private ProgressBar pb;
    private TextView textView_copy_desc;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testdata_activity);

        IMG_PATH = FileUtils.CreateFolder(FileUtils.MakePath(ConstString.APP_FILE_PATH, "IMG"));
        Music_PATH = FileUtils.CreateFolder(FileUtils.MakePath(ConstString.APP_FILE_PATH, "MUSIC"));
        VIDEO_PATH = FileUtils.CreateFolder(FileUtils.MakePath(ConstString.APP_FILE_PATH, "VIDEO"));

        pb = (ProgressBar) findViewById(R.id.progressBar_copyfile);
        pb.setVisibility(View.INVISIBLE);
        pb.setMax(1000);

        textView_copy_desc = (TextView) findViewById(R.id.tv_copy_desc);

        btn_copyimg = (Button) findViewById(R.id.btn_copy1000_pic);
        btn_copyimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {

                    @Override
                    public void run() {
                        copy1000IMG();

                    }
                }.start();
            }
        });

        btn_copymusic = (Button) findViewById(R.id.btn_copy1000_music);
        btn_copymusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {

                    @Override
                    public void run() {
                        copy1000Music();

                    }
                }.start();
            }
        });

        btn_copyvideo = (Button) findViewById(R.id.btn_copy1000_video);
        btn_copyvideo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {

                    @Override
                    public void run() {
                        copy1000Video();

                    }
                }.start();
            }
        });


        btn_bigvideo = (Button) findViewById(R.id.btn_big_video);
        btn_bigvideo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        CreateBigVideo();
                    }
                }.start();
            }
        });

        btn_bigIMG = (Button) findViewById(R.id.btn_big_pic);
        btn_bigIMG.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        CreateBigImg();
                    }
                }.start();
            }
        });

        btn_bigMusic = (Button) findViewById(R.id.btn_big_music);
        btn_bigMusic.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        CreateBigMusic();
                    }
                }.start();
            }
        });

        Button btn_special_music = (Button) findViewById(R.id.btn_special_music);
        btn_special_music.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {

                    @Override
                    public void run() {
                        createSecialMusic();
                    }
                }.start();
            }
        });


        Button btn_special_video = (Button) findViewById(R.id.btn_special_video);
        btn_special_video.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {

                    @Override
                    public void run() {
                      createSpecialVideo();
                    }
                }.start();
            }
        });
        Button btn_fake_music= (Button) findViewById(R.id.btn_fake_music);
        btn_fake_music.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {

                    @Override
                    public void run() {
                        createFakeMusic();
                    }
                }.start();
            }
        });

        Button btn_fake_pic= (Button) findViewById(R.id.btn_fake_pic);
        btn_fake_pic.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {

                    @Override
                    public void run() {
                        createFakePic();
                    }
                }.start();
            }
        });
        Button btn_fake_video= (Button) findViewById(R.id.btn_fake_video);
        btn_fake_video.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {

                    @Override
                    public void run() {
                        createFakeVideo();
                    }
                }.start();
            }
        });

        Button btn_super_file= (Button) findViewById(R.id.btn_spuer_file);
        btn_super_file.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {

                    @Override
                    public void run() {
                        createSpuerFile();
                    }
                }.start();
            }
        });
    }


    private Handler hd = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_START:
                    textView_copy_desc.setVisibility(View.INVISIBLE);
                    pb.setVisibility(View.VISIBLE);
                    pb.setProgress(0);
                    btn_copyvideo.setClickable(false);
                    btn_copymusic.setClickable(false);
                    btn_copyimg.setClickable(false);
                    btn_bigIMG.setClickable(false);
                    btn_bigMusic.setClickable(false);
                    btn_bigvideo.setClickable(false);
                    break;
                case UPDATE_STOP:
                    btn_copyvideo.setClickable(true);
                    btn_copymusic.setClickable(true);
                    btn_copyimg.setClickable(true);
                    btn_bigIMG.setClickable(true);
                    btn_bigMusic.setClickable(true);
                    btn_bigvideo.setClickable(true);
                    pb.setVisibility(View.INVISIBLE);
                    textView_copy_desc.setVisibility(View.VISIBLE);
                    textView_copy_desc.setText("创建完成,请查看相应文件.");
                    sendMountedSdcardBroadcast();
                    break;
                case UPDATE_CPOY_IMG:
                    pb.setProgress(copy_img_count);
                    break;
                case UPDATE_CPOY_MUSIC:
                    pb.setProgress(copy_music_count);
                    break;
                case UPDATE_CPOY_VIDEO:
                    pb.setProgress(copy_video_count);
                    break;
                case CLEAR_ALL_DATA:
                    Toast.makeText(TestDataActivcity.this, "All files have been cleared", Toast.LENGTH_LONG).show();
                    break;

            }
            super.handleMessage(msg);
        }
    };


    private void copy1000Music() {
        sendMessage(UPDATE_START);
        for (int i = 0; i < 1000; i++) {
            InputStream in = getResources().openRawResource(R.raw.musicexample2);
            FileUtils.CopyFiles(in, FileUtils.MakePath(Music_PATH, FileUtils.CreateRandomFilesName("mp3")));
            copy_music_count = i;
            sendMessage(UPDATE_CPOY_MUSIC);
        }
        sendMessage(UPDATE_STOP);
    }


    private void copy1000IMG() {
        sendMessage(UPDATE_START);
        for (int i = 0; i < 1000; i++) {
            InputStream in = getResources().openRawResource(R.raw.cover00);
            FileUtils.CopyFiles(in, FileUtils.MakePath(IMG_PATH, FileUtils.CreateRandomFilesName("jpg")));
            copy_img_count = i;
            sendMessage(UPDATE_CPOY_IMG);
        }
        sendMessage(UPDATE_STOP);
    }

    private void copy1000Video() {
        sendMessage(UPDATE_START);

        for (int i = 0; i < 1000; i++) {
            InputStream in = getResources().openRawResource(R.raw.videoexample);
            FileUtils.CopyFiles(in, FileUtils.MakePath(VIDEO_PATH, FileUtils.CreateRandomFilesName("m4v")));
            copy_video_count = i;
            sendMessage(UPDATE_CPOY_VIDEO);
        }
        sendMessage(UPDATE_STOP);
    }

    private void CreateBigVideo() {
        sendMessage(UPDATE_START);
        for (int i = 0; i < 1000; i++) {
            InputStream in = getResources().openRawResource(R.raw.videoexample);
            FileUtils.CopyFiles(in, FileUtils.MakePath(VIDEO_PATH, "AAAAAAAAAAAAAAAAABBigVideo.m4v"));
            copy_video_count = i;
            sendMessage(UPDATE_CPOY_VIDEO);
        }
        sendMessage(UPDATE_STOP);
    }

    private void CreateBigImg() {
        sendMessage(UPDATE_START);
        for (int i = 0; i < 1000; i++) {
            InputStream in = getResources().openRawResource(R.raw.picexample);
            FileUtils.CopyFiles(in, FileUtils.MakePath(IMG_PATH, "AAAAAAAAAAAAAAAAABigIMG.jpg"));
            copy_img_count = i;
            sendMessage(UPDATE_CPOY_IMG);
        }
        sendMessage(UPDATE_STOP);
    }

    private void CreateBigMusic() {
        sendMessage(UPDATE_START);
        for (int i = 0; i < 1000; i++) {
            InputStream in = getResources().openRawResource(R.raw.musicexample);
            FileUtils.CopyFiles(in, FileUtils.MakePath(Music_PATH, "AAAAAAAAAAAAAAAAABBigMusic.wav"));
            copy_music_count = i;
            sendMessage(UPDATE_CPOY_MUSIC);
        }
        sendMessage(UPDATE_STOP);
    }

    private void sendMessage(int what) {
        Message m = new Message();
        m.what = what;
        hd.sendMessage(m);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        menu.add(0, 0, 0, "清除测试文件");
        //menu.add(0,1,1,"退出");
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()) {
            case 0:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        clearAllTestFiles();
                    }
                }.start();
                break;
            case 1:
                this.finish();
        }

        return true;
    }

    private void sendMountedSdcardBroadcast() {
        new MediaScaner(getApplicationContext()).scanFolder(ConstString.APP_FILE_PATH);
        TaoLog.Loge(TAG, "sendMountedSdcardBroadcast");
    }

    private void clearAllTestFiles() {
        FileUtils.RemoveAllFiles(IMG_PATH, Music_PATH, VIDEO_PATH);
        new MediaScaner(getApplicationContext()).mountSDCard();
        sendMessage(CLEAR_ALL_DATA);
    }


    private void createSpecialVideo(){
        pb.setMax(1000);
        sendMessage(UPDATE_START);

        String[] arrays = getResources().getStringArray(R.array.symbol);
        pb.setMax(arrays.length*100);
        int x= 0;
        for (String symbol:arrays){
            for (int i = 0; i < 100; i++) {
                InputStream in = getResources().openRawResource(R.raw.videoexample);
                FileUtils.CopyFiles(in, FileUtils.MakePath(VIDEO_PATH, symbol+".m4v"));
                x++;
                copy_video_count = x;
                sendMessage(UPDATE_CPOY_VIDEO);
            }

        }
        sendMessage(UPDATE_STOP);

    }
    private void createSecialMusic() {
        String mp3TempPath = FileUtils.MakePath(Music_PATH, "temp.mp3");
        String picTempPath = FileUtils.MakePath(IMG_PATH,"temp.jpg");
        FileUtils.InputstreamToFile(getResources().openRawResource(R.raw.musicexample2),mp3TempPath);
        FileUtils.InputstreamToFile(getResources().openRawResource(R.raw.cover00),picTempPath);
        Mp3File mp3File = null;

        String[] arrays = getResources().getStringArray(R.array.artist);
        for(String artist:arrays){
            try {
                mp3File = new Mp3File(mp3TempPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ID3Wrapper newId3Wrapper = new ID3Wrapper(new ID3v1Tag(), new ID3v23Tag());
            newId3Wrapper.setArtist(artist);

            try {

                if (mp3File != null) {
                    mp3File.setId3v1Tag(newId3Wrapper.getId3v1Tag());
                    mp3File.setId3v2Tag(newId3Wrapper.getId3v2Tag());
                    mp3File.save(FileUtils.MakePath(Music_PATH, FileUtils.CreateRandomFilesName("mp3")));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotSupportedException e) {
                e.printStackTrace();
            }
        }

        sendMessage(UPDATE_STOP);
    }


    private void createFakeMusic(){
        for (int i=0;i<5;i++){
            FileUtils.createDustFiles(Music_PATH,10,"mp3");
        }
        sendMessage(UPDATE_STOP);

    }
    private void createFakePic(){
        for (int i=0;i<5;i++){
            FileUtils.createDustFiles(IMG_PATH,10,"jpg");
        }
        sendMessage(UPDATE_STOP);

    }
    private void createFakeVideo(){
        for (int i=0;i<5;i++){
            FileUtils.createDustFiles(VIDEO_PATH,10,"m4v");
        }
        sendMessage(UPDATE_STOP);

    }
    private void createSpuerFile(){

        FileUtils.createDustFiles(VIDEO_PATH,1200,"m4v");
        sendMessage(UPDATE_STOP);

    }

}

