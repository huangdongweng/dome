package com.example.administrator.phonesefe.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.phonesefe.R;

/**
 * Created by Administrator on 2016/12/13.
 */

public class BaseActivity extends AppCompatActivity {
    protected ActionBar actionBar;
    ImageView back;
    TextView title;
    ImageView menu;
    /**开启一个Activity*/
    public void startActivity(Class targetClass){
        Intent intent=new Intent(this,targetClass);
        startActivity(intent);
    }
    /**带值跳转页面*/
    public void startActivity(Class targetClass ,Bundle bundle){
        Intent intent=new Intent(this,targetClass);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
    /**自定义ActionBar*/
    public void initActionBar(boolean isBackShow,boolean isMenuShow,String titleName,View.OnClickListener listener){
        //获取系统的ActionBar
        actionBar=getSupportActionBar();
        //设置actionBer的显示操作---显示自定义
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //设置自定义actionBer布局
        actionBar.setCustomView(R.layout.actionbar);
        //找控件
        back= (ImageView) findViewById(R.id.actionbar_back_iv);
        title= (TextView) findViewById(R.id.actionbar_title_tv);
        menu= (ImageView) findViewById(R.id.actionbar_memu_iv);
        if (isBackShow){
            back.setVisibility(View.VISIBLE);
        }else {
            back.setVisibility(View.GONE);
        }
        if (isMenuShow){
            menu.setVisibility(View.VISIBLE);
        }else {
            menu.setVisibility(View.GONE);
        }
        title.setText(titleName);
        //设置监听事件
        back.setOnClickListener(listener);
        menu.setOnClickListener(listener);
    }
}
