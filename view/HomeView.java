package com.example.administrator.phonesefe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/12/24.
 */

public class HomeView extends View {
    /**扇形的当前角度*/
    private int currentAngle = 90;
    private Paint paint;
    /**记录是否回退*/
    private boolean back = true;
    /**记录动画状态，是否在执行  true表示动画正在执行*/
    private boolean isRunning = false;

    public HomeView(Context context) {
        this(context,null);
    }

    public HomeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public HomeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        //画笔颜色
        paint.setColor(Color.BLUE);
    }
    /**定义长度*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width < height){
            height = width;
        }else {
            width = height;
        }
        //添加宽高
        setMeasuredDimension(width,height);
    }
    /**d定义开始和进程*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //
        RectF rectF = new RectF(0,0,getWidth(),getHeight());
        canvas.drawArc(rectF,-90,currentAngle,true,paint);
    }

    /**
     *  设置动画
     * @param targetAngle 指定的目标角度
     */
    public void  setAngle(final int targetAngle){

        if (!isRunning){
            isRunning = true;
            //定时器
            final Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (back){
                        if (currentAngle >= 10 ) {
                            currentAngle -= 10;
                        }else {
                            currentAngle = 0;
                            back = false;
                        }
                    }else {
                        if (currentAngle <= targetAngle-10){
                            currentAngle += 10;
                        }else {
                            currentAngle = targetAngle;
                            back = true;
                            isRunning = false;
                            //取消
                            timer.cancel();
                        }
                    }
                    postInvalidate();
                }
            };
            //延时400毫秒，40毫秒执行一次
            timer.schedule(task,400,40);
        }
    }

}
