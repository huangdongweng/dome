package com.example.administrator.phonesefe.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.adapter.SoftManagerAdapter;
import com.example.administrator.phonesefe.base.BaseActivity;
import com.example.administrator.phonesefe.entity.SoftWareInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */
public class SoftwareManagerActivity extends BaseActivity implements View.OnClickListener{
    private String softType;
    private ListView lv;
    private ProgressBar pb;
    private List<SoftWareInfo> data=new ArrayList<>();
    private SoftManagerAdapter adapter;
    private Button deleteall;
    private CheckBox checkall;
    private BroadcastReceiver receiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_softmanager);
        initActionBar(true,false,"软件管理",this);
        //取上个页面的值
        Intent intent=getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        softType=bundle.getString("softType");
        //初始化
        initUI();
        //异步加载数据
        asyncLoadData();
        //初始化广播
        initReceiver();
    }
    /**初始化广播*/
    private void initReceiver() {
        receiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                
                asyncLoadData();
            }
        };
        //注册广播
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addDataScheme("package");
        registerReceiver(receiver,filter);
    }

    /**异步加载数据*/
    private void asyncLoadData() {
        lv.setVisibility(View.INVISIBLE);//隐藏
        pb.setVisibility(View.VISIBLE);//显示
        data.clear();//清空数据源所有的数据
        //开线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取应用程序信息
                //获取设备上的所有的应用详情
                List<ApplicationInfo> applicationinfo=getPackageManager().
                        getInstalledApplications(PackageManager.
                                MATCH_UNINSTALLED_PACKAGES);
                for (ApplicationInfo info:applicationinfo){
                    //获取应用名
                    String label = (String) getPackageManager().getApplicationLabel(info);
                    //判断是否为系统应用
                    boolean isSystem =true;
                    if ((info.flags & ApplicationInfo.FLAG_SYSTEM)>0){
                        isSystem=true;
                    }else {
                        isSystem=false;
                    }
                    //包名
                    String packageName =info.packageName;
                    //版本号
                    String version="";
                    try {
                        version =getPackageManager().getPackageInfo(packageName,0).versionName;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    //图标
                    Drawable icon=info.loadIcon(getPackageManager());
                    //创建实体类对象
                    SoftWareInfo soft=new SoftWareInfo(label,icon,packageName,version,false,!isSystem);
                    //添加到集合
                    if (softType.equals("sys")){//存系统应用
                        if (isSystem){
                            data.add(soft);
                        }
                    }else if (softType.equals("user")){
                        if (!isSystem){
                            data.add(soft);
                        }
                    }else {
                        data.add(soft);
                    }
                }
                //子线程不能更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //写主线程执行操作
                        pb.setVisibility(View.GONE);
                        lv.setVisibility(View.VISIBLE);
                        //创建适配器
                        adapter=new SoftManagerAdapter(SoftwareManagerActivity.this);
                        //添加适配器
                        adapter.setData(data);
                        lv.setAdapter(adapter);
                        //这个适配器调用了通知设置数据改变的方法
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
    /**初始化*/
    private void initUI() {
        lv= (ListView) findViewById(R.id.activity_softmanager_lv);
        pb= (ProgressBar) findViewById(R.id.activity_softmanager_pb);
        checkall= (CheckBox) findViewById(R.id.activity_softmanager_checkall_cb);
        deleteall= (Button) findViewById(R.id.activity_softmanager_deleteall_but);
        if (softType.equals("sys")){
            checkall.setChecked(false);//按钮点击的状态
            checkall.setClickable(false);//设置按钮是否能被点击
            deleteall.setClickable(false);
        }else {
            //添加监听事件
            checkall.setOnClickListener(this);
            deleteall.setOnClickListener(this);
        }
    }
    /**添加监听事件*/
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.actionbar_back_iv:
               //关闭页面
                finish();
                break;
            case R.id.activity_softmanager_checkall_cb:
                //遍历数据源
                for (SoftWareInfo info:adapter.getData()){
                    if (info.isDelete()){
                        //数据源中的按钮要与全选的按钮状态要一致
                        info.setCheak(checkall.isChecked());
                    }
                }
                //通知数据源数据发生变化，请从新适配
                adapter.notifyDataSetChanged();
                break;
            case R.id.activity_softmanager_deleteall_but:
                //遍历数据源
                for (SoftWareInfo info:adapter.getData()){
                    //从数据源中拿出被选中的APP
                    if (info.isCheak()){
                        Intent intent=new Intent();
                        intent.setAction(Intent.ACTION_DELETE);//删除数据
                        //御载应用
                        intent.setData(Uri.parse("package:"+info.getPackageName()));
                        startActivity(intent);
                    }
                }
                //通知适配器重新适配
                adapter.notifyDataSetChanged();
//                asyncLoadData();
                break;
        }
    }
    /**解除注册广播*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
