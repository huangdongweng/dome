package com.example.administrator.phonesefe.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.base.BaseActivity;

/**欢迎页面
 * Created by Administrator on 2016/12/14.
 */

public class WelcomeActivity extends BaseActivity implements Animation.AnimationListener{
    //声明
    private ImageView logo;
    private Animation anim;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initActionBar(false,false,"手机管家",null);
        actionBar.hide();
        //初始化
        logo= (ImageView) findViewById(R.id.activity_welcome_iv);
        anim= AnimationUtils.loadAnimation(this,R.anim.animation_welcome_alpha);
        //设置动画停留在结束位置
        anim.setFillAfter(true);
        //设置动画的监听事件
        anim.setAnimationListener(this);
        logo.startAnimation(anim);

    }
    /**动画开始时触发*/
    @Override
    public void onAnimationStart(Animation animation) {

    }
    /***动画结束时触发*/
    @Override
    public void onAnimationEnd(Animation animation) {
        //跳到Home页面
        startActivity(HomeActivity.class);
        //关页面
        finish();
    }
    /**动画重复时触发*/
    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
