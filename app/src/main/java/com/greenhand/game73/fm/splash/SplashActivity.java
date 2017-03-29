package com.greenhand.game73.fm.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.greenhand.game73.R;

/**
 * 欢迎界面
 */
public class SplashActivity extends AppCompatActivity {

    public final int MSG_FINISH_GUIDE_ACTIVITY = 500;

    public Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FINISH_GUIDE_ACTIVITY:
                    //跳转到AdsActivity，并结束当前的SplashActivity
                    Intent intent = new Intent(SplashActivity.this, AdsActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        // 停留3秒后发送消息，跳转到MainActivity
        mHandler.sendEmptyMessageDelayed(MSG_FINISH_GUIDE_ACTIVITY, 3000);
    }
}
