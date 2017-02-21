package com.example.administrator.phonesefe.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.api.SearchFileCallback;
import com.example.administrator.phonesefe.base.BaseActivity;
import com.example.administrator.phonesefe.biz.FlieManager;
import com.example.administrator.phonesefe.biz.MemeryManager;

/**
 * Created by Administrator on 2016/12/28.
 */

public class FileActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv;
    private TextView tv_allfilesize, tv_docFileSize, tv_videofFileSize, tv_audioFileSize, tv_imageFileSize, tv_zipFileSize, tv_apkFileSize;
    private ImageView iv_all, iv_doc, iv_video, iv_audio, iv_image, iv_zip, iv_apk;
    private ProgressBar pb_all, pb_doc, pb_video, pb_audio, pb_image, pb_zip, pb_apk;
    private FlieManager fileManagerr = FlieManager.getFileManager();
    private SearchFileCallback listener = new SearchFileCallback() {
        //检索到文件时会调用的方法
        @Override
        public void searching(final long fileSize) {
            //主线程
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //调用封装的方法格式化大小
                    tv.setText(MemeryManager.getFamaterMem(FileActivity.this,fileSize));
                    tv_allfilesize.setText(Formatter.formatFileSize(FileActivity.this,fileManagerr.getAllFileSize()));
                    tv_docFileSize.setText(MemeryManager.getFamaterMem(FileActivity.this,fileManagerr.getDocFileSize()));
                    tv_videofFileSize.setText(MemeryManager.getFamaterMem(FileActivity.this,fileManagerr.getVideofFileSize()));
                    tv_audioFileSize.setText(MemeryManager.getFamaterMem(FileActivity.this,fileManagerr.getAudioFileSize()));
                    tv_imageFileSize.setText(MemeryManager.getFamaterMem(FileActivity.this,fileManagerr.getImageFileSize()));
                    tv_zipFileSize.setText(MemeryManager.getFamaterMem(FileActivity.this,fileManagerr.getZipFileSize()));
                    tv_apkFileSize.setText(MemeryManager.getFamaterMem(FileActivity.this,fileManagerr.getApkFileSize()));
                }
            });
        }
        //单文件检索完毕的时，会调的方法
        @Override
        public void ending(boolean isEnd) {
            //主线程
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pb_all.setVisibility(View.GONE);
                    iv_all.setVisibility(View.VISIBLE);
                    iv_all.setOnClickListener(FileActivity.this);
                    pb_doc.setVisibility(View.GONE);
                    iv_doc.setVisibility(View.VISIBLE);
                    iv_doc.setOnClickListener(FileActivity.this);
                    pb_video.setVisibility(View.GONE);
                    iv_video.setVisibility(View.VISIBLE);
                    iv_video.setOnClickListener(FileActivity.this);
                    pb_audio.setVisibility(View.GONE);
                    iv_audio.setVisibility(View.VISIBLE);
                    iv_audio.setOnClickListener(FileActivity.this);
                    pb_image.setVisibility(View.GONE);
                    iv_image.setVisibility(View.VISIBLE);
                    iv_image.setOnClickListener(FileActivity.this);
                    pb_zip.setVisibility(View.GONE);
                    iv_zip.setVisibility(View.VISIBLE);
                    iv_zip.setOnClickListener(FileActivity.this);
                    pb_apk.setVisibility(View.GONE);
                    iv_apk.setVisibility(View.VISIBLE);
                    iv_apk.setOnClickListener(FileActivity.this);
                }
            });
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        initActionBar(true, false, "管理文件", this);
        //初始化啊
        initUI();
        asyncloadData();
    }
    private void asyncloadData() {
        //给fileManagerr类中的listener传本类的listener
        fileManagerr.setListener(listener);
        //子线程（耗时操作）
        new Thread(new Runnable() {
            @Override
            public void run() {
                //文件检索
                fileManagerr.searchFiles();
            }
        }).start();
    }
    /**
     * 初始化
     */
    private void initUI() {
        tv = (TextView) findViewById(R.id.activity_file_tv);
        tv_allfilesize = (TextView) findViewById(R.id.activity_file_allfilesize_tv);
        tv_docFileSize = (TextView) findViewById(R.id.activity_file_docFilesize_tv);
        tv_videofFileSize = (TextView) findViewById(R.id.activity_file_videofFilesize_tv);
        tv_audioFileSize = (TextView) findViewById(R.id.activity_file_audioFilesize_tv);
        tv_imageFileSize = (TextView) findViewById(R.id.activity_file_imagefilesize_tv);
        tv_zipFileSize = (TextView) findViewById(R.id.activity_file_zipfilesize_tv);
        tv_apkFileSize = (TextView) findViewById(R.id.activity_file_apkfilesize_tv);

        iv_all = (ImageView) findViewById(R.id.activity_file_allfilesize_iv);
        iv_doc = (ImageView) findViewById(R.id.activity_file_docFilesize_iv);
        iv_video = (ImageView) findViewById(R.id.activity_file_videofFilesize_iv);
        iv_audio = (ImageView) findViewById(R.id.activity_file_audioFilesize_iv);
        iv_image = (ImageView) findViewById(R.id.activity_file_imagefilesize_iv);
        iv_zip = (ImageView) findViewById(R.id.activity_file_zipfilesize_iv);
        iv_apk = (ImageView) findViewById(R.id.activity_file_apkfilesize_iv);

        pb_all = (ProgressBar) findViewById(R.id.activity_file_allfilesize_pb);
        pb_doc = (ProgressBar) findViewById(R.id.activity_file_docFilesize_pb);
        pb_video = (ProgressBar) findViewById(R.id.activity_file_videofFilesize_pb);
        pb_audio = (ProgressBar) findViewById(R.id.activity_file_audioFilesize_pb);
        pb_image = (ProgressBar) findViewById(R.id.activity_file_imagefilesize_pb);
        pb_zip = (ProgressBar) findViewById(R.id.activity_file_zipfilesize_pb);
        pb_apk = (ProgressBar) findViewById(R.id.activity_file_apkfilesize_pb);


    }

    @Override
    public void onClick(View view) {
        Bundle bundle = null;
        switch (view.getId()) {
            case R.id.actionbar_back_iv:
                finish();
                break;
            case R.id.activity_file_allfilesize_iv:
                bundle = new Bundle();
                bundle.putString("type", "all");
                startActivity(FileDetailsActivity.class, bundle);
                break;
            case R.id.activity_file_docFilesize_iv:
                bundle = new Bundle();
                bundle.putString("type", "doc");
                startActivity(FileDetailsActivity.class, bundle);
                break;
            case R.id.activity_file_videofFilesize_iv:
                bundle = new Bundle();
                bundle.putString("type", "video");
                startActivity(FileDetailsActivity.class, bundle);
                break;
            case R.id.activity_file_audioFilesize_iv:
                bundle = new Bundle();
                bundle.putString("type", "audio");
                startActivity(FileDetailsActivity.class, bundle);
                break;
            case R.id.activity_file_imagefilesize_iv:
                bundle = new Bundle();
                bundle.putString("type", "image");
                startActivity(FileDetailsActivity.class, bundle);
                break;
            case R.id.activity_file_zipfilesize_iv:
                bundle = new Bundle();
                bundle.putString("type", "zip");
                startActivity(FileDetailsActivity.class, bundle);
                break;
            case R.id.activity_file_apkfilesize_iv:
                bundle = new Bundle();
                bundle.putString("type", "apk");
                startActivity(FileDetailsActivity.class, bundle);
                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        tv.setText(Formatter.formatFileSize(FileActivity.this,fileManagerr.getAllFileSize()));
        tv_allfilesize.setText(Formatter.formatFileSize(FileActivity.this,fileManagerr.getAllFileSize()));
        tv_docFileSize.setText(MemeryManager.getFamaterMem(FileActivity.this,fileManagerr.getDocFileSize()));
        tv_videofFileSize.setText(MemeryManager.getFamaterMem(FileActivity.this,fileManagerr.getVideofFileSize()));
        tv_audioFileSize.setText(MemeryManager.getFamaterMem(FileActivity.this,fileManagerr.getAudioFileSize()));
        tv_imageFileSize.setText(MemeryManager.getFamaterMem(FileActivity.this,fileManagerr.getImageFileSize()));
        tv_zipFileSize.setText(MemeryManager.getFamaterMem(FileActivity.this,fileManagerr.getZipFileSize()));
        tv_apkFileSize.setText(MemeryManager.getFamaterMem(FileActivity.this,fileManagerr.getApkFileSize()));
    }
}
