package com.example.administrator.phonesefe.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.base.BaseActivity;
import com.example.administrator.phonesefe.biz.MemeryManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Administrator on 2016/12/19.
 */
public class PhoneActivity extends BaseActivity implements View.OnClickListener {
    private ProgressBar cell;
    private TextView dosage;
    private TextView version_tv1, version_tv2;
    private TextView cpu_tv1, cpu_tv2;
    private TextView space_tv1, space_tv2;
    private TextView camera_tv1, camera_tv2;
    private TextView root_tv1, root_tv2;
    private int temperature;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initActionBar(true, false, "手机信息", this);
        //初始化
        initUI();
        //获得数据
        initData();
        initBattery();
        //点击电池吐司电量
        cell.setOnClickListener(this);
    }
    /**
     * 初始化
     */
    private void initUI() {
        cell = (ProgressBar) findViewById(R.id.activity_phone_cell_pb);
        dosage = (TextView) findViewById(R.id.activity_phone_dosage_tv);

        version_tv1 = (TextView) findViewById(R.id.activity_phone_item_version_tv1);
        version_tv2 = (TextView) findViewById(R.id.activity_phone_item_version_tv2);
        cpu_tv1 = (TextView) findViewById(R.id.activity_phone_item_cpu_tv1);
        cpu_tv2 = (TextView) findViewById(R.id.activity_phone_item_cpu_tv2);
        space_tv1 = (TextView) findViewById(R.id.activity_phone_item_space_tv1);
        space_tv2 = (TextView) findViewById(R.id.activity_phone_item_space_tv2);
        camera_tv1 = (TextView) findViewById(R.id.activity_phone_item_camera_tv1);
        camera_tv2 = (TextView) findViewById(R.id.activity_phone_item_camera_tv2);
        root_tv1 = (TextView) findViewById(R.id.activity_phone_item_root_tv1);
        root_tv2 = (TextView) findViewById(R.id.activity_phone_item_root_tv2);
    }
    /**
     * 初始化电池电量
     */
    private void initBattery() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                    //获取电池当前的电量
                    int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                    //获取电池总电量
                    int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
                    //获取电池的温度
                    temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
                    Toast.makeText(context, temperature + "", Toast.LENGTH_LONG).show();
                    //计算电量的百分比
                    int progress = (level * 100) / scale;
                    //设置当前电量
                    cell.setProgress(progress);
                    //设置显示电量
                    dosage.setText(progress + "%");
                }
            }
        };
        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);
    }
    /**
     * 获得数据
     */
    private void initData() {

        //获取设备名称
        String name = Build.BOARD;
        version_tv1.setText("设备名称: " + name);
        //系统版本号
        String version = Build.VERSION.RELEASE;
        version_tv2.setText("系统版本号: " + version);
        //cpu型号
        String cpu = getCPUtype();
        cpu_tv1.setText("cpu型号: " + cpu);
        //获取CPU的核心数
        int num = getCPUnumber();
        cpu_tv2.setText("CPU的核心数: " + num);
        //全部运行内存
        long all = MemeryManager.getAllMemeray(this);
        space_tv1.setText("全部运行内存: " + Formatter.formatFileSize(this, all));
        //乘余内存
        long aval = MemeryManager.getAvalMemeray(this);
        space_tv2.setText("乘余内存: " + Formatter.formatFileSize(this, aval));
        //屏幕分辨率
        String screenRes = getScreenResolution();
        camera_tv1.setText("屏幕分辨率:"+screenRes);
        //相机分辨率
        String cameraRes = getCameraResolution();
        camera_tv2.setText("相机分辨率:"+cameraRes);
        //系统基带版本
        String base_version = Build.VERSION.CODENAME;
        root_tv1.setText("系统基带版本: " + base_version);
        //是否Root
        boolean isRoot = checkRoot();
        root_tv2.setText("是否Root: " + isRoot);
    }
    /**
     * 是否Root
     */
    private boolean checkRoot() {
        String path = "/system/bin/su";
        String path1 = "/systemx/bin/su";
        boolean isRoot = false;
        if (new File(path).exists() || new File(path1).exists()) {
            isRoot = true;
        } else {
            isRoot = false;
        }
        return false;
    }
    /**
     * 添加监听事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionbar_back_iv:
                finish();
                break;
            case R.id.activity_phone_cell_pb:
                Toast.makeText(this, temperature + "", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    /**
     * 获取CPU的型号
     */
    public String getCPUtype() {
        //要读的文件
        String path = "/proc/cpuinfo";
        String str = "";
        BufferedReader br = null;
        try {
            //开流读取//缓冲流
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
            while ((str = br.readLine()) != null) {
                if (str.contains("model name")) {
                    return str.split(":")[1];
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * 获取CPU的核心数
     */
    public int getCPUnumber() {
        //要读的文件
        String path = "/proc/cpuinfo";
        String str = "";
        int count = 0;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
            while ((str = br.readLine()) != null) {
                if (str.contains("processor")) {
                    count++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    /**
     * 获取屏幕分辨率
     */
    public String getScreenResolution() {
        //获取窗口的管理器对象
        WindowManager wm = getWindowManager();
        //获的屏幕对象
        Display display = wm.getDefaultDisplay();
        //创建Point对象，用来接收屏幕的大小信息
        Point point = new Point();
        //获取大小信息，并且保存在point对象中
        display.getSize(point);
        return point.y + " * " + point.x;
    }

    /**
     * 获取相机的最大分辨率
     */
    public String getCameraResolution() {
        String maxSize = "";
        //打开相机
        Camera camera = Camera.open();
        //获取相机参数
        Camera.Parameters parameters = camera.getParameters();
        //返回相机所支持的所有的分辨率
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        //获取最大分辨率
        Camera.Size size = null;
        for (Camera.Size s : sizes) {
            if (size == null) {
                size = s;
            } else if (size.width * s.height < s.width * s.height) {
                size = s;
            }
        }
        //释放相机
        camera.release();
//        maxSize = size.height + "*"+size.width;
        return size.height + "*"+size.width;
    }
    /**
     * 关闭广播
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
