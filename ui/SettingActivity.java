package com.example.administrator.phonesefe.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.base.BaseActivity;

/**
 * Created by Administrator on 2016/12/30.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {
    private ImageView iv4, iv5;
    private ToggleButton iv, iv2, iv3;
    private NotificationManager mNManager;
    private Notification.Builder mBuilder;
    private Notification notify;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initActionBar(true, false, "设置", this);

        initUI();
    }
    /**
     * 初始化
     */
    private void initUI() {
        iv = (ToggleButton) findViewById(R.id.activity_setting_iv1);
        iv2 = (ToggleButton) findViewById(R.id.activity_setting_iv2);
        iv3 = (ToggleButton) findViewById(R.id.activity_setting_iv3);
        iv4 = (ImageView) findViewById(R.id.activity_setting_iv4);
        iv5 = (ImageView) findViewById(R.id.activity_setting_iv5);
        //监听事件
        iv.setOnCheckedChangeListener(this);
        iv2.setOnCheckedChangeListener(this);
        iv3.setOnCheckedChangeListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionbar_back_iv:
                finish();
                break;
            case R.id.activity_setting_iv4:
                break;
            case R.id.activity_setting_iv5:
                startActivity(WelcomeActivity.class);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.activity_setting_iv1:
                if (isChecked) {
                    iv.setBackgroundResource(R.drawable.togglebutton_off);
                }  else {
                    iv.setBackgroundResource(R.drawable.togglebutton_on);
                }
                break;
            case R.id.activity_setting_iv2:
                if (isChecked) {
                    iv2.setBackgroundResource(R.drawable.togglebutton_on);
                }  else {
                    iv2.setBackgroundResource(R.drawable.togglebutton_off);
                }
                break;
            case R.id.activity_setting_iv3:
                //打开开关，发送通知
                if (isChecked) {
                    iv3.setBackgroundResource(R.drawable.togglebutton_on);
                    //获得mNmanger对象
                    mNManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
                    //创建builder对象
                    mBuilder = new Notification.Builder(this);
                    //设置PendingIntent
                    Intent intent = new Intent(this, HomeActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                    //设置标题
                    mBuilder.setContentTitle("明天不爱上班")
                            .setContentText("明天放假不用上班")//设置内容
                            .setSubText("--记住我是谁！")//设置内容下面的一小段文字
                            .setTicker("收到老大的信息")//设置通知栏显示的文字
                            .setWhen(System.currentTimeMillis())//设置通知的时间
                            .setSmallIcon(R.drawable.icon_audio)//设置小图标
                            .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_software))//设置大图标
                            .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                            .setAutoCancel(true)//设置点击后取消
                            .setContentIntent(pendingIntent);//设置PendingIntent
                    notify = mBuilder.build();
                    mNManager.notify(0x10, notify);
                } else {
                    iv3.setBackgroundResource(R.drawable.togglebutton_off);
                    //关闭开关，取消通知
                    mNManager.cancel(0x10);//取消Notification
                }
                break;
        }
    }
}
