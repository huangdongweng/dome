package com.example.administrator.phonesefe.entity;


import android.graphics.drawable.Drawable;

import java.io.File;

/**
 * Created by Administrator on 2016/12/23.
 */
public class AppRubbish {
    private boolean isCheck;
    private String ChineseName;
    private String EnglishName;
    private String packageName;
    //文档路径
    private String filePath;
    private Drawable icon;
    private String fileSize;
    private File file;
    public AppRubbish(String chineseName, String englishName,
                      String packageName, String filePath) {
        ChineseName = chineseName;
        EnglishName = englishName;
        this.packageName = packageName;
        this.filePath = filePath;

    }

    public AppRubbish(boolean isCheck, String englishName,
                      String fileSize, Drawable icon,File file) {
        this.isCheck = isCheck;
        EnglishName = englishName;
        this.fileSize = fileSize;
        this.icon = icon;
        this.file=file;
    }

    public String getFileSize() {
        return fileSize;
    }

    public File getFile() {
        return file;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getChineseName() {
        return ChineseName;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String fileSize() {
        return fileSize;
    }
}
