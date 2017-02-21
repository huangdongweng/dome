package com.example.administrator.phonesefe.entity;

import android.app.ActivityManager;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/12/27.
 */

public class Rocketinfo {
    //是否选中
    private boolean ischeck=false;
    //应用图片
    private Drawable icom;
    //应用名
    private String appName;
    //应用包名
    private String packageName;
    //标签
    private int tag;
    private ActivityManager.RunningAppProcessInfo info;


    public Rocketinfo(boolean ischeck,int tag,ActivityManager.RunningAppProcessInfo info){
        this.ischeck=ischeck;
        this.tag=tag;
        this.info=info;
    }

    public int getTag() {
        return tag;
    }

    public Rocketinfo(boolean ischeck, Drawable icom, String appName, String packageName) {
        this.ischeck = ischeck;
        this.icom = icom;
        this.appName = appName;
        this.packageName = packageName;
    }
    public ActivityManager.RunningAppProcessInfo getInfo() {
        return info;
    }
    public boolean ischeck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }


    public Drawable getIcom() {
        return icom;
    }

    public void setIcom(Drawable icom) {
        this.icom = icom;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
