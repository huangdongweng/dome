package com.example.administrator.phonesefe.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.base.BaseActivity;
import com.example.administrator.phonesefe.biz.MemeryManager;
import com.example.administrator.phonesefe.biz.ProcessManager;
import com.example.administrator.phonesefe.view.HomeView;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.angle;


public class HomeActivity extends BaseActivity implements View.OnClickListener{
    private ImageView telnumder;
    private ImageView software;
    private ImageView clean;
    private ImageView rocket;
    private ImageView phone;
    private ImageView filemgr;
    private HomeView circle_sv;
    private ImageView circle_iv;
    private TextView circle_tv;
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //初始化actionbar
        initActionBar(false,true,"手机管家",this);
        //初始化UI
        init();
    }
        /***初始化*/
    private void init() {
        telnumder= (ImageView) findViewById(R.id.home_telnumder_item_iv);
        software= (ImageView) findViewById(R.id.home_software_item_iv);
        clean= (ImageView) findViewById(R.id.home_clean_item_iv);
        rocket= (ImageView) findViewById(R.id.home_rocket_item_iv);
        phone= (ImageView) findViewById(R.id.home_phone_item_iv);
        filemgr= (ImageView) findViewById(R.id.home_filemgr_item_iv);
        circle_sv= (HomeView) findViewById(R.id.activity_home_circle_sv);
        circle_iv= (ImageView) findViewById(R.id.activity_home_circle_iv);
        circle_tv= (TextView) findViewById(R.id.activity_home_circle_tv);
        //设置监听事件
        telnumder.setOnClickListener(this);
        software.setOnClickListener(this);
        clean.setOnClickListener(this);
        phone.setOnClickListener(this);
        rocket.setOnClickListener(this);
        phone.setOnClickListener(this);
        filemgr.setOnClickListener(this);

//        initCircle();
        circle_iv.setOnClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        initCircle();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    /**初始化圆环数据*/
    private void initCircle() {
        circle_iv.setClickable(false);
        long total = MemeryManager.getAllMemeray(this);
        long free = MemeryManager.getAvalMemeray(this);
        //百分比
        final int progress = (int)((total-free)*100f/total);
        //角度
        int angle = (int)((progress/100f)*360);
        final Timer timer  = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (count <= progress){
                    count++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            circle_tv.setText(count+"%");
                        }
                    });
                }else {
                    count = 0;
                    timer.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            circle_iv.setClickable(true);
                        }
                    });
                }
            }
        };
        timer.schedule(task,400,30);
        circle_sv.setAngle(angle);
    }
    /**点击时调用的方法*/
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.actionbar_back_iv:
                finish();
                break;
            case R.id.actionbar_memu_iv:
                startActivity(SettingActivity.class);
                break;
            case R.id.home_telnumder_item_iv :
                //跳转到电话助手
                startActivity(TalClassListActivity.class);
                break;
            case R.id.home_software_item_iv :
                //软件信息
                startActivity(SoftwareActivity.class);
                break;
            case R.id.home_clean_item_iv :
                //手机清理
                startActivity(PhoneCleanActivity.class);
                break;
            case R.id.home_rocket_item_iv :
                //手机加速
                startActivity(RocketActivity.class);
                break;
            case R.id.home_phone_item_iv:
                //手机状态
                startActivity(PhoneActivity.class);
                break;
            case R.id.home_filemgr_item_iv :
                startActivity(FileActivity.class);
                //文件管理
                break;
            case R.id.activity_home_circle_iv:
                ProcessManager.killRunningProcess(this);
                initCircle();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
