package com.example.administrator.phonesefe.entity;

import java.io.File;

/**
 * Created by Administrator on 2016/12/23.
 */
public class Rubbishinfo {
    private boolean isCheck;
    private File file;
    /**文件图标以类型*/
    private String iconName;
    /**MiniType类型*/
    private String fileType;

    public Rubbishinfo(File file, String iconName, String fileType) {

        this.file = file;
        this.iconName = iconName;
        this.fileType = fileType;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public File getFile() {
        return file;
    }

    public String getIconName() {
        return iconName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
