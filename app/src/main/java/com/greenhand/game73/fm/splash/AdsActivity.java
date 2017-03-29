package com.greenhand.game73.fm.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.greenhand.game73.R;
import com.greenhand.game73.fm.home.MainActivity;
import com.greenhand.game73.utils.PrefUtils;

import java.util.Timer;
import java.util.TimerTask;

public class AdsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int TIMER = 1;
    private static final String APP_START_COUNT = "count";//应用启动次数统计的key
    private TextView txtTime;
    private Button btnSkip;
    private int t = 5;//广告起始时间
    private int count;//应用启动的次数
    private SharedPreferences preferences;
    private Timer timer;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (t < 0) {
                //是否该开启向导页：如果用户每启动app5次，我们就开启一次介绍页,否则，直接主界面
                if (count == 1 || count % 5 == 0) {
                    //跳到GuideActivity
                    goActivity(GuideActivity.class);
                } else {
                    //由广告也直接跳转到主页
                    goActivity(MainActivity.class);
                }
            } else if (msg.what == TIMER) {

                txtTime.setText("" + t);
                t--;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ads);
        //获取并存储启动次数
        doPrefs();
        initView();
        //开启广告计时
        startTime();
    }

    /**
     * 决定什么时候加载向导页
     */
    private void doPrefs() {

        //取数据
        count = PrefUtils.getInt(this, APP_START_COUNT, 0);
        Toast.makeText(this, "你的应用层序被使用了：" + ++count + "次", Toast.LENGTH_SHORT).show();
        //存数据
        PrefUtils.putInt(this, APP_START_COUNT, count);
    }

    private void initView() {

        txtTime = (TextView) findViewById(R.id.txt_time);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(this);
    }

    private void startTime() {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //每隔一秒发一条空消息
                mHandler.sendEmptyMessage(TIMER);
            }
        }, 0, 1000);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_skip:
                if (count == 1 || count % 5 == 0) {
                    goActivity(GuideActivity.class);
                } else {
                    goActivity(MainActivity.class);
                }
                break;
        }
    }

    private void goActivity(Class<?> cls) {

        Intent intent = new Intent(this, cls);
        startActivity(intent);
        //结束当前activty
        finish();
        //取消定时器
        timer.cancel();
    }

}
