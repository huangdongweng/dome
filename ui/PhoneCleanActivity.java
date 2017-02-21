package com.example.administrator.phonesefe.ui;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.adapter.PhoneCleanAdapter;
import com.example.administrator.phonesefe.base.BaseActivity;
import com.example.administrator.phonesefe.biz.FlieManager;
import com.example.administrator.phonesefe.biz.MemeryManager;
import com.example.administrator.phonesefe.entity.AppRubbish;
import com.example.administrator.phonesefe.db.DBManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/23.
 * handler作用:进行主线程和子线程之间的通信;发消息
 *Hander  Message MessageQueue Looper
 */
public class PhoneCleanActivity extends BaseActivity implements View.OnClickListener{
    private TextView allFileSize;
    private LinearLayout ll;
    private ListView lv;
    private PhoneCleanAdapter adapter;
    private ProgressBar pb;
    private long sizeAll;
    private Button delete;
    //handler作用:进行主线程和子线程之间的通信;发消息
    private Handler handler=new Handler(){
        //处理子线程发送的消息
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg!=null){
                if (msg.what==0x10){
                    //文件检索过程中处理的消息
                    sizeAll+=msg.arg1;
                    allFileSize.setText(Formatter.formatFileSize(PhoneCleanActivity.this,sizeAll));
                }
                if (msg.what==0x12){
                    //文件检索完毕号处理的消息
                    //隐藏
                    pb.setVisibility(View.GONE);
                    //可见的
                    lv.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }
                if (msg.what==0x14){
                    sizeAll -=msg.arg1;
                    allFileSize.setText(Formatter.formatFileSize(PhoneCleanActivity.this,sizeAll));
                }
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneclean);
        initActionBar(true,false,"手机清理",this);
        //初始化
        initUI();
        //添加适配器数据
        setAdapterData();
    }
    /**添加适配器数据*/
    private void setAdapterData() {
        //给适配器添加数据
        adapter=new PhoneCleanAdapter(this);
        lv.setAdapter(adapter);
        //可见的
        pb.setVisibility(View.VISIBLE);
        //隐藏
        lv.setVisibility(View.INVISIBLE);
        //开启一条子线程,将耗时操作放进里面
        new Thread(new Runnable() {
            @Override
            public void run() {
                //数据源
                 asyneLoadData();
            }
        }).start();
    }
    /**初始化*/
    private void initUI() {
        lv= (ListView) findViewById(R.id.activity_clean_lv);
        allFileSize= (TextView) findViewById(R.id.activit_clean_tv);
        pb= (ProgressBar) findViewById(R.id.activity_clean_pb);
        ll= (LinearLayout) findViewById(R.id.activity_clean_ll);
        delete= (Button) findViewById(R.id.activity_clean_but);
        delete.setOnClickListener(this);
    }
    /**数据源*/
    private void asyneLoadData() {
        //清空数据源数据
        adapter.getData().clear();
        //获数据库中的查讯结果
        List<AppRubbish> list= DBManager.readAppRubbish(PhoneCleanActivity.this);

        //获取内置路径sd卡的根路径
        String rootPath = MemeryManager.getSDstoragePath();
        File file=null;
        for (AppRubbish rubbish:list){
            //跟路经
            file=new File(rootPath+rubbish.getFilePath());
            if (file.exists()){
                Drawable icon=null;
                try {
                    icon =getPackageManager().getApplicationIcon(rubbish.getPackageName());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                String fileSize = Formatter.formatFileSize(this,FlieManager.getFileSize(file));
                //添加数据
                adapter.getData().add(new AppRubbish(true,rubbish.getEnglishName(),fileSize,icon,file));
                //子线程查找到一个文件，燃耗给主线程发消息
                //创建Message对象的三种方法
//                Message message=new Message();
//                Message message=Message.obtain();
                Message message=handler.obtainMessage();
                //要发给谁(地址)
                message.what=0x10;
                //内容
                message.arg1=(int)(FlieManager.getFileSize(file));
//                message.arg2=4;//传int类型
//                message.obj="李四";//传对象
                handler.sendMessage(message);
            }
        }
        //文件检索完
        //发送空的消息
        handler.sendEmptyMessage(0x12);
        //通知适配器重新适配（更新UI的操作，子线程里不行）
//        adapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.actionbar_back_iv:
                //关闭页面
                finish();
                break;
            case R.id.activity_clean_but:
                //不可点击
                delete.setClickable(false);
                //子线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<AppRubbish> temp =new ArrayList<>();
                        for (AppRubbish info : adapter.getData()){
                            if (info.isCheck()) {
                                //文件大小
                                long size = FlieManager.getFileSize(info.getFile());
                                //删除垃圾文件
                                FlieManager.deleteFile(info.getFile());
                                //添加到集合
                                temp.add(info);
                                Message message=handler.obtainMessage();
                                message.what=0x14;
                                message.arg1=(int)size;
                                handler.sendMessage(message);
                            }
                        }
                        //删除集合数据
                        adapter.getData().removeAll(temp);
                        //主线程
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //通知适配器重新适配
                                adapter.notifyDataSetChanged();
                                //可以点击
                                delete.setClickable(true);
                            }
                        });
                    }
                }).start();

                break;
        }
    }
}
