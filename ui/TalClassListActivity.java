package com.example.administrator.phonesefe.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.adapter.ClassListAdapter;
import com.example.administrator.phonesefe.base.BaseActivity;
import com.example.administrator.phonesefe.db.DBManager;
import com.example.administrator.phonesefe.entity.ClassListinfo;

import java.util.List;


/**
 * Created by Administrator on 2016/12/16.
 */

public class TalClassListActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private List<ClassListinfo> list;
    private ListView lv;
    private ClassListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telnumber);
        initActionBar(true,false,"通信大全",this);
        //初始化UI
        init();
    }
    //初始化操作
    private void init() {
        lv= (ListView) findViewById(R.id.activity_telnunber_classlist_lv);
        list= DBManager.readClassListinfo(this);

        adapter=new ClassListAdapter(this);
        adapter.setData(list);
        lv.setAdapter(adapter);
        //添加每行的监听事件
        lv.setOnItemClickListener(this);
    }
    /**添加监听事件*/
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.actionbar_back_iv:
                //关闭页面
                finish();
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        Bundle bundle =new Bundle();
        ClassListinfo info = adapter.getData().get((int)id);
        bundle.putString("name",info.getName());
        bundle.putInt("idx",info.getIdx());
        startActivity(TelNumberActivity.class,bundle);
    }
}
