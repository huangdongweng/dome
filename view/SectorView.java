package com.example.administrator.phonesefe.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.phonesefe.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/12/22.
 * 自定义View       扇形
 * 1、自定义属性
 *      在values包中，创建attyr文件，设置需求的属性
 */

public class SectorView extends View {
    /**开始角度*/
    private int startAngle;
    /**结束角度*/
    private int endAngle;
    /**背景颜色*/
    private int backgroundColorSector= Color.GREEN;
    /**进度颜色*/
    private int progressColorSector=Color.BLUE;
    /**画笔*/
    private Paint paint;

    public SectorView(Context context) {
        this(context,null);
    }

    public SectorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        paint =new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //初始化属性
        TypedArray typedArray= context.getTheme().obtainStyledAttributes(attrs, R.styleable.SectorView,0,0);
        //获取属性个数
        int num=typedArray.getIndexCount();
        for (int i=0;i<num;i++){
            int id=typedArray.getIndex(i);
            switch (id){
                case R.styleable.SectorView_backgroundColorSector:
                   backgroundColorSector=typedArray.getColor(id,Color.GREEN);
                    break;
                case R.styleable.SectorView_progressColorSector:
                    progressColorSector=typedArray.getColor(id,Color.BLUE);
                    break;
                case R.styleable.SectorView_endAngle:
                    endAngle=typedArray.getInt(id,0);
                    break;
            }
        }
        //回收再利用
        typedArray.recycle();
    }
    /**测量的方法*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int heght= MeasureSpec.getSize(heightMeasureSpec);
        if (width<heght){
            heght=width;
        }else {
            width=heght;
        }
        //设置测量结果
        setMeasuredDimension(width,heght);
    }
    /**绘制*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //首页绘制底部的整圆
        paint.setColor(backgroundColorSector);
//        paint.setStrokeWidth(2);//画笔笔数的宽度

        RectF rectf=new RectF(0,0,getWidth(),getHeight());
        canvas.drawArc(rectf,-90,360,true,paint);
        //绘制进度扇形
        paint.setColor(progressColorSector);
        canvas.drawArc(rectf,-90+startAngle,endAngle,true,paint);
    }
    /**动画效果*/
    public void drawAnim(int start , final int end){
        startAngle=start;
        endAngle=end;
        //创建定时器对象
        final Timer timer=new Timer();
        //定时器需要执行的任务
        TimerTask task =new TimerTask() {
            @Override
            public void run() {
                endAngle+=4;
                if (endAngle>=end){
                    endAngle=end;
                    //告诉view 重新绘制
                    postInvalidate();
                    timer.cancel();
                }
                postInvalidate();
            }
        };
        //定时器启动任务
        timer.schedule(task,400,30);

    }
}
