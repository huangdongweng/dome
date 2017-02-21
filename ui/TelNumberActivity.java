package com.example.administrator.phonesefe.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.adapter.NumberAdapter;
import com.example.administrator.phonesefe.base.BaseActivity;
import com.example.administrator.phonesefe.db.DBManager;
import com.example.administrator.phonesefe.entity.TeNumber;

import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class  TelNumberActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private ListView lv;

    private List<TeNumber> data;
    private String name;
    private int idx;
    private NumberAdapter adapter;
    //buider作用：创建一个对话框
    private AlertDialog.Builder builder;
    //alert作用：控制对话框的现实和隐藏
    private AlertDialog alert;
    LayoutInflater inflater;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        //设置actionbar
        initActionBar(true,false,name,this);
        //初始化
        init();





    }
    /***数据初始化*/
    private void init() {
        Intent intent=getIntent();
        //拿到数据
        Bundle bundle=intent.getBundleExtra("bundle");
        name=bundle.getString("name");
        idx=bundle.getInt("idx");
        inflater =LayoutInflater.from(this);
        lv= (ListView) findViewById(R.id.activity_telnunber_number_lv);
        //获取数据源
        data= DBManager.readTeNumber(this,idx);
        //创建适配器
        adapter=new NumberAdapter(this);
        adapter.setData(data);
        //添加适配器
        lv.setAdapter(adapter);
        //给ListView添加监听事件
        lv.setOnItemClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.actionbar_back_iv:
                //关闭页面
               finish();
                break;
            case R.id.dialog_telnumber_true_but:
                break;
            case R.id.dialog_telnumber_false_but:
                       alert.dismiss();//隐藏
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        //点击时
        //创建弹出窗口
        builder=new AlertDialog.Builder(this);
        View dialogview=inflater.inflate(R.layout.dialog_telnumber,null);
        //弹出对话框设置视图
        builder.setView(dialogview);
        alert=builder.create();

//        alert.dismiss();//隐藏
        //找控件
        TextView message= (TextView) dialogview.findViewById(R.id.dialog_telnumber_message_tv);
        Button yes= (Button) dialogview.findViewById(R.id.dialog_telnumber_true_but);
        Button no= (Button) dialogview.findViewById(R.id.dialog_telnumber_false_but);
        //获取集合List<TeNumber>中的数据
        final String name=data.get(i).getName();
        final String number=data.get(i).getNumber();
        message.setText(name+" : "+number);
        //添加监听事件
        no.setOnClickListener(this);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建意图
                Intent intent =new Intent();
                //设置打电话动作
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+number));
                //添加意图
                startActivity(intent);
                alert.dismiss();
            }
        });
        alert.show();//显示
    }
}
