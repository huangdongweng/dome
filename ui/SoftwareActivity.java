package com.example.administrator.phonesefe.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.base.BaseActivity;
import com.example.administrator.phonesefe.view.SectorView;

import java.io.File;

/**
 * Created by Administrator on 2016/12/20.
 */

public class SoftwareActivity extends BaseActivity implements View.OnClickListener{
    //声明
    private ProgressBar storage_pb;
    private TextView storage_tv;
    private RelativeLayout all;
    private RelativeLayout system;
    private RelativeLayout user;
    private SectorView sv_storage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software);
        initActionBar(true,false,"软件信息",this);
        //初始化
        initUI();
        //
        initData();
    }
    private void initData() {
        //获取外部存储文件
        File file=Environment.getExternalStorageDirectory();
        //获取外部存储的根路径
//        String path = Environment.getExternalStorageDirectory().getPath();
        //获取外部存储的总的大小
        long total=file.getTotalSpace();
        //获取可用空间
        long free=file.getFreeSpace();
        //计算可用空间的进度
        int progress=(int)((free*100)/total);
        //格式化空间
        String f_total= Formatter.formatFileSize(this, total);
        String f_free = Formatter.formatFileSize(this,free);
        //计算角度
        int angle =(int)((total-free)*360/total);
        //设置进度
        storage_pb.setProgress(progress);
        //设置可用空间
        storage_tv.setText("可用空间:"+f_free+"/"+f_total);
        sv_storage.drawAnim(0,angle);
    }
    /**初始化*/
    private void initUI() {
        storage_pb= (ProgressBar) findViewById(R.id.activity_storage_pb);
        storage_tv= (TextView) findViewById(R.id.activity_storage_tv);
        all= (RelativeLayout) findViewById(R.id.activity_software_all_rl);
        system= (RelativeLayout) findViewById(R.id.activity_software_system_rl);
        user= (RelativeLayout) findViewById(R.id.activity_software_user_rl);
        sv_storage= (SectorView) findViewById(R.id.includ_software_sv);
        //添加监听事件
        all.setOnClickListener(this);
        system.setOnClickListener(this);
        user.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        Bundle bunder=null;
        switch (view.getId()){
            case R.id.actionbar_back_iv:
                //关闭页面
                finish();
                break;
            case R.id.activity_software_all_rl:
                bunder=new Bundle();
                bunder.putString("softType","all");
                startActivity(SoftwareManagerActivity.class,bunder);
                break;
            case R.id.activity_software_system_rl:
                bunder=new Bundle();
                bunder.putString("softType","sys");
                startActivity(SoftwareManagerActivity.class,bunder);
                break;
            case R.id.activity_software_user_rl:
                bunder=new Bundle();
                bunder.putString("softType","user");
                startActivity(SoftwareManagerActivity.class,bunder);
                break;
        }
    }
}
