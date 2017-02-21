package com.example.administrator.phonesefe.entity;

import java.io.File;

/**
 * Created by Administrator on 2016/12/28.
 */

public class Fileinfo {
    private boolean isCheck;
    private File file;
    private String iconName;
    private String fileType;

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public Fileinfo(File file, String iconName, String fileType) {
        this.file = file;
        this.iconName = iconName;
        this.fileType = fileType;
    }

    public boolean isCheck() {
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
}
