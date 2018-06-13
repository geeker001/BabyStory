package com.lofter.story.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lofter.story.R;
import com.lofter.story.view.LoadingDialog;


/**
 * PACKAGE_NAME：com.boya.enforcelaw.activity
 * DATE：2017/9/27 16:24
 * USER: xiantao.jiang
 * DESCRIBE:  APP启动页
 */

public class WelcomeActivity extends Activity {

    private final int MSG_WELCOME_SLEEP = 1;
    private final int THREAD_SLEEP_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //欢迎页隐藏标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        showDelay();
    }


    protected void showDelay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Thread.sleep(THREAD_SLEEP_TIME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                msg.what = MSG_WELCOME_SLEEP;
                mHandler.sendMessage(msg);
            }
        }).start();
    }


    private void gotoLoginPage() {
        Intent intent = new Intent();
        intent.setClass(this, StoryListActivity.class);
        startActivity(intent);
        finish();
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_WELCOME_SLEEP:
                    gotoLoginPage();
                    break;
            }
        }
    };

}
