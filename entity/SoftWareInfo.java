package com.example.administrator.phonesefe.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/12/20.
 */

public class SoftWareInfo {
    private String label;//应用名
    private Drawable icom;//应用图标
    private String packageName;//应用包名
    private String version;//版本号
    private boolean isCheak;//是否选中
    private boolean isDelete;//能否被删除

    public SoftWareInfo(String label, Drawable icom,
                        String packageName, String version,
                        boolean isCheak, boolean isDelete) {
        this.label = label;
        this.isDelete = isDelete;
        this.icom = icom;
        this.packageName = packageName;
        this.version = version;
        this.isCheak = isCheak;
    }

    public String getLabel() {
        return label;
    }

    public Drawable getIcom() {
        return icom;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getVersion() {
        return version;
    }

    public boolean isCheak() {
        return isCheak;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public void setCheak(boolean cheak) {
        isCheak = cheak;
    }
}
