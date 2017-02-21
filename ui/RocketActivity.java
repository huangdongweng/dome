package com.example.administrator.phonesefe.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.adapter.RocketAdapter;
import com.example.administrator.phonesefe.base.BaseActivity;
import com.example.administrator.phonesefe.biz.MemeryManager;
import com.example.administrator.phonesefe.biz.ProcessManager;
import com.example.administrator.phonesefe.entity.Rocketinfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 */
public class RocketActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_name;
    private TextView tv_version;
    private ProgressBar pb_dosage;
    private TextView tv_dosage;
    private TextView tv_system;
    private TextView tv_user;
    private ProgressBar pb;
    private ListView lv_rockete;
    private Button but_deleck;
    private RocketAdapter adapter;
    private List<Rocketinfo> data = new ArrayList<>();
    /**
     * 是否显示用户进程
     */
    private ActivityManager am;
    private Handler hander = new Handler() {
        //处理子线程发送的消息
        @Override
        public void handleMessage(Message msg) {
            Log.i("tag", "======" + msg);
            super.handleMessage(msg);
            //隐藏
            pb.setVisibility(View.GONE);
            //显示
            lv_rockete.setVisibility(View.VISIBLE);
            if (msg != null&& msg.what==0x10) {
                adapter.setData(checkAdapterData(data, true));
                adapter.notifyDataSetChanged();
                //有数据后给按钮添加监听事件
                tv_user.setOnClickListener(RocketActivity.this);
                tv_system.setOnClickListener(RocketActivity.this);
                but_deleck.setOnClickListener(RocketActivity.this);
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rockete);
        initActionBar(true, false, "手机加速", this);
        //初始化
        initUI();
        //添加数据
        initData();
    }
    /**
     * 添加数据
     */
    private void initData() {
        am= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        //获取设备名称
        final String name = Build.BOARD;
        tv_name.setText(name);
        //系统版本号
        String version = Build.VERSION.RELEASE;
        tv_version.setText(version);
        //全部运行内存
        long total = MemeryManager.getAllMemeray(this);
        //乘余内存
        long avail = MemeryManager.getAvalMemeray(this);
        //格式化空间
        String f_total= Formatter.formatFileSize(this, total);
        String f_free = Formatter.formatFileSize(this,avail);
        //计算可用空间的进度
        int progress = (int) ((avail * 100) / total);
        pb_dosage.setProgress(progress);
        tv_dosage.setText("可用空间:"+f_free+"/"+f_total);
        //创建适配器
        adapter = new RocketAdapter(this);
        //添加适配器
        lv_rockete.setAdapter(adapter);
        //进程数据
        getRunningProcessData(0x10);
    }
    /**
     * 进程数据
     */
    private void getRunningProcessData(final int msgWhat) {
        //清空数组
        data.clear();
        //显示
        pb.setVisibility(View.VISIBLE);
        //隐藏
        lv_rockete.setVisibility(View.INVISIBLE);
        //开子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取第三方进程(所有的进程），存入一个临时的集合里
                List<ActivityManager.RunningAppProcessInfo> list = ProcessManager.getRunningProcess(RocketActivity.this);
                Rocketinfo rocketinfo = null;
                //遍历临时的集合
                for (ActivityManager.RunningAppProcessInfo info : list) {
                    try {
                        //获取进程包名
                        ApplicationInfo appinfo = getPackageManager().getApplicationInfo(info.processName, PackageManager.GET_META_DATA |
                                PackageManager.GET_SHARED_LIBRARY_FILES | PackageManager.MATCH_UNINSTALLED_PACKAGES);
                        //判断是否为用户进程
                        if ((appinfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                            rocketinfo = new Rocketinfo(true, 1, info); //当数字为1时是用户进程
                        } else {
                            rocketinfo = new Rocketinfo(false, 2, info);//当数字为2时是系统进程
                        }
                    } catch (PackageManager.NameNotFoundException e) {//当有异常时走的方法
                        //结束本次循环，继续执行下一次循环
                        continue;
                    }
                    //给集合添加数据
                    data.add(rocketinfo);
                }
                //子线程不能更新UI，发送数据给主线程
                Message msg = hander.obtainMessage();
                msg.what = msgWhat;
                msg.obj = data;
                hander.sendMessage(msg);
            }
        }).start();
    }
    /**
     * 初始化
     */
    private void initUI() {
        tv_name = (TextView) findViewById(R.id.activity_rocket_name_tv);
        tv_version = (TextView) findViewById(R.id.activity_rocket_version_tv);
        pb_dosage = (ProgressBar) findViewById(R.id.activity_rocket_dosage_pb);
        tv_dosage = (TextView) findViewById(R.id.activity_rocket_dosage_tv);
        tv_system = (TextView) findViewById(R.id.activity_rocket_system_tv);
        tv_user = (TextView) findViewById(R.id.activity_rocket_user_tv);
        lv_rockete = (ListView) findViewById(R.id.activity_rocket_lv);
        pb = (ProgressBar) findViewById(R.id.adapter_rocket_item_pb);
        but_deleck = (Button) findViewById(R.id.activity_rocket_deleck_but);
    }
    /**判断显示用户进程还是系统进程*/
    private List<Rocketinfo> checkAdapterData(List<Rocketinfo> data, boolean isShowUser) {
        List<Rocketinfo> temp = new ArrayList<>();
        for (Rocketinfo info : data) {
            if (isShowUser) {
                if (info.getTag() == 1) {
                    temp.add(info);
                }
            } else {
                if (info.getTag() == 2) {
                    temp.add(info);
                }
            }
        }
        return temp;
    }
    /**点击时调用的方法*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionbar_back_iv:
                //关闭页面
                finish();
                break;
            case R.id.activity_rocket_user_tv:
                //清除数据
                adapter.getData().clear();
                //设置用户进程
                adapter.setData(checkAdapterData(data,true));
                //通知系统重新适配
                adapter.notifyDataSetChanged();
                break;
            case R.id.activity_rocket_system_tv:
                adapter.getData().clear();
                adapter.setData(checkAdapterData(data,false));
                adapter.notifyDataSetChanged();
                break;
            case R.id.activity_rocket_deleck_but:

                List<Rocketinfo> temp=new ArrayList<>();
                //遍历集合
                for (Rocketinfo info:adapter.getData()){
                    if (info.ischeck()&&(info.getTag()==1)){
                        am.killBackgroundProcesses(info.getInfo().processName);
                        temp.add(info);
                    }
                }
                adapter.getData().removeAll(temp);
                adapter.notifyDataSetChanged();
                getRunningProcessData(0);
                break;
        }

    }
}
