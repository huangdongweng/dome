package com.example.administrator.phonesefe.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.adapter.GuidePaperAdapter;
import com.example.administrator.phonesefe.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面
 * Created by Administrator on 2016/12/13.
 */

public class GuideActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    //声明
    private ViewPager vp;
    private List<View> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Button skip;
    private ImageView circle_1, circle_2, circle_3;

    /**
     * 判断是否是第一次运行
     */
    private boolean isFirstCome;//false表示不是第一次

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFirstCome = getSharedPreferences("share", MODE_PRIVATE).getBoolean("isFirstCome", false);
//        isFirstCome = getSharedPreferences("share", MODE_PRIVATE).getString("keys",);

        if (!isFirstCome) {
            setContentView(R.layout.activity_guide);
            initActionBar(false,false,"手机管家",null);
            actionBar.hide();
            //初始化控件
            initUI();
            //添加viewpager的数据源
            initData();
            //创建适配器
            GuidePaperAdapter adapter = new GuidePaperAdapter(data);
            //添加适配器
            vp.setAdapter(adapter);
            /**改变文件中的状态*/
            //获取编辑器对象
            SharedPreferences.Editor edit = getSharedPreferences("share", MODE_PRIVATE).edit();
            edit.putBoolean("isFirstCome", true);//存数据
//            edit.putString("keys","nipkjki");
            edit.commit();//提交
            //给ViewPager设置监听事件
            vp.addOnPageChangeListener(this);

        } else {
            //跳转到欢迎页面
            startActivity(WelcomeActivity.class);
            //关页面

            finish();
        }
    }

    /**
     * 初始化控件
     */
    private void initUI() {
        vp = (ViewPager) findViewById(R.id.guide_item_vp);
        skip = (Button) findViewById(R.id.activity_guide_skip_tv);
        circle_2 = (ImageView) findViewById(R.id.activity_guide_circle_iv1);
        circle_3 = (ImageView) findViewById(R.id.activity_guide_circle_iv2);
        circle_1 = (ImageView) findViewById(R.id.activity_guide_circle_iv3);
        //设置skip的监听事件
        skip.setOnClickListener(this);
    }

    /**
     * 初始化数据源
     */
    private void initData() {
        inflater = LayoutInflater.from(this);
        data.add(inflater.inflate(R.layout.guide_item_01, null));
        data.add(inflater.inflate(R.layout.guide_item_02, null));
        data.add(inflater.inflate(R.layout.guide_item_03, null));

    }
    /**给skip添加监听事件*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_guide_skip_tv:
                startActivity(HomeActivity.class);
                finish();
                break;
        }
    }

    /**
     * 页面在滑动时会调用的方法
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 页面最终停止时会调用的方法
     */
    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            circle_1.setImageResource(R.drawable.shape_guide_cick_red);
            circle_2.setImageResource(R.drawable.shape_guide_circk_gray);
            circle_3.setImageResource(R.drawable.shape_guide_circk_gray);
            skip.setVisibility(View.INVISIBLE);//让skip消失
        } else if (position == 1) {
            circle_1.setImageResource(R.drawable.shape_guide_circk_gray);
            circle_2.setImageResource(R.drawable.shape_guide_cick_red);
            circle_3.setImageResource(R.drawable.shape_guide_circk_gray);
            skip.setVisibility(View.INVISIBLE);//让skip消失
        } else if (position == 2) {
            circle_1.setImageResource(R.drawable.shape_guide_circk_gray);
            circle_2.setImageResource(R.drawable.shape_guide_circk_gray);
            circle_3.setImageResource(R.drawable.shape_guide_cick_red);
            skip.setVisibility(View.VISIBLE);//可见的
        }
    }
    /**
     * 但页面状态发生变化时
     */
    @Override
    public void onPageScrollStateChanged(int state) {
    }
}