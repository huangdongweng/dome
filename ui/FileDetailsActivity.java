package com.example.administrator.phonesefe.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.adapter.FileDetailsAdapter;
import com.example.administrator.phonesefe.base.BaseActivity;
import com.example.administrator.phonesefe.biz.FlieManager;
import com.example.administrator.phonesefe.biz.MemeryManager;
import com.example.administrator.phonesefe.entity.Fileinfo;
import com.example.administrator.phonesefe.utils.FileTypeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/29.
 */
public class FileDetailsActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView num;
    private TextView size;
    private ListView lv;
    private ProgressBar pb;
    private Button but;
    private String name;
    private List<Fileinfo> data;
    private long fileSize;
    private FlieManager fileManager = FlieManager.getFileManager();
    private FileDetailsAdapter adapter;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filedetails);
        initActionBar(true, false, name, this);
        //初始化
        initUI();
        //获得数据源
        initData();
    }
    /**
     * 初始化
     */
    private void initUI() {
        num = (TextView) findViewById(R.id.activity_filedetails_tv2);
        size = (TextView) findViewById(R.id.activity_filedetails_tv4);
        lv = (ListView) findViewById(R.id.activity_filedetails_lv);
        pb = (ProgressBar) findViewById(R.id.activity_filedetails_pb);
        but = (Button) findViewById(R.id.activity_filedetails_but);
        lv.setOnItemClickListener(this);
        but.setOnClickListener(this);
    }
    private void initData() {
        //获得上一个页面传的值
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        type = bundle.getString("type");
        switch (type) {
            case "all":
                name = "全部文件";
                fileSize = fileManager.getAllFileSize();
                data = fileManager.getAllFile();
                break;
            case "doc":
                name = "文档文件";
                fileSize = fileManager.getDocFileSize();
                data = fileManager.getDocFile();
                break;
            case "video":
                name = "视频文件";
                fileSize = fileManager.getVideofFileSize();
                data = fileManager.getVideofFile();
                break;
            case "audio":
                name = "音频文件";
                fileSize = fileManager.getAudioFileSize();
                data = fileManager.getAudioFile();
                break;
            case "image":
                name = "图像文件";
                fileSize = fileManager.getImageFileSize();
                data = fileManager.getImageFile();
                break;
            case "zip":
                name = "压缩文件";
                fileSize = fileManager.getZipFileSize();
                data = fileManager.getZipFile();
                break;
            case "apk":
                name = "应用文件";
                fileSize = fileManager.getApkFileSize();
                data = fileManager.getApkFile();
                break;
        }
        num.setText(data.size()+"");//加""号，把类型转换为String类型
        size.setText(MemeryManager.getFamaterMem(this, fileSize));
        //创建适配器
        adapter = new FileDetailsAdapter(this);
        //把数据源添加到适配器
        adapter.setData(data);
        //添加适配器
        lv.setAdapter(adapter);
        pb.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }
    /**
     * 页面销毁时
     */
    @Override
    protected void onDestroy() {
        for (Fileinfo info : adapter.getData()) {
            info.setCheck(false);
        }
        super.onDestroy();
    }
    /**
     * ListView的监听事件
     *
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    //点击查看文件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Fileinfo info = adapter.getItem((int) l);
        Intent intent = new Intent();//设置意图
        //文件预览 隐式跳转
        intent.setAction(Intent.ACTION_VIEW);//设置动作ACTION_VIEW
        //文件类型
        intent.setDataAndType(Uri.fromFile(info.getFile()), FileTypeUtil.getMIMEType(info.getFile()));
        startActivity(intent);//添加意图
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionbar_back_iv:
                finish();
                break;
            case R.id.activity_filedetails_but:
                final List<Fileinfo> list = new ArrayList<>();
                //从数据源中拿出被选中的文件
                for (Fileinfo info : adapter.getData()) {
                    if (info.isCheck()) {
                        list.add(info);
                        changeData(type, info);
                        info.getFile().delete();
                    }
                }
                //删除选中的全部数据
                adapter.getData().removeAll(list);
                //重新适配数据
                adapter.notifyDataSetChanged();
                break;
        }
    }
    /**
     * 变化的数据
     */
    private void changeData(String type, Fileinfo info) {
        switch (type) {
            case "all":
                //重新赋值
                fileManager.setAllFileSize(fileManager.getAllFileSize() - info.getFile().length());
                //删除文件
                fileManager.getAllFile().remove(info);
                num.setText(fileManager.getAllFile().size()+"");
                size.setText(Formatter.formatFileSize(this, fileManager.getAllFileSize()));
                break;
            case "doc":
                //重新赋值
                fileManager.setDocFileSize(fileManager.getDocFileSize() - info.getFile().length());
                //删除文件
                fileManager.getDocFile().remove(info);
                num.setText(fileManager.getDocFile().size()+"");
                size.setText(Formatter.formatFileSize(this, fileManager.getDocFileSize()));
                break;
            case "video":
                //重新赋值
                fileManager.setVideofFileSize(fileManager.getVideofFileSize() - info.getFile().length());
                //删除文件
                fileManager.getVideofFile().remove(info);
                num.setText(fileManager.getVideofFile().size()+"");
                size.setText(Formatter.formatFileSize(this, fileManager.getVideofFileSize()));
                break;
            case "audio":
                //重新赋值
                fileManager.setAudioFileSize(fileManager.getAudioFileSize() - info.getFile().length());
                //删除文件
                fileManager.getAudioFile().remove(info);
                num.setText(fileManager.getAudioFile().size()+"");
                size.setText(Formatter.formatFileSize(this, fileManager.getAudioFileSize()));
                break;
            case "image":
                //重新赋值
                fileManager.setImageFileSize(fileManager.getImageFileSize() - info.getFile().length());
                //删除文件
                fileManager.getImageFile().remove(info);
                num.setText(fileManager.getImageFile().size()+"");
                size.setText(Formatter.formatFileSize(this, fileManager.getImageFileSize()));
                break;
            case "zip":
                //重新赋值
                fileManager.setZipFileSize(fileManager.getZipFileSize() - info.getFile().length());
                //删除文件
                fileManager.getZipFile().remove(info);
                num.setText(fileManager.getZipFile().size()+"");
                size.setText(Formatter.formatFileSize(this, fileManager.getZipFileSize()));
                break;
            case "apk":
                //重新赋值
                fileManager.setApkFileSize(fileManager.getApkFileSize() - info.getFile().length());
                //删除文件
                fileManager.getZipFile().remove(info);
                num.setText(fileManager.getApkFile().size()+"");
                size.setText(Formatter.formatFileSize(this, fileManager.getApkFileSize()));
                break;
        }
    }
}
